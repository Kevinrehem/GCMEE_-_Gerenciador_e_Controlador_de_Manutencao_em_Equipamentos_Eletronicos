## GCMEE - Gerenciador e Controlador de Manutenção em Equipamentos Eletrônicos 🚀

Projeto de exemplo (backend em Spring Boot + frontend com Vite) usado para gerenciamento de manutenção em equipamentos eletronicos através de OS e procedimentos que podem ser executadas em equipamentos de clientes cadastrados pelo usuário.

Este repositório contém duas partes principais:

- `backend/` — API REST escrita em Java com Spring Boot. Contém modelos, DTOs, repositórios, serviços e controllers para entidades como Customer, Equipment, Procedure, ServiceOrder e Technician.
- `frontend/` — aplicação web leve criada com Vite (React). Serve como interface do usuário para consumir a API.

## Tecnologias 🛠️

- Java 21 e Spring Boot
- Maven (wrapper incluído: `mvnw` / `mvnw.cmd`)
- Node.js + npm (para o frontend)
- Vite + React

Obs: use Java 21 e Node 16+ para melhor compatibilidade.

## Estrutura do projeto (resumo) 📁

- `backend/`
  - `pom.xml` — configuração Maven
  - `src/main/java/com/jacare/onboardingsites/` — código fonte Java
    - `controller/` — endpoints REST (CustomerController, EquipmentController, ProcedureController, ServiceOrderController, TechnicianController)
    - `dto/` — objetos de transferência (Create/Get/Update DTOs por entidade)
    - `model/` — entidades do domínio (Customer, Equipment, Procedure, ServiceOrder, Technician)
    - `repository/` — interfaces Spring Data JPA
    - `service/` — lógica de negócio
  - `src/main/resources/application.properties` — configuração da aplicação (porta, datasource, etc.)

- `frontend/`
  - `package.json` — dependências e scripts
  - `index.html`, `src/` — app React

## Visão geral do que o código faz 🔍

O backend implementa uma API REST com operações CRUD para as entidades mencionadas. Cada entidade tem:

- Model: representa a entidade persistida.
- DTOs: objetos para comunicação (criação, retorno e atualização).
- Repository: abstração de acesso a dados (usando Spring Data JPA).
- Service: contém regras de negócio e validações.
- Controller: expõe endpoints HTTP para o frontend consumir.

O frontend consome esses endpoints e apresenta a interface para criar, listar e editar registros.

## Rodando localmente (Windows PowerShell) ⚙️

Recomendações: abra o PowerShell com a pasta do projeto como diretório atual.

Backend (desenvolvimento):

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Isso compilará e iniciará a API. Se preferir empacotar em um JAR e executar:

```powershell
cd backend
.\mvnw.cmd clean package -DskipTests
java -jar target\*.jar
```

Para rodar os testes unitários:

```powershell
cd backend
.\mvnw.cmd test
```

Frontend (desenvolvimento):

```powershell
cd frontend
npm install
npm run dev
```

Frontend (build para produção):

```powershell
cd frontend
npm run build
# O resultado ficará em `dist/` (serve com um servidor static ou configure integração com o backend)
```

## Configurações importantes 🔧

Edite `backend/src/main/resources/application.properties` para ajustar porta, datasource, credenciais ou outras propriedades.

Exemplos típicos de propriedades que podem existir:

- `server.port=8081`
- `spring.datasource.url=jdbc:...`
- `spring.jpa.hibernate.ddl-auto=update`

Se você alterar a porta do backend, atualize também o endereço usado pelo frontend para as requisições HTTP (ver `src` do frontend).

## Padrão de endpoints (API) — exemplo 🔗

Os controllers implementam endpoints REST conforme padrão comum. Exemplo de rotas esperadas (ajuste conforme os RequestMappings do projeto):

- Customers
  - GET  /api/customers        — listar todos
  - GET  /api/customers/{id}   — obter por id
  - POST /api/customers        — criar
  - PUT  /api/customers/{id}   — atualizar
  - DELETE /api/customers/{id} — remover

- Equipments, Procedures, Technicians, ServiceOrders seguem um padrão similar em `/api/{resource}`.

Você pode testar usando o curl, Postman ou uma extensão REST no VS Code. Exemplo com curl (PowerShell):

```powershell
curl.exe -Method Get http://localhost:8081/api/customers
```

Obs: o caminho exato (`/api/...`) deve ser confirmado nos arquivos dos controllers caso tenha sido usado outro prefixo.

## Arquitetura & fluxos 🧭

- Controller -> Service -> Repository -> Database
- DTOs são usados para separar a camada de exposição (API) do modelo de domínio.

Isso facilita validação, testes e evolução de API sem expor diretamente as entidades JPA.

## Sugestões de comentários no código (em Português) 💬

Adicione comentários claros e curtos nas classes e métodos importantes. Exemplos que você pode inserir nos arquivos `controller`, `service` e `model`:

Controller (exemplo genérico):

```java
// Controller responsável por expor os endpoints REST para a entidade Customer.
// Recebe DTOs, delega a lógica ao Service e retorna respostas HTTP apropriadas.
# Trabalho: Sistema CRUD — Controle de Chamados Técnicos (modelo adaptado)

Este repositório contém um projeto completo com backend (Spring Boot) e frontend (React + Vite) que já está estruturado para um sistema do tipo "Controle de Chamados Técnicos" — tema escolhido para adaptar o exercício solicitado (baseado no modelo do projeto "cadAluno_atual").

O objetivo deste README é orientar a entrega do exercício acadêmico descrito: criar um sistema CRUD seguindo a mesma estrutura do projeto de referência, com banco de dados relacional no servidor de laboratório/casa e documentação/arquivo SQL para criação das tabelas.

Resumo do tema escolhido
- Tema: Controle de Chamados Técnicos
- Entidades principais (exemplo de modelagem):
  - Technician (técnico) — id, name, contact
  - Equipment (equipamento) — id, name, equip_type, owner_id (FK -> customer / owner)
  - ServiceOrder (chamado) — id, price, technician_id (FK -> Technician), equipment_id (FK -> Equipment), status
  - Procedure (procedimento/exame) — id, name, description, price
  - Customer (opcional) — id, name, email, phoneNumber (dono do equipamento)

Relações mínimas exigidas: pelo menos duas entidades relacionadas (por exemplo: ServiceOrder -> Technician e ServiceOrder -> Equipment). A modelagem acima já atende esse requisito.

Requisitos de entrega (resumido)
- O projeto deve conter frontend e backend (estrutura igual ao modelo `cadAluno_atual`).
- Deve implementar operações CRUD (Create, Read, Update, Delete) para as entidades.
- O banco de dados relacional deve ser criado no servidor especificado e dentro do database `aula` em um schema com o seu nome de usuário.
- Devem ser incluídos:
  - Script SQL para criação das tabelas e seeds (arquivo: `backend/db/<seu_usuario>_schema.sql`).
  - Código-fonte do sistema (todo o diretório do projeto — frontend e backend).
  - Arquivo de configuração com as credenciais de conexão (padrão: `backend/src/main/resources/application.properties`) — não coloque credenciais sensíveis em repositório público, use placeholders e comente como preencher.
  - Documento explicativo curto (README.txt ou README_entrega.md) descrevendo o tema, entidades e como executar o sistema.

Detalhes do banco de dados (servidores)
- Servidor (laboratório): 10.90.24.54
- Servidor (casa): 200.18.128.54

Observação: o banco deve ser criado dentro do database `aula`. Dentro de `aula` crie um schema (ou owner) com o seu nome de usuário. Exemplo (substituir <usuario> pelos seus dados):

-- [Criação do schema (PostgreSQL)](./DatabaseCreationScript.sql)
-- CREATE SCHEMA IF NOT EXISTS "<usuario>";
-- SET search_path TO "<usuario>", public;

Importante: o Spring Boot (via JPA/Hibernate) pode criar ou atualizar automaticamente as tabelas a partir das entidades Java quando a aplicação for iniciada (dependendo do valor de `spring.jpa.hibernate.ddl-auto`, por exemplo `update` ou `create`). No entanto, isso só funciona se o database `aula` e o schema/usuário já existirem no servidor e as credenciais em `application.properties` tiverem permissão para criar/alterar objetos no schema. Por isso incluímos o script de criação rápido acima — [DatabaseCreationScript.sql](./DatabaseCreationScript.sql) — para garantir que o banco e o schema existam antes de rodar a aplicação.


Exemplo de conteúdo do script (resumo):
-- Tabelas: technician, equipment, procedure, service_order, customer
-- Chaves estrangeiras entre service_order.technician_id -> technician.id e service_order.equipment_id -> equipment.id
-- Tipos e constraints básicos (NOT NULL, UNIQUE quando aplicável)

Requisitos técnicos e arquivos obrigatórios
- Estrutura: manter a mesma organização (backend/ com Maven + src; frontend/ com Vite + src).
- Scripts SQL: `backend/db/<usuario>_schema.sql` (obrigatório)
- Configuração de conexão: `backend/src/main/resources/application.properties` (usar placeholders ou instruções para substituir host/port/db/user/password). Exemplo de propriedades:
```
spring.datasource.url=jdbc:postgresql://10.90.24.54:5432/aula
spring.datasource.username=<usuario>
spring.datasource.password=<senha>
spring.jpa.hibernate.ddl-auto=validate
```

- README_entrega.md (ou README.txt): explicar o tema, entidades, relacionamentos e passos para executar (criar schema, executar script SQL, iniciar backend e frontend).

Como rodar o projeto (resumo) — instruções locais
1. Configurar o banco de dados no servidor (usar o script `backend/db/<usuario>_schema.sql`).
2. Atualizar `backend/src/main/resources/application.properties` com os dados do servidor e credenciais.
3. Iniciar backend (Windows PowerShell):
```powershell
cd backend
.\mvnw.cmd spring-boot:run
```
4. Iniciar frontend:
```powershell
cd frontend
npm install
npm run dev
```
5. Acessar o frontend (porta informada pelo Vite) e testar operações CRUD nas páginas.

Observações de segurança
- Não comite senhas reais no repositório. Use placeholders no arquivo `application.properties` e inclua um `application.properties.example` com instruções.

Formato da entrega (.zip)
- Compacte a pasta do projeto, incluindo:
  - pasta do projeto (completo)
  - script SQL (`backend/db/<usuario>_schema.sql`)
  - documento explicativo (`README_entrega.md`)
- Nome do arquivo final:
  - `nome_sobrenome_tema.zip` (por exemplo: `joao_silva_controle_chamados.zip`)

Critérios de avaliação
- Estrutura e organização do projeto — 30%
- Correção e funcionalidade das operações CRUD — 30%
- Qualidade da modelagem e consistência do banco de dados — 25%
- Clareza da documentação e apresentação do tema — 15%

Boas práticas e recomendações
- Comentários: comente controladores, serviços e modelos explicando propósito e contratos (inputs/outputs).
- Validações: use `@Valid` e anotações do Bean Validation para entradas de API.
- Migrations (opcional): adicione scripts de migração (Flyway/Liquibase) para facilitar deploy/controle de versão do esquema.
- Testes: inclua ao menos alguns testes unitários para Services e um teste de integração simples para Controllers.

Checklist sugerida antes da entrega
- [X] Script SQL criado e testado no servidor `aula`.
- [X] [application.properties.example](./backend/src/main/resources/application.properties) com placeholders incluído.
- [x] README.md com instruções de execução e modelagem.
- [x] Projeto rodando localmente (backend + frontend) sem erros.
