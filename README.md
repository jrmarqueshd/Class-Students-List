# Class Students List

> Projeto feito para estudar Java e o ecossistema Spring Boot.

Uma API REST para gerenciamento de alunos, cursos e matrículas. O sistema permite cadastrar alunos e cursos, e depois matricular alunos nos cursos disponíveis.

---

## O que o projeto faz

O projeto expõe três recursos principais:

- **Alunos** (`/students`) — CRUD completo de alunos
- **Cursos** (`/courses`) — CRUD completo de cursos
- **Matrículas** (`/enrollments`) — Vincula alunos a cursos

Algumas regras de negócio importantes:
- Não é possível deletar um aluno que ainda está matriculado em algum curso
- Não é possível deletar um curso que ainda tem alunos matriculados
- O e-mail do aluno deve ser único
- Um aluno só pode ser matriculado uma vez no mesmo curso

---

## Tecnologias

- **Java 26**
- **Spring Boot 4.0.6**
- **Spring Data JPA** (persistência)
- **Spring Validation** (validação de campos)
- **Flyway** (migrations de banco de dados)
- **PostgreSQL** (banco de dados)
- **Docker Compose** (para subir o banco localmente)
- **Gradle** (build)

---

## Pré-requisitos

Antes de rodar o projeto, você vai precisar ter instalado:

- [Java 26+](https://jdk.java.net/)
- [Docker](https://www.docker.com/) (para o PostgreSQL)

---

## Como rodar

### 1. Clone o repositório

```bash
git clone git@github.com:jrmarqueshd/Class-Students-List.git class-students-list
cd class-students-list
```

### 2. Configure as variáveis de ambiente

Os arquivos de configuração com dados sensíveis (credenciais do banco) **não estão versionados**. Em vez disso, o repositório traz versões de exemplo que você precisa copiar e preencher com os seus valores:

```bash
# Configuração da aplicação
cp src/main/resources/application-example.yml src/main/resources/application.yaml

# Configuração do Docker Compose
cp compose-example.yaml compose.yaml
```

Abra os arquivos copiados e preencha as variáveis:
- `DATABASE_URL` — ex: `jdbc:postgresql://localhost:5432/students_db`
- `DATABASE_USERNAME` — usuário do PostgreSQL
- `DATABASE_PASSWORD` — senha do PostgreSQL

> Nunca commite o `application.yaml` ou o `compose.yaml` com credenciais reais. Esses arquivos estão no `.gitignore` por esse motivo.

### 3. Suba o banco de dados

Com o `compose.yaml` preenchido, suba o container:

```bash
docker compose up -d
```

Isso vai subir um container PostgreSQL na porta **5432** com o banco e as credenciais que você definiu.

> **Dica:** o Spring Boot tem integração com Docker Compose embutida. Se preferir, você pode simplesmente rodar a aplicação direto — ela tentará subir o compose automaticamente.

### 4. Rode a aplicação

```bash
./gradlew bootRun
```

A aplicação vai subir na porta **5555**. As migrations do Flyway rodam automaticamente na inicialização, criando todas as tabelas necessárias.

Acesse: `http://localhost:5555`

---

## Estrutura do banco de dados

O banco é criado e versionado pelo Flyway. As tabelas são:

**`students`**
| Coluna | Tipo | Descrição |
|---|---|---|
| id | BIGSERIAL | Chave primária |
| name | VARCHAR(120) | Nome do aluno |
| email | VARCHAR(160) | E-mail único |
| birth_date | DATE | Data de nascimento |
| created_at | TIMESTAMP | Data de criação (automática) |

**`courses`**
| Coluna | Tipo | Descrição |
|---|---|---|
| id | BIGSERIAL | Chave primária |
| name | VARCHAR(120) | Nome do curso |
| code | VARCHAR(30) | Código do curso |
| description | TEXT | Descrição |
| workload_hours | INTEGER | Carga horária em horas |
| active | BOOLEAN | Se o curso está ativo |
| created_at | TIMESTAMP | Data de criação (automática) |
| updated_at | TIMESTAMP | Data da última atualização |

**`enrollments`**
| Coluna | Tipo | Descrição |
|---|---|---|
| id | BIGSERIAL | Chave primária |
| student_id | BIGINT | FK para aluno |
| course_id | BIGINT | FK para curso |
| status | BOOLEAN | Status da matrícula |
| enrolled_at | TIMESTAMP | Data da matrícula (automática) |

---

## Endpoints

### Alunos — `/students`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/students` | Cadastra um novo aluno |
| `GET` | `/students?page=0&size=10` | Lista todos os alunos (paginado) |
| `GET` | `/students/{id}` | Busca aluno por ID |
| `PUT` | `/students/{id}` | Atualiza dados do aluno |
| `DELETE` | `/students/{id}` | Remove um aluno |
| `GET` | `/students/{id}/courses` | Lista os cursos em que o aluno está matriculado |

**Corpo para criação/atualização:**
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "birth_date": "2000-05-15"
}
```

---

### Cursos — `/courses`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/courses` | Cadastra um novo curso |
| `GET` | `/courses?page=0&size=10` | Lista todos os cursos (paginado) |
| `GET` | `/courses/{id}` | Busca curso por ID |
| `PUT` | `/courses/{id}` | Atualiza dados do curso |
| `DELETE` | `/courses/{id}` | Remove um curso |
| `GET` | `/courses/{id}/students` | Lista os alunos matriculados no curso |

**Corpo para criação/atualização:**
```json
{
  "name": "Desenvolvimento Web",
  "code": "DEV-WEB-01",
  "description": "Curso completo de desenvolvimento web com HTML, CSS e JavaScript",
  "workload_hours": 120,
  "active": true
}
```

---

### Matrículas — `/enrollments`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/enrollments` | Matricula um aluno em um curso |
| `DELETE` | `/enrollments/student/{student_id}/course/{course_id}/cancel` | Cancela uma matrícula específica |
| `DELETE` | `/enrollments/student/{student_id}/cancel` | Cancela todas as matrículas de um aluno |
| `DELETE` | `/enrollments/course/{course_id}/cancel` | Cancela todas as matrículas de um curso |

**Corpo para criação:**
```json
{
  "student_id": 1,
  "course_id": 2,
  "status": true
}
```

---

## Tratamento de erros

A API retorna erros no formato [Problem Details (RFC 9457)](https://www.rfc-editor.org/rfc/rfc9457). Exemplos:

**404 — Recurso não encontrado:**
```json
{
  "type": "/errors/resource-not-found",
  "title": "Resource not found",
  "status": 404,
  "detail": "Aluno não encontrado."
}
```

**409 — E-mail duplicado:**
```json
{
  "type": "/errors/email-already-exists",
  "title": "Duplicated email",
  "status": 409,
  "detail": "O email já existe na base de dados"
}
```

**400 — Campos inválidos:**
```json
{
  "type": "/errors/validation-error",
  "title": "Validation error",
  "status": 400,
  "detail": "Um ou mais campos falharam",
  "errors": [
    { "field": "email", "message": "E-mail inválido" },
    { "field": "name", "message": "Nome é obrigatório" }
  ]
}
```

> **Atenção para datas:** o campo `birth_date` deve seguir o padrão `yyyy-MM-dd`. Qualquer outro formato vai retornar um erro 400 com a mensagem explicando o padrão esperado.

---

## Estrutura do projeto

```
src/main/java/com/junior/
├── ClassStudentsListApplication.java   # Ponto de entrada
├── student/                            # Tudo relacionado a alunos
│   ├── StudentEntity.java
│   ├── StudentsController.java
│   ├── StudentService.java
│   ├── StudentsRepository.java
│   └── dto/
├── courses/                            # Tudo relacionado a cursos
│   ├── CourseEntity.java
│   ├── CoursesController.java
│   ├── CourseService.java
│   ├── CoursesRepository.java
│   └── dto/
├── enrollments/                        # Tudo relacionado a matrículas
│   ├── EnrollmentEntity.java
│   ├── EnrollmentsController.java
│   ├── EnrollmentService.java
│   ├── EnrollmentsRepository.java
│   └── dto/
└── shared/
    └── exception/                      # Exceções e handler global
```
