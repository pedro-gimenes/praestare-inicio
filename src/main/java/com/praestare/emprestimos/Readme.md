# 💰 Praestare Empréstimos API

API REST desenvolvida em **Java Spring Boot** para gerenciamento de **usuários**, **denúncias** e **contatos**, com autenticação JWT e integração com banco de dados **MySQL**.  
Este sistema faz parte da plataforma **Praestare Empréstimos**, voltada ao controle e segurança de operações financeiras e de relacionamento com clientes.

---

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3**
- **Spring Security (JWT)**
- **Spring Data JPA**
- **MySQL**
- **Lombok**
- **ModelMapper**
- **Maven**

---

## 🗂️ Estrutura do Projeto

src/
├── main/
│ ├── java/
│ │ ├── controller/ # Controladores REST (Usuário, Contato, Denúncia, Autenticação)
│ │ ├── service/ # Lógica de negócio (Services)
│ │ ├── repository/ # Interfaces JPA (Repositories)
│ │ ├── model/ # Entidades JPA (Usuário, Contato, Denúncia, Login)
│ │ ├── mapper/ # Conversores DTO ↔ Entity
│ │ ├── security/ # Autenticação e filtros JWT
│ │ ├── exception/ # Tratamento de erros e exceções
│ │ └── config/ # Configurações de segurança, CORS, RestTemplate
│ └── resources/
│ ├── application.properties
│ └── static/ / templates/ (caso necessário)
└── test/
└── java/ # Testes unitários e de integração

yaml
Copiar código

---

## ⚙️ Configuração do Ambiente

### 1️⃣ Banco de Dados

Crie um banco MySQL com o nome **`praestare_emprestimo`** e configure o arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/praestare_emprestimo
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.error.include-stacktrace=never

# Chave JWT (alterar para ambiente de produção!)
api.security.token.secret=
2️⃣ Executar o Projeto
Com Maven instalado, rode:

bash
Copiar código
mvn spring-boot:run
A aplicação estará disponível em:

arduino
Copiar código
http://localhost:8080
🔐 Autenticação JWT
A autenticação é feita via JSON Web Token (JWT).

Fluxo:
O usuário envia suas credenciais (login/senha) para /auth/login.

A API valida as credenciais e gera um token JWT.

O cliente deve enviar o token em todas as requisições subsequentes no cabeçalho:

makefile
Copiar código
Authorization: Bearer <token>
O token é validado pelo filtro SecurityFilter.java, configurado em ConfiguracaoSeguranca.java.

📍 Endpoints Principais
🔑 Autenticação
Método	Endpoint	Descrição
POST	/auth/login	Autentica o usuário e retorna o token JWT

👤 Usuário
Método	Endpoint	Descrição
GET	/usuarios	Lista todos os usuários
GET	/usuarios/{id}	Busca um usuário específico
POST	/usuarios	Cria um novo usuário
PUT	/usuarios/{id}	Atualiza os dados de um usuário
DELETE	/usuarios/{id}	Remove um usuário

💬 Contato
Método	Endpoint	Descrição
GET	/contatos	Lista todos os contatos
POST	/contatos	Envia uma nova mensagem de contato

🚨 Denúncia
Método	Endpoint	Descrição
GET	/denuncias	Lista todas as denúncias
POST	/denuncias	Registra uma nova denúncia

🧱 Camadas da Aplicação
Controller → Recebe e responde requisições HTTP.

Service → Contém a lógica de negócio.

Repository → Acesso ao banco de dados via Spring Data JPA.

Mapper → Converte objetos (DTO ↔ Entity).

Security → Autenticação e autorização JWT.

Exception → Tratamento centralizado de erros.

⚠️ Boas Práticas e Cuidados
Não utilize a chave JWT padrão (12345678) em produção.
Gere uma chave segura e armazene em variável de ambiente.

Configure corretamente o CORS em WebConfig.java.

Use perfis (application-dev.properties, application-prod.properties) para ambientes diferentes.

Mantenha o banco de dados com usuários e permissões limitadas.

🧪 Testes
Para rodar os testes (quando implementados):

bash
Copiar código
mvn test