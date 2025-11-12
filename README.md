# GCMEE - Gerenciador e Controlador de Manutenção em Equipamentos Eletrônicos

O GCMEE é um sistema full-stack projetado para gerenciar o fluxo completo de manutenção de equipamentos eletrônicos. Ele permite o cadastro de clientes, técnicos, equipamentos e procedimentos, culminando na criação e gerenciamento de Ordens de Serviço.

## 📖 Índice

  * [Tecnologias Utilizadas](https://www.google.com/search?q=%23-tecnologias-utilizadas)
  * [🚀 Instalação e Execução](https://www.google.com/search?q=%23-instala%C3%A7%C3%A3o-e-execu%C3%A7%C3%A3o)
      * [Pré-requisitos](https://www.google.com/search?q=%23pr%C3%A9-requisitos)
      * [Backend (Spring Boot)](https://www.google.com/search?q=%23backend-spring-boot)
      * [Frontend (React + Vite)](https://www.google.com/search?q=%23frontend-react--vite)
  * [🔌 Documentação da API (Endpoints)](https://www.google.com/search?q=%23-documenta%C3%A7%C3%A3o-da-api-endpoints)
      * [Clientes (Customer)](https://www.google.com/search?q=%23clientes-customer)
      * [Técnicos (Technician)](https://www.google.com/search?q=%23t%C3%A9cnicos-technician)
      * [Equipamentos (Equipment)](https://www.google.com/search?q=%23equipamentos-equipment)
      * [Procedimentos (Procedure)](https://www.google.com/search?q=%23procedimentos-procedure)
      * [Ordens de Serviço (Service Order)](https://www.google.com/search?q=%23ordens-de-servi%C3%A7o-service-order)
  * [💻 Manual de Uso (Frontend)](https://www.google.com/search?q=%23-manual-de-uso-frontend)

-----

## 🛠️ Tecnologias Utilizadas

### Backend

  * **Java 21**: Linguagem principal da aplicação.
  * **Spring Boot 3.5.7**: Framework para criação da API REST.
  * **Spring Data JPA**: Para persistência de dados e comunicação com o banco.
  * **PostgreSQL**: Banco de dados relacional utilizado.
  * **Maven**: Gerenciador de dependências do backend.

### Frontend

  * **React 19**: Biblioteca para construção da interface de usuário.
  * **Vite**: Ferramenta de build e servidor de desenvolvimento frontend.
  * **React Router DOM**: Para gerenciamento de rotas no lado do cliente.
  * **Tailwind CSS**: Framework de estilização CSS utilitário.
  * **Radix UI**: Componentes de UI acessíveis e de baixo nível.

-----

## 🚀 Instalação e Execução

### Pré-requisitos

  * **Java JDK 21**
  * **Apache Maven**
  * **Node.js (v18 ou superior)**
  * **PostgreSQL** em execução

### Backend (Spring Boot)

1.  **Clone o repositório** e navegue até a pasta do backend.
2.  **Configure o Banco de Dados**:
      * Edite `src/main/resources/application.properties` com suas credenciais do PostgreSQL:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_seu_banco
        spring.datasource.username=seu_usuario
        spring.datasource.password=sua_senha
        ```
3.  **Execute a aplicação**:
    ```bash
    mvn spring-boot:run
    ```
      * A API estará disponível em `http://localhost:8080`.

### Frontend (React + Vite)

1.  **Navegue até a pasta do frontend**.
2.  **Instale as dependências**:
    ```bash
    npm install
    ```
3.  **Execute o servidor de desenvolvimento**:
    ```bash
    npm run dev
    ```
      * O frontend estará acessível em `http://localhost:5173`.

-----

## 💻 Manual de Uso (Frontend)

A interface do usuário (UI) foi desenvolvida para ser intuitiva. Abaixo estão as funcionalidades principais acessíveis via navegador:

1.  **Cadastro de Entidades Base**:

      * Acesse as abas **Clientes** e **Técnicos** para registrar as pessoas envolvidas.
      * Use a aba **Equipamentos** para cadastrar o hardware trazido pelo cliente, vinculando-o ao proprietário.
      * Defina os preços e tipos de serviço na aba **Procedimentos**.

2.  **Gerenciamento de Serviços**:

      * Para iniciar um trabalho, vá em **Ordens de Serviço** e clique em "Criar Nova".
      * Selecione o Cliente e o Equipamento (o sistema deve filtrar equipamentos do cliente selecionado).
      * Descreva o defeito relatado.
      * Atribua um Técnico responsável.

3.  **Execução e Finalização**:

      * O técnico pode acessar a O.S. para alterar o status (ex: "Em Análise" -\> "Aguardando Peça" -\> "Finalizado").
      * Ao concluir, o técnico insere o **Laudo Técnico** explicando o que foi feito.
      * O sistema permite a visualização do histórico de serviços de cada equipamento.

-----

## 🔌 Documentação da API (Endpoints)

**URL Base:** `http://localhost:8080`

### Clientes (Customer)

**Rota:** `/app/customer`

  * **`GET /select-all`**

      * Lista todos os clientes.
      * `curl -X GET "http://localhost:8080/app/customer/select-all"`

  * **`POST /create`**

      * Cria um novo cliente.
      * **Body:**
        ```json
        {
          "name": "João da Silva",
          "email": "joao.silva@email.com",
          "phone": "11987654321",
          "address": "Rua Exemplo, 123"
        }
        ```
      * `curl -X POST "http://localhost:8080/app/customer/create" -H "Content-Type: application/json" -d '{ "name": "João da Silva", "email": "joao@email.com", "phone": "11987654321", "address": "Rua Exemplo, 123" }'`

  * **`PUT /update`**

      * Atualiza um cliente.
      * **Body:**
        ```json
        {
          "id": 1,
          "name": "João Atualizado",
          "email": "joao.novo@email.com",
          "phone": "11999999999",
          "address": "Rua Nova, 456"
        }
        ```
      * `curl -X PUT "http://localhost:8080/app/customer/update" -H "Content-Type: application/json" -d '{ "id": 1, "name": "João Atualizado", "phone": "11999999999" }'`

  * **`DELETE /delete/{id}`**

      * Deleta um cliente.
      * `curl -X DELETE "http://localhost:8080/app/customer/delete/1"`

-----

### Técnicos (Technician)

**Rota:** `/app/technician`

  * **`GET /select-all`**

      * Lista todos os técnicos.
      * `curl -X GET "http://localhost:8080/app/technician/select-all"`

  * **`POST /create`**

      * Cadastra um técnico.
      * **Body** (Baseado em `TechnicianCreateDTO`):
        ```json
        {
          "technicianName": "Maria Oliveira",
          "technicianPhone": "11988887777",
          "technicianEmail": "maria.tec@email.com",
          "technicianPassword": "senha_segura_123",
          "technicianSpecialty": "Hardware e Redes"
        }
        ```
      * `curl -X POST "http://localhost:8080/app/technician/create" -H "Content-Type: application/json" -d '{ "technicianName": "Maria Oliveira", "technicianEmail": "maria@email.com", "technicianPassword": "123", "technicianSpecialty": "Hardware" }'`

  * **`PUT /update`**

      * Atualiza um técnico.
      * **Body** (Baseado em `TechnicianUpdateDTO`):
        ```json
        {
          "id": 1,
          "technicianName": "Maria Oliveira Souza",
          "technicianPhone": "11999998888",
          "technicianEmail": "maria.souza@email.com",
          "technicianPassword": "nova_senha_456",
          "technicianSpecialty": "Especialista Apple"
        }
        ```
      * `curl -X PUT "http://localhost:8080/app/technician/update" -H "Content-Type: application/json" -d '{ "id": 1, "technicianName": "Maria Souza", "technicianSpecialty": "Apple" }'`

  * **`DELETE /delete/{id}`**

      * Deleta um técnico.
      * `curl -X DELETE "http://localhost:8080/app/technician/delete/1"`

-----

### Equipamentos (Equipment)

**Rota:** `/app/equipment`

  * **`GET /select-all`**

      * Lista todos os equipamentos.
      * `curl -X GET "http://localhost:8080/app/equipment/select-all"`

  * **`POST /create`**

      * Cadastra um equipamento.
      * **Body:**
        ```json
        {
          "equipmentName": "Notebook Dell Inspiron",
          "equipmentModel": "I15-3583-A30P",
          "equipmentBrand": "Dell",
          "serialNumber": "SN-ABC-12345",
          "accessories": "Carregador original",
          "customerName": "João da Silva"
        }
        ```
      * `curl -X POST "http://localhost:8080/app/equipment/create" -H "Content-Type: application/json" -d '{ "equipmentName": "Notebook Dell", "customerName": "João da Silva" }'`

  * **`PUT /update`**

      * Atualiza um equipamento.
      * **Body:**
        ```json
        {
          "id": 1,
          "equipmentName": "Notebook Dell Vostro",
          "equipmentModel": "V15-3000",
          "equipmentBrand": "Dell",
          "serialNumber": "SN-XYZ-98765",
          "accessories": "Sem acessórios",
          "customerName": "João da Silva"
        }
        ```
      * `curl -X PUT "http://localhost:8080/app/equipment/update" -H "Content-Type: application/json" -d '{ "id": 1, "equipmentName": "Notebook Dell Vostro" }'`

  * **`DELETE /delete/{id}`**

      * Deleta um equipamento.
      * `curl -X DELETE "http://localhost:8080/app/equipment/delete/1"`

-----

### Procedimentos (Procedure)

**Rota:** `/app/procedure`

  * **`GET /select-all`**

      * Lista todos os procedimentos.
      * `curl -X GET "http://localhost:8080/app/procedure/select-all"`

  * **`POST /create`**

      * Cadastra um procedimento.
      * **Body:**
        ```json
        {
          "procedureName": "Limpeza Completa",
          "procedureDescription": "Limpeza interna e troca de pasta térmica.",
          "procedureCost": 80.00
        }
        ```
      * `curl -X POST "http://localhost:8080/app/procedure/create" -H "Content-Type: application/json" -d '{ "procedureName": "Limpeza", "procedureCost": 80.00 }'`

  * **`PUT /update`**

      * Atualiza um procedimento.
      * **Body:**
        ```json
        {
          "id": 1,
          "procedureName": "Limpeza Premium",
          "procedureDescription": "Limpeza, troca de pasta e organização de cabos.",
          "procedureCost": 100.00
        }
        ```
      * `curl -X PUT "http://localhost:8080/app/procedure/update" -H "Content-Type: application/json" -d '{ "id": 1, "procedureCost": 100.00 }'`

  * **`DELETE /delete/{id}`**

      * Deleta um procedimento.
      * `curl -X DELETE "http://localhost:8080/app/procedure/delete/1"`

-----

### Ordens de Serviço (Service Order)

**Rota:** `/app/service-order`

> **Nota:** Os campos abaixo são sugestões, pois os DTOs de Criação/Atualização de ServiceOrder não foram fornecidos. Verifique a implementação da classe `ServiceOrderCreateDTO` no código fonte.

  * **`GET /select-all`**

      * Lista todas as O.S.
      * `curl -X GET "http://localhost:8080/app/service-order/select-all"`

  * **`POST /create`**

      * Cria uma O.S.
      * **Body (Sugestão):**
        ```json
        {
          "customerId": 1,
          "equipmentId": 2,
          "technicianId": 1,
          "problemDescription": "Tela azul intermitente.",
          "entryDate": "2025-11-12"
        }
        ```
      * `curl -X POST "http://localhost:8080/app/service-order/create" -H "Content-Type: application/json" -d '{ "customerId": 1, "equipmentId": 2, "problemDescription": "Erro no Windows" }'`

  * **`PUT /update`**

      * Atualiza uma O.S.
      * **Body (Sugestão):**
        ```json
        {
          "id": 5,
          "status": "CONCLUIDO",
          "technicalReport": "Formatado e atualizado drivers.",
          "closingDate": "2025-11-14"
        }
        ```
      * `curl -X PUT "http://localhost:8080/app/service-order/update" -H "Content-Type: application/json" -d '{ "id": 5, "status": "CONCLUIDO" }'`

  * **`DELETE /delete/{id}`**

      * Deleta uma O.S.
      * `curl -X DELETE "http://localhost:8080/app/service-order/delete/1"`

