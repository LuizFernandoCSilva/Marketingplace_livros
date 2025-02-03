# 📚 Marketplace para Venda de Livros Usados

Este projeto é uma aplicação de marketplace para venda de livros usados, desenvolvida com **Java e Spring Boot** utilizando **arquitetura de microserviços**. A aplicação permite que usuários cadastrem seus livros para venda, realizem buscas, façam compras e recebam notificações automáticas por e-mail.

## 🚀 Tecnologias Utilizadas

- **Linguagem**: Java 17
- **Framework**: Spring Boot
- **Autenticação e Segurança**: JWT Token, Spring Security, BCrypt
- **Banco de Dados**: PostgreSQL
- **ORM**: JPA/Hibernate
- **Mensageria**: RabbitMQ
- **Pagamento**: Stripe API
- **Envio de E-mails**: SMTP
- **Gerenciamento de Dependências**: Maven
- **Ferramentas Extras**: Lombok

## ⚙️ Funcionalidades

✅ **Cadastro e autenticação de usuários**
✅ **Gestão de produtos (CRUD de livros)**
✅ **Sistema de busca avançado**
✅ **Compra de livros com pagamento via Stripe**
✅ **Envio automático de e-mails para usuários**
✅ **Comunicação entre microserviços via RabbitMQ**
✅ **APIs RESTful otimizadas para eficiência e segurança**

## 🛠️ Como Executar o Projeto

### 🔧 Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:
- [Java 17](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/) (para rodar o PostgreSQL e RabbitMQ, opcional)

### 📥 Clonando o Repositório
```bash
  git clone https://github.com/LuizFernandoCSilva/Marketingplace_livros
  cd marketplace_livros
```

### 📦 Configurando o Banco de Dados com Docker (opcional)
Se quiser rodar o PostgreSQL e o RabbitMQ via Docker, execute:
```bash
docker-compose up -d
```

### 🔑 Configurando as Variáveis de Ambiente
No diretório raiz do projeto, crie um arquivo `.env` e adicione:
```
DB_URL=jdbc:postgresql://localhost:5432/seudb
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta
SMTP_HOST=smtp.seuprovedor.com
SMTP_PORT=587
SMTP_USERNAME=seu_email
SMTP_PASSWORD=sua_senha
STRIPE_SECRET_KEY=sua_chave_stripe
```

### ▶️ Executando a Aplicação

1. **Compile o projeto:**
```bash
mvn clean install
```

2. **Execute a aplicação:**
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.


## 📄 Licença
Este projeto está sob a licença MIT. Sinta-se livre para usá-lo e contribuir! 😊


📩 Caso tenha dúvidas ou sugestões, me chame no LinkedIn ou abra uma issue no repositório!

---

💡 *Se este projeto te ajudou, deixe uma ⭐ no GitHub!*
