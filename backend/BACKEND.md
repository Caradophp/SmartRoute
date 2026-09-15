# BACKEND

## Instruções para rodar o projeto do backend

### Requisitos

- Java 21 ou superior
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code, etc.)
- Banco de dados PostgreSQL instalado e configurado
- Maven 3.8.0 ou superior
- Git

> Consulte o arquivo de configuração do banco de dados: [Configuração do banco](BANCO.md)

### Configurar application.yaml

1. Acesse a pasta `src/main/resources`
2. Abra o arquivo `application.yaml`
3. Configure as seguintes propriedades:
   - `spring.datasource.username`: seu usuário do PostgreSQL
   - `spring.datasource.password`: sua senha do PostgreSQL
   - `spring.datasource.url`: URL de conexão do banco de dados (padrão: `jdbc:postgresql://localhost:5432/smartroute`)

### Instalação e Execução

#### Via IDE
1. Clone o repositório do projeto
2. Abra o projeto em sua IDE
3. Aguarde a sincronização das dependências Maven
4. Execute a classe principal (classe com método `main`)

#### Via Terminal
```bash
# Clone o repositório
git clone <url-do-repositorio>
cd backend/smartRoute

# Instale as dependências
mvn clean install

# Execute o projeto
mvn spring-boot:run
```

### Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       └── application.yaml
│   └── test/
├── pom.xml
└── BACKEND.md
```

### Troubleshooting

**Erro de conexão com o banco de dados:**
- Verifique se o PostgreSQL está rodando
- Confirme as credenciais no `application.yaml`
- Valide a URL de conexão

**Dependências não sincronizadas:**
- Execute `mvn clean install` novamente
- Limpe o cache da IDE

### Documentação Adicional

- [Configuração do Banco de Dados](BANCO.md)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
