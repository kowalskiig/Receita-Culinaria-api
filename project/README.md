  # 🍽️ MinhaReceita
---

MinhaReceita é um sistema completo de gestão de receitas culinárias, construído com foco em boas práticas, regras de negócio reais e código limpo. O projeto foi idealizado para simular um ambiente de desenvolvimento backend robusto, com autenticação, permissões, domínio complexo, escalabilidade e melhores práticas no desenvolvimento.

---

  ## 🧠 Problema Resolvido

Esse projeto é uma API RESTful desenvolvida com Spring Boot, projetada para gerenciar e organizar receitas culinárias de forma eficiente, permitindo que usuários cadastrados criem, consultem, atualizem e compartilhem suas receitas favoritas.

- Cadastro e edição de receitas com múltiplos ingredientes e categorias
- Sistema de reviews com nota e comentário
- Autenticação com OAuth2
- Permissões baseadas em perfil (admin, usuário comum)
- Gerenciamento de favoritos
- Busca por nome, ingredientes e categorias

---

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


  ## 🧠 Stacks e Práticas

| Tecnologias / Práticas       | Justificativa                                                                 |
|-----------------------------|------------------------------------------------------------------------------|
| **Java 21 + Spring Boot 3.5.3**    | Padrão de mercado para APIs seguras, escaláveis e testáveis.                |
| **Spring Security + JWT**    | Segurança baseada em tokens, controle de acesso por roles.                 |
| **DTOs personalizados**      | Evita vazamento de dados e mantém o código desacoplado.                    |
| **Validações com Bean Validation** | Garante integridade nos dados recebidos da API.                          |
| **Projeções com interfaces** | Eficiência na busca com menos overhead de memória.                         |
| **Injeção de dependência via construtor** | Facilita testes, segue o princípio de imutabilidade e boas práticas do spring.        |
| **Tratamento global de erros** | Retorna respostas padronizadas para exceções comuns.                      |
| **Troca branchs para cada tarefa** | Facilita o controle de versão, a colaboração em equipe e a revisão de código, isolando as funcionalidades.|
| **BCrypt** | Deixar a API mais segura cryptografando a senha.|
| **Organização por domínio**  | Código limpo, modular e fácil de manter ou escalar.
---

  ## 🧠 Documentação 
| Tecnologias / Práticas       | Justificativa                                                                 |
|-----------------------------|------------------------------------------------------------------------------|
| OpenAPI / Swagger UI  | Garante uma API auto-documentada, interativa e de fácil consumo para outros desenvolvedores. Facilita a colaboração e testes.                |


  ## 🚀 O que aprendi

- Aprimorei minhas consultas SQL, buscando sempre o melhor desempenho do API e do DB.
- Entidades-relacionamentos, com tabelas de associação e todos os tipos de relacionamentos.
- Separar responsabilidade com DTOs específicos.
- Tratar exceções de forma padronizada.
- Trabalhar com consistência transacional em cenários sensíveis.
- Ler e refatorar o código para que ele siga boas práticas e deixá-lo com boa legibilidade.
- Melhora de raciocinio para regras de négocio e implementações.

  ## 🧠 Pontos a melhorar

- Qualidade dos commits, ao longo do projeto foi melhorando, porém um pouco incosistente.
- Uso de branchs, foi um ponto bem confuso pois foi a primeira vez que eu usei.
- OpenApi e Documentação, também foi uma coisa que usei pela primeira vez, então tipo algumas dúvidas na hora da implementação.

---



## 👨🏻‍💻 Como se fosse uma vaga...

> **Vaga: Desenvolvedor Backend Java Júnior**
>
> Buscamos um dev backend que tenha domínio em Spring Boot, boas práticas com JPA, autenticação, arquitetura limpa e foco em regras de negócio. Desejável experiência com segurança e controle de acesso baseado em usuários.
>
> ⚠️ Desafio técnico: crie um sistema que gerencie receitas, com controle de usuários, ingredientes, reviews e favoritos.

### Como eu aplicaria:

- ✅ Projeto com controle de domínio real e regras claras
- ✅ Código limpo com DTOs separados para cada operação
- ✅ Simulação de usuários e permissões reais com Spring Security
- ✅ Organização em pacotes por domínio
- ✅ Validações manuais e automáticas
- ✅ README com foco em valor, clareza e processo seletivo

---

## 📚 Próximos Passos

- [ ] Implementar testes automatizados com JUnit e Mockito
- [ ] Refatorar busca com Specification
- [ ] Integração com imagem em nuvem (S3)
- [ ] Adicionar autenticação via redes sociais (Google, GitHub)

---

## 📎 Rodando o Projeto


**Pré-requisitos:**

* Java Development Kit (JDK) 21+
* Maven (ou utilize o wrapper `./mvnw`)

1. Clone o repositório e entrando na pasta
```bash
git clone https://github.com/gustavokowallski/MinhaReceita.git
cd MinhaReceita
```
3.  **Executar o projeto:**
    ```bash
    ./mvnw spring-boot:run
    ```
    O projeto será executado em `http://localhost:8080` por padrão. 

4.  **Abra o swagger:**

  No link: `http://localhost:8080/swagger-ui/index.html#/`


5.  **Abra o Postmanr:**

[**Coleção Postman GameList API**](https://nawszera.postman.co/workspace/nawszera's-Workspace~ea6779bc-203d-4c77-8395-e87a3f1091fa/request/45108000-defeb9d6-ec73-4875-b3e0-e6e1bfc8b533?action=share&creator=45108000&ctx=documentation&active-environment=45108000-ee357952-f911-405a-9337-066beac8e080)

5.  **Teste com os diferentes perfis para testar as roles:**

**email**: `admin@gmail.com` /
**email**: `user@gmail.com`


**password** `123456`
