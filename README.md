# Sistema de Gerenciamento de Blog (API)

## Objetivo
Evoluir do CRUD simples para um sistema com relacionamentos entre entidades. Esta API permite gerenciar **Posts** e seus **Comentários**, demonstrando operações CRUD completas e relacionamentos JPA.


---

## Conceitos praticados

- Tudo que foi aprendido no projeto anterior (CRUD simples, Spring Boot, DTOs, validação, tratamento de exceções, H2 Database).
- **Relacionamentos entre entidades JPA**:
    - `@OneToMany` (Post → Comentários)
    - `@ManyToOne` (Comentário → Post)
- Uso de **banco de dados real** (PostgreSQL ou MySQL) via Docker.
- **Paginação e ordenação** com Spring Data JPA.
- **Tratamento de exceções mais específico**, com respostas HTTP padronizadas.

---

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL / MySQL (via Docker)
- Lombok
- Validation (`jakarta.validation`)
- Spring Web (REST API)
- Docker (para o banco de dados)

---

## Requisitos Funcionais (RFs)

1. **RF001**: Cadastro de Posts  
   Um Post deve ter:
    - id (identificador único)
    - título
    - conteúdo
    - nome do autor
    - data de publicação

2. **RF002**: Listagem de todos os Posts de forma paginada.

3. **RF003**: Busca de um Post específico pelo seu id.

4. **RF004**: Cadastro de Comentários em um Post existente. Um Comentário deve ter:
    - id (identificador único)
    - texto
    - nome do autor
    - data de criação
    - referência para o Post ao qual pertence

5. **RF005**: Listagem de todos os Comentários de um Post específico.

6. **RF006**: Exclusão de um Post (com exclusão em cascata dos comentários associados).

---

## Requisitos Não-Funcionais (RNFs)

1. **RNF001**: A aplicação deve ser configurada para se conectar a um banco de dados PostgreSQL ou MySQL rodando em um container Docker.

2. **RNF002**: A listagem de Posts deve ser paginada, aceitando parâmetros na URL como: ?page=0&size=10&sort=dataDePublicacao,desc

3. **RNF003**: A API deve retornar códigos de status HTTP apropriados para cada operação:
- `201 Created` para criação
- `204 No Content` para exclusão bem-sucedida
- `200 OK` para consultas e atualizações bem-sucedidas

---

## Endpoints principais

### Posts
| Método | Endpoint | Descrição |
|--------|---------|-----------|
| POST | `/posts` | Criar um novo Post |
| GET | `/posts` | Listar Posts paginados |
| GET | `/posts/{id}` | Buscar Post por ID |
| PUT | `/posts/{id}` | Atualizar Post existente |
| DELETE | `/posts/{id}` | Excluir Post (com comentários em cascata) |

### Comentários
| Método | Endpoint | Descrição |
|--------|---------|-----------|
| POST | `/posts/{postId}/comentarios` | Adicionar comentário a um Post existente |
| GET | `/posts/{postId}/comentarios` | Listar todos os comentários de um Post |

---

