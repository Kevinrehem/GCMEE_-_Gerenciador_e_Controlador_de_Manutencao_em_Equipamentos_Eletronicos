## GCMEE - Gerenciador e Controlador de Manutenção em Equipamentos Eletrônicos 🚀

Projeto de exemplo (backend em Spring Boot + frontend com Vite) usado para gerenciamento de manutenção em equipamentos eletronicos através de OS e procedimentos que podem ser executadas em equipamentos de clientes cadastrados pelo usuário.

Este repositório contém duas partes principais:

- `backend/` — API REST escrita em Java com Spring Boot. Contém modelos, DTOs, repositórios, serviços e controllers para entidades como Customer, Equipment, Procedure, ServiceOrder e Technician.
- `frontend/` — aplicação web leve criada com Vite (React). Serve como interface do usuário para consumir a API.

## Tecnologias 🛠️

- Java 21 e Spring Boot
- Maven (wrapper incluído: `mvnw` / `mvnw.cmd`)
## GCMEE — Gerenciador de Chamados Técnicos (documentação de execução e API)

Este repositório contém um sistema simples de gerenciamento de manutenção: backend em Spring Boot (Java + Maven) e frontend em React (Vite). Abaixo estão instruções claras de execução (incluindo o `Start_GCMEE.bat`), indicação de onde configurar a conexão com o banco e a documentação completa dos endpoints do backend com exemplos JSON.

Links importantes
- [Configuração da aplicação](./backend/src/main/resources/application.properties)
- [Script de criação do banco](./DatabaseCreationScript.sql) (use antes de rodar, se necessário)


## Execução automática (arquivo `Start_GCMEE.bat`)

O arquivo `Start_GCMEE.bat` na raiz do projeto abre duas janelas do Windows: uma para o backend (executando o JAR) e outra para o frontend (`npm run dev`).

Antes de usar `Start_GCMEE.bat`, faça estes passos obrigatórios (ordem recomendada):

1) Preparar o banco de dados
   - Rode o script `DatabaseCreationScript.sql` no servidor escolhido (laboratório ou casa) dentro do database `aula` — este arquivo está na raiz do repositório: `DatabaseCreationScript.sql`.
   - Ajuste, se desejar, para criar um schema com seu usuário.

2) Ajustar configuração do backend
   - Edite `backend/src/main/resources/application.properties` e preencha as propriedades de conexão (url, username, password) e, se quiser, a porta do servidor (`server.port`).

3) Gerar o JAR do backend (necessário para `Start_GCMEE.bat`)
   - No PowerShell (na raiz do projeto):

```powershell
cd backend
.\mvnw.cmd clean package -DskipTests
```

   - Após o `package`, o JAR estará em `backend/target/` (por exemplo `onboardingsites-0.0.1-SNAPSHOT.jar`).

4) Instalar dependências do frontend
   - Ainda no PowerShell:

```powershell
cd frontend
npm install
```

5) Executar o `Start_GCMEE.bat`
   - Voltando para a raiz do projeto:

```powershell
cd ..\
.\Start_GCMEE.bat
```

Observações sobre `Start_GCMEE.bat`:
- Ele chama `java -jar target/onboardingsites-0.0.1-SNAPSHOT.jar` dentro da pasta `backend` — portanto, o JAR precisa existir antes.
- Ele também chama `npm run dev` dentro da pasta `frontend` — por isso é necessário executar `npm install` antes.

Alternativas (modo desenvolvimento):
- Rodar o backend direto com o Maven (mais conveniente durante desenvolvimento):

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

- Rodar o frontend diretamente (após `npm install`):

```powershell
cd frontend
npm run dev
```

## Observações sobre porta / URL do backend usada pelo frontend

O frontend uses a variável `VITE_API_BASE` (arquivo `.env` ou variáveis de ambiente) para montar as chamadas HTTP. Por padrão o código contém:

- Valor default: `http://localhost:8080` (arquivo: `frontend/src/services/api.js`)
- O `application.properties` do backend neste repositório usa por padrão `server.port=8081`.

São duas formas de garantir que frontend e backend conversem corretamente:

1) Ajustar `backend/src/main/resources/application.properties` para `server.port=8080` (se preferir manter frontend sem variáveis).
2) Ou criar um arquivo `frontend/.env` com a linha:

```
VITE_API_BASE=http://localhost:8081
```

Recomendo criar `frontend/.env` apontando para a porta do backend definida em `application.properties`.

## Como usar a aplicação pelo Frontend — passo a passo rápido

1) Abra a interface no endereço que o Vite informar no terminal (normalmente `http://localhost:5173`).
2) Cadastre um Customer (Clientes) — ir à página "Customers" → "Novo Cliente" → preencher nome, email, telefone → salvar.
3) Cadastre Equipamentos (Equipments) — vá em "Equipamentos" → "Novo Equipamento" → selecione o Customer já cadastrado como owner e o tipo de equipamento (LAPTOP / DESKTOP / MONITOR).
4) Cadastre Procedimentos (Procedures) — vá em "Procedures" → adicionar nome, descrição e preço.
5) Cadastre Técnicos (Technicians) — vá em "Technicians" → adicionar nome.
6) Crie uma Ordem de Serviço (Service Orders) — vá em "Service Orders" → novo chamado: selecione Technician, Equipment, marque os Procedures aplicáveis e selecione o status (ON_HOLD, IN_PROGRESS, AWAITING_PAYMENT, PAID, CANCELLED). Salve.
7) Use listagens nas páginas para editar ou deletar registros (cada linha tem ações de editar/remover).

Dica: crie primeiro Customer → Equipment → Technician/Procedure → ServiceOrder para evitar erros de referência (ServiceOrder precisa de IDs de Technician, Equipment e Procedure).

## Documentação completa dos endpoints do backend (ponto-a-ponto)

Base: o backend expõe endpoints com prefixo `/app/{resource}` e os caminhos são padronizados: `/select-all`, `/create`, `/update`, `/delete/{id}`.

Nota: os exemplos abaixo usam `http://localhost:8081` como backend; ajuste para o que estiver em `application.properties` ou `VITE_API_BASE`.

1) Customers
- GET /app/customer/select-all
  - Descrição: lista todos os customers.
  - Retorno: array de objetos CustomerGetDTO
  - Exemplo de resposta (200):

```json
[
  { "id": 1, "name": "João Silva", "phoneNumber": "(31) 99999-0000", "email": "joao@example.com" }
]
```

- POST /app/customer/create
  - Descrição: cria um customer.
  - Body (JSON) — `CustomerCreateDTO`:

```json
{
  "name": "Maria Souza",
  "email": "maria@example.com",
  "phoneNumber": "(31) 98888-0000"
}
```

  - Sucesso: retorna 201 e corpo com mensagem. Erros retornam 400.

- PUT /app/customer/update
  - Descrição: atualiza um customer existente.
  - Body (JSON) — `CustomerUpdateDTO`:

```json
{
  "id": 1,
  "name": "Maria Souza Silva",
  "email": "maria.silva@example.com",
  "phoneNumber": "(31) 98888-0000"
}
```

- DELETE /app/customer/delete/{id}
  - Descrição: remove um customer por id.
  - Exemplo: DELETE `/app/customer/delete/1`

2) Equipments
- GET /app/equipment/select-all
  - Retorno: array `EquipmentGetDTO` com owner embutido (CustomerGetDTO).
  - Exemplo de resposta:

```json
[
  {
    "id": 10,
    "name": "Dell Inspiron",
    "equipType": "LAPTOP",
    "owner": { "id": 1, "name": "João Silva", "phoneNumber": "(31) 99999-0000", "email": "joao@example.com" }
  }
]
```

- POST /app/equipment/create
  - Body (`EquipmentCreateDTO`):

```json
{
  "name": "Dell Inspiron",
  "customerId": 1,
  "equipType": "LAPTOP"
}
```

- PUT /app/equipment/update
  - Body (`EquipmentUpdateDTO`):

```json
{
  "id": 10,
  "name": "Dell Inspiron 5000",
  "customerId": 1,
  "equipType": "LAPTOP"
}
```

- DELETE /app/equipment/delete/{id}

Observação: `equipType` aceita os valores do enum: `LAPTOP`, `DESKTOP`, `MONITOR`.

3) Procedures
- GET /app/procedure/select-all
  - Retorna lista de `ProcedureGetDTO`.
  - Exemplo de resposta:

```json
[
  { "id": 100, "name": "Troca de HD", "description": "Substituição do HD por SSD", "price": 250.0 }
]
```

- POST /app/procedure/create
  - Body (`ProcedureCreateDTO`):

```json
{
  "name": "Troca de HD",
  "description": "Substituição do HD por SSD",
  "price": 250.0
}
```

- PUT /app/procedure/update
  - Body (`ProcedureUpdateDTO`):

```json
{
  "id": 100,
  "name": "Troca de SSD",
  "description": "Instalação de SSD",
  "price": 300.0
}
```

- DELETE /app/procedure/delete/{id}

4) Technicians
- GET /app/technician/select-all
  - Retorna lista de `TechnicianGetDTO`.
  - Exemplo:

```json
[
  { "id": 5, "name": "Carlos Pereira" }
]
```

- POST /app/technician/create
  - Body (`TechnicianCreateDTO`):

```json
{
  "name": "Carlos Pereira"
}
```

- PUT /app/technician/update
  - Body (`TechnicianUpdateDTO`):

```json
{
  "id": 5,
  "name": "Carlos P."
}
```

- DELETE /app/technician/delete/{id}

5) Service Orders (Ordens de Serviço)
- GET /app/service-order/select-all
  - Retorna lista de `ServiceOrderGetDTO`. Cada item retorna id, price, technician, equipment, procedures (lista) e status.
  - Exemplo (simplificado):

```json
[
  {
    "id": 200,
    "price": 550.0,
    "technician": { "id": 5, "name": "Carlos Pereira" },
    "equipment": { "id": 10, "name": "Dell Inspiron", "equipType": "LAPTOP", "owner": { "id": 1, "name": "João Silva", "phoneNumber": "(31) 99999-0000", "email": "joao@example.com" } },
    "procedures": [ { "id": 100, "name": "Troca de HD", "description": "...", "price": 250.0 } ],
    "serviceOrderStatus": "IN_PROGRESS"
  }
]
```

- POST /app/service-order/create
  - Body (`ServiceOrderCreateDTO`):

```json
{
  "technicianId": 5,
  "equipmentId": 10,
  "procedureIds": [100],
  "serviceOrderStatus": "ON_HOLD"
}
```

  - Nota: o backend calcula o `price` agregando os procedimentos associados — no DTO de criação o preço não é enviado.

- PUT /app/service-order/update
  - Body (`ServiceOrderUpdateDTO`): (mesma estrutura da criação, mas contém `id` no DTO de update)

```json
{
  "id": 200,
  "technicianId": 5,
  "equipmentId": 10,
  "procedureIds": [100, 101],
  "serviceOrderStatus": "IN_PROGRESS"
}
```

- DELETE /app/service-order/delete/{id}

Enumerações importantes
- `EquipType`: `LAPTOP`, `DESKTOP`, `MONITOR`
- `ServiceOrderStatus`: `ON_HOLD`, `IN_PROGRESS`, `AWAITING_PAYMENT`, `PAID`, `CANCELLED`

## Testes rápidos (curl / PowerShell)

Exemplo — listar customers:

```powershell
curl.exe -Method Get http://localhost:8081/app/customer/select-all
```

Exemplo — criar procedimento (PowerShell + JSON):

```powershell
curl.exe -Method Post -Body '{"name":"Troca de HD","description":"...","price":250}' -Headers @{"Content-Type"="application/json"} http://localhost:8081/app/procedure/create
```
