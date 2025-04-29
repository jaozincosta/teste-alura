# Case Técnico - Alura - Java Spring Boot

Projeto desenvolvido como parte do processo seletivo para a vaga de Desenvolvedor Java Júnior da **Alura**.

---

## Tecnologias utilizadas

- Java 18
- Spring Boot
- Spring Data JPA
- Spring Security (bônus)
- Flyway (migração de banco de dados)
- MySQL

---

## Como rodar o projeto

1. **Clone o repositório:**
```bash
git clone https://github.com/seuusuario/seurepositorio.git
```
2. Configure o banco de dados no arquivo **`application.properties`**:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/alura_case  
spring.datasource.username=seu-usuario  
spring.datasource.password=sua-senha  
spring.jpa.hibernate.ddl-auto=none  
spring.jpa.show-sql=true  
```
3. **Execute o projeto**

- Pela IDE (rodar a classe TesteAluraApplication)

- Ou via terminal:
```bash
./mvnw spring-boot:run
```
4. **Acesse a API:**
```http
http://localhost:8080
```

## Autenticação (Spring Security)
| Usuário    | Senha  | Role       |
|------------|--------|------------|
| instructor | 123456 | INSTRUCTOR |
| student    | 123456 | STUDENT    |

Endpoints protegidos para usuários com **`INSTRUCTOR`**:
- **`POST /task/new/*`**
- **`POST /course/{id}/publish`**

Endpoints acessíveis para **qualquer usuário autenticado**:
- **`GET /course`**
- **`GET /course/{id}/tasks`**

## Testes
O projeto possui testes automatizados para os principais fluxos, cobrindo:

- Criação de atividades (aberta, alternativa única e múltipla)
- Validações de regra de negócio
- Publicação de cursos
- Listagem de cursos e atividades

Execute os testes com:
```bash
./mvnw test
```

## Estrutura de diretórios
```bash
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config
│   │   ├── controller
│   │   ├── dto
│   │   ├── model
│   │   ├── repository
│   │   └── service
│   └── resources/
│       ├── application.properties
│       └── db/migration/
│           └── V1_create_tables.sql
├── test/
    └── java/com/example/demo/
       ├── service/
       │   └── TaskServiceTest.java
       └── TesteAluraApplicationTests.java

```
## Acesso

- Role INSTRUCTOR tem acesso à criação de atividades e publicação de cursos.
- Role STUDENT pode visualizar cursos e atividades.
- A autenticação está configurada com Spring Security usando usuários em memória para fins de teste.
