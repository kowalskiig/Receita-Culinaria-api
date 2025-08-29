#  Minha Receita

![CI](https://github.com/gustavokowallski/MinhaReceita/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-brightgreen)
![Docker](https://img.shields.io/badge/docker-enabled-blue)
![PostgreSQL](https://img.shields.io/badge/postgres-db-blue)

**Minha Receita** é uma API RESTful para gestão de receitas culinárias com autenticação, controle de permissões, sistema de favoritos e reviews, construída com foco em código limpo, regras de negócio reais e boas práticas de backend.  
Idealizado como um desafio técnico completo, o projeto simula um ambiente real de desenvolvimento robusto.

> 📢 Este projeto simula um desafio técnico real e mostra como aplico boas práticas de arquitetura, testes e segurança no backend Java com Spring Boot.


---

## 📌 Problema Resolvido

O sistema permite que usuários cadastrados criem, editem, consultem e compartilhem receitas com múltiplos ingredientes e categorias, além de:

- Criar reviews com nota e comentário
- Favoritar/desfavoritar receitas
- Buscar receitas por nome, ingredientes e categorias
- Atribuir permissões específicas por perfil (usuário comum vs admin)

---

## 🧩 Funcionalidades Principais

### 🔐 Autenticação e Permissões

- Login via OAuth2 (`/oauth2/token`)
- Usuários autenticados podem criar/editar/deletar receitas e reviews
- Admins têm permissões estendidas (como CRUD de ingredientes)

### 📋 Gestão de Receitas

- Cada receita pertence a um usuário
- Suporte a múltiplos ingredientes e categorias por receita
- Atualizações validadas (ex: campos obrigatórios, vínculo de dono)

### ⭐ Favoritos

- Usuários podem favoritar/desfavoritar receitas
- Impede duplicações automáticas de favoritos

### 🧪 Reviews

- Avaliação por nota + comentário
- Apenas o autor pode editar/deletar o próprio review
- Suporte a paginação de reviews por receita

### 🥫 Ingredientes

- CRUD completo (acesso restrito a admins)
- Proteção contra exclusão de ingrediente em uso
- Busca por nome com ordenação alfabética

---

## 🏆 Conquistas e Aprendizados

Este projeto foi elaborado como simulação de um desafio técnico júnior. Os principais aprendizados e entregas incluem:

- ✅ **Automatização de CI/CD** com GitHub Actions (build + push da imagem para o DockerHub)
- ✅ **Refatoração completa** para legibilidade e performance (consultas otimizadas)
- ✅ **Tratamento global de exceções** com `@ExceptionHandler`, cobrindo 100% dos cenários
- ✅ **Testes unitários** com JUnit e Mockito cobrindo fluxos de sucesso e erro (100% de cobertura)
- ✅ **Containerização com Docker e Docker Compose** para ambientes reprodutíveis

---

## 📦 Tecnologias Utilizadas

| Tecnologias / Práticas | Justificativa |
| :--- | :--- |
| **Java 21 + Spring Boot** | Backend moderno, seguro e escalável |
| **Spring Security + OAuth2/JWT** | Autenticação e autorização por roles |
| **BCrypt** | Criptografia segura de senhas |
| **PostgreSQL + Spring Data JPA** | Persistência relacional robusta |
| **Docker + Docker Compose** | Ambientes isolados e reprodutíveis |
| **GitHub Actions** | CI/CD com build, testes e entrega contínua |
| **Swagger/OpenAPI** | API auto-documentada e interativa |
| **JUnit 5 + Mockito** | Testes automatizados de lógica e validações |
| **DTOs personalizados** | Proteção de dados e desacoplamento |
| **Bean Validation** | Validação automática dos dados de entrada |
| **Injeção via construtor** | Testabilidade e boas práticas do Spring |
| **Organização por domínio** | Código modular, limpo e escalável |

---

## 🚀 Próximos Passos

- 📸 Upload de imagens nas receitas  
- 🏆 Ranking de usuários por engajamento  
- 🔔 Notificações para reviews e favoritos

---

## ⚙️ Como Rodar o Projeto (via Docker Compose)

### Pré-requisitos
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Git

### Passos

```bash
# Clone o repositório
git clone https://github.com/gustavokowallski/MinhaReceita.git
cd MinhaReceita

# Suba os containers
docker compose up

```
## 🌐 Acesso à Aplicação

A aplicação estará disponível em:

➡️ [http://localhost:8080](http://localhost:8080)  
➡️ [Swagger UI](http://localhost:8080/swagger-ui/index.html)

---

## 📬 Testando a API

### ✅ Via Postman (Recomendado)

- **Coleção de endpoints + variáveis de ambiente:**  
  [![Run in Postman](https://run.pstmn.io/button.svg)](https://nawszera.postman.co/workspace/nawszera's-Workspace~ea6779bc-203d-4c77-8395-e87a3f1091fa/collection/45108000-4940dac4-9643-4a53-b591-5ad13ab61698?action=share&creator=45108000&active-environment=45108000-ee357952-f911-405a-9337-066beac8e080)

### 📖 Via Swagger

- Acesse: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
- Utilize o token OAuth2 no botão **Authorize** para testar rotas protegidas

---

## 🔐 Credenciais de Teste

### 👑 Admin
```bash
email: admin@gmail.com  
senha: 123456
```
### 👤 User
```bash
email: user@gmail.com    
senha: 123456
```
