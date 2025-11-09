## OnboardingLandpage 🚀

Projeto de exemplo (backend em Spring Boot + frontend com Vite) usado para gerenciamento de site de onboarding.

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
- Repository: abstração de acesso a dados (provavelmente usando Spring Data JPA).
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
curl.exe -Method Get http://localhost:8080/api/customers
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
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    // Injeção do serviço que contém a lógica de negócio para Customer
    private final CustomerService customerService;

    // GET /api/customers
    // Retorna a lista de clientes (pode ser paginada no futuro)
    @GetMapping
    public ResponseEntity<List<CustomerGetDTO>> getAll() {
        ...
    }
}
```

Service (exemplo):

```java
// Serviço que implementa regras de negócio para Customer.
// - validações
// - conversões entre DTOs e entidades
// - transações
public class CustomerService {
    // Cria um novo cliente após validação dos dados
    public CustomerGetDTO create(CustomerCreateDTO dto) {
        // validar dto
        // mapear dto -> entity
        // salvar via repository
        // mapear entity -> dto de retorno
    }
}
```

Model (exemplo):

```java
// Entidade Customer mapeada para a tabela do banco.
// Inclui os campos principais, constraints e relacionamentos.
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    // nome do cliente
    private String name;
}
```

Dica: Use comentários Javadoc simples em métodos públicos para explicar inputs/outputs e efeitos colaterais (por exemplo: transações, exclusões em cascata).

## Testes ✅

O backend contém testes (pasta `src/test`). Para executá-los:

```powershell
cd backend
.\mvnw.cmd test
```

Adicione testes unitários para Services e testes de integração para Controllers utilizando `@SpringBootTest` ou `@WebMvcTest`.

## Troubleshooting (problemas comuns) 🛠️

- Porta já em uso: altere `server.port` em `application.properties` ou finalize o processo que está usando a porta.
- Erros do Maven: verifique a versão do Java (use `java -version`) e execute o Maven com a mesma JVM.
- Erros CORS ao conectar frontend: habilite CORS no backend (configure `WebMvcConfigurer` ou use `@CrossOrigin` nos controllers).

## Próximos passos sugeridos ➡️

- Adicionar documentação automática da API (Swagger/OpenAPI).
- Adicionar autenticação/autorização (Spring Security + JWT).
- Melhorar validação de entrada com `@Valid` e `javax.validation`.
- Adicionar testes e CI (GitHub Actions).

## Autor / Licença ©️

Projeto de classe / demonstração. Modifique conforme necessário.

---

Se quiser, eu também posso inserir comentários diretamente nos arquivos do backend (controllers/services/models) — diga quais arquivos você quer que eu comente e eu aplico as mudanças.# OnboardingLandpage
