# Minha Receita

![Status do Build do CI](https://github.com/gustavokowallski/MinhaReceita/actions/workflows/ci.yml/badge.svg)

Minha Receita é um sistema completo de gestão de receitas culinárias, construído com foco em boas práticas, regras de negócio reais e código limpo. O projeto foi idealizado para simular um ambiente de desenvolvimento backend robusto, com autenticação, permissões, domínio complexo e escalabilidade.

 ##  Problema Resolvido

Esse projeto é uma API RESTful desenvolvida com Spring Boot, projetada para gerenciar e organizar receitas culinárias de forma eficiente, permitindo que usuários cadastrados criem, consultem, atualizem e compartilhem suas receitas favoritas.

- Cadastro e edição de receitas com múltiplos ingredientes e categorias
- Sistema de reviews com nota e comentário
- Autenticação com OAuth2
- Permissões baseadas em perfil (admin, usuário comum)
- Gerenciamento de favoritos
- Busca por nome, ingredientes e categorias


## 🧩 Funcionalidades Principais

  ### 🔐 Autenticação

- OAuth2 com suporte a `{{host}}/oauth2/token`
- Somente usuários autenticados podem inserir, editar ou deletar receitas e reviews
- Admins possuem permissões estendidas para manipular ingredientes

  ### 📋 Gestão de Receitas

- Cada receita pertence a um usuário autenticado
- Receitas contêm múltiplos ingredientes e categorias
- Regras de atualização: somente campos válidos (ex: não altera se estiver em branco)
- Validação de vínculo entre usuário e receita na atualização

  ### ⭐ Favoritos

- Usuários podem favoritar e desfavoritar receitas
- Previne duplicação: não é possível favoritar a mesma receita mais de uma vez

  ### 🧪 Reviews

- Sistema de avaliação (nota + comentário)
- Só o autor do review pode deletar ou editar
- Busca paginada de reviews por receita

  ### 🥫 Ingredientes

- Buscáveis por nome (com ordenação alfabética)
- CRUD completo (restrito a admins)
- Protegido contra exclusão se estiver em uso em uma receita
- Com acesso apenas para usuários com Role de ADMIN.

---




 ### Conquistas e Aprendizados

Este projeto foi uma simulação de um desafio técnico para uma vaga júnior. Com ele, eu apliquei conceitos de domínio real e regras de negócio claras, usando código limpo, validações e tratamentos de exceções completos.

* **Automatizei um pipeline de CI/CD** utilizando GitHub Actions para fazer o build e o push da imagem da aplicação para o DockerHub de forma automatizada.

* **Refatorei o projeto por completo**, aprimorando a legibilidade do código, otimizando a performance das consultas ao banco de dados e removendo código desnecessário.

* **Tratei exceções** de forma padronizada utilizando o ExceptionHandler, tratando 100% dos cenários de erro e trabalhei com validações de dados utilizando a biblioteca Validations, garantindo que dados de entrada sejam validados antes de ser enviado a requisição.

* **Melhorei minha habilidade em testes unitários** garantindo 100% de código coberto, cobrindo testes de sucesso e testes de erros.

* **Utilizei Docker e Docker Compose** para criar ambientes de desenvolvimento e produção reproduzíveis, simplificando o processo de setup e garantindo a consistência da aplicação..

## Pontos a melhorar

- Qualidade dos commits, ao longo do projeto foi melhorando, porém um pouco incosistente.
- Aumentar a robustez dos testes com cenários mais avançados.


| Tecnologias / Práticas | Justificativa | 
| :--- | :--- | 
| **Java 21 + Spring Boot** | Padrão de mercado para APIs seguras, escaláveis e testáveis. | 
| **Spring Security + OAuth2/JWT** | Segurança baseada em tokens e controle de acesso por roles. | 
| **BCrypt** | Criptografia de senhas para garantir a segurança da API. | 
| **PostgreSQL + Spring Data JPA** | Banco de dados relacional robusto para persistência de dados. | 
| **Docker + Docker Compose** | Contêineres que garantem um ambiente de desenvolvimento isolado e replicável. | 
| **GitHub Actions** | Automação de CI/CD para build, testes e entrega contínua. | 
| **OpenAPI / Swagger UI** | Garante uma API auto-documentada e interativa para outros desenvolvedores. |  
| **JUnit 5 + Mockito** | Ferramentas de testes automatizados para validar a estabilidade e funcionalidade da aplicação. | 
| **Injeção de dependência via construtor** | Facilita testes e segue o princípio de imutabilidade e boas práticas do Spring. | 
| **DTOs personalizados** | Evita vazamento de dados e mantém o código desacoplado. | 
| **Validações com Bean Validation** | Garante integridade nos dados recebidos da API. | 
| **Organização por domínio** | Código limpo, modular e fácil de manter ou escalar. | 
| **Git e Troca de Branches** | Facilita o controle de versão, colaboração em equipe e revisão de código. | 

---
## 🚀 Próximos Passos

📸 Adicionar upload de imagens nas receitas.

🏆 Criar ranking de usuários baseado em engajamento (receitas e reviews).

🔔 Implementar notificações para novos reviews e favoritos.

---
### Rodando o Projeto

O ambiente completo do projeto pode ser iniciado com um único comando, graças ao **Docker Compose**.

**Pré-requisitos:**

* Docker Desktop (Garanta que esteja instalado)
* Git

**Passos:**

1. **Clone o repositório e navegue até a pasta:**
 
```bash
 git clone https://github.com/gustavokowallski/MinhaReceita.git
cd MinhaReceita
```
2. **Roda o comando dentro da página MinhaReceita:**

```bash
docker compose up 
```
A API estará disponível em `http://localhost:8080`.

3. **Consuma a API:**

### Consumindo pelo Postman (Recomendado)
* **Coleção Postman:** Clique no botão abaixo para importar e testar os endpoints da API diretamente no Postman:
    [![Run in Postman](https://run.pstmn.io/button.svg)](https://nawszera.postman.co/workspace/nawszera's-Workspace~ea6779bc-203d-4c77-8395-e87a3f1091fa/collection/45108000-4940dac4-9643-4a53-b591-5ad13ab61698?action=share&creator=45108000&active-environment=45108000-ee357952-f911-405a-9337-066beac8e080)

* **Coleção Postman:** Clique no botão abaixo para importar os váriaveis de ambiente:
    [![Enviroment](https://run.pstmn.io/button.svg)](https://nawszera.postman.co/workspace/nawszera's-Workspace~ea6779bc-203d-4c77-8395-e87a3f1091fa/environment/45108000-ee357952-f911-405a-9337-066beac8e080?action=share&creator=45108000&active-environment=45108000-ee357952-f911-405a-9337-066beac8e080)
```bash
Caso prefira pegue o token de authenticação e abra o Swagger para testar a API no postman
```

### Documentação da API
* Acesse o Swagger UI em: `http://localhost:8080/swagger-ui/index.html`
---

* **Credenciais de Teste:**

**Admin:**

```bash
email: admin@gmail.com
password: 123456
```
**Usuário:**

```bash
email: user@gmail.com
password: 123456
```
