# Objetivo

Nesse projeto desenvolvemos um sistema de e-commerce que permita aos usuários realizarem as seguintes operações:
1. **Login e Registro de Usuário:** Os usuários devem ser capazes de se cadastrar e fazer login no sistema usando as ferramentas do Spring
   Security para autenticação e autorização.
2. **Gestão de Itens:** Os usuários administradores terão acesso a uma tela de gestão de itens, basicamente o controle de cadastro e
   manutenção de itens, bem como seus preços.
3. **Carrinho de Compras:** Os usuários podem adicionar e remover itens do carrinho de
   compras. O carrinho de compras deve ser persistente e associado ao usuário logado.
4. **Pagamentos (Simulação):** Implementar uma tela que simule o processo de pagamento, onde os usuários possam visualizar os itens do
   carrinho e concluir uma compra fictícia. 

## Arquitetura de Microserviços utilizando Spring Boot

![Arquitetura](/Arquitetura.png)


Neste projeto, adotamos uma arquitetura de microserviços para facilitar a escalabilidade, flexibilidade e manutenção do sistema. Vamos explorar os componentes principais dessa arquitetura e como eles se integram.

## Spring Boot

Utilizamos o Spring Boot como framework principal para o desenvolvimento de microserviços. O Spring Boot proporciona um ambiente simplificado e eficiente para a criação de aplicativos Java, facilitando a configuração e o desenvolvimento.

## Eureka Server

O Eureka Server é utilizado para implementar o serviço de descoberta de instâncias de microserviços na arquitetura. Ele permite que os microserviços se registrem e se descubram dinamicamente, facilitando a comunicação entre eles.

## Config Server

O Config Server é responsável por centralizar a configuração dos microserviços em um local único. Ele fornece uma maneira conveniente de gerenciar e distribuir as configurações de aplicativos de forma dinâmica, o que é essencial em um ambiente de microserviços, onde a configuração pode variar entre diferentes instâncias do mesmo serviço.
- **Repositorio de configurações:** https://github.com/SavioRony/tech-fase-5-config

## API Gateway

O API Gateway é um ponto de entrada único para os clientes acessarem os diferentes microserviços. Ele atua como uma camada intermediária entre os clientes e os microserviços, roteando as solicitações para os serviços apropriados. Além disso, o API Gateway pode realizar tarefas como autenticação, autorização e balanceamento de carga, proporcionando uma interface unificada para os clientes.

## Feign Client

O Feign Client é uma ferramenta do Spring Cloud que simplifica a comunicação entre os microserviços. Ele permite que os desenvolvedores criem interfaces Java simples e declarativas para chamar serviços RESTful, abstraindo a complexidade da comunicação HTTP.

## Execução Manual do Projeto

### Configuração e Inicialização

1. **Config Server**: Primeiramente, inicie o serviço do Config Server. Navegue até o diretório do projeto `config-server` e execute o aplicativo Spring Boot.

```bash
cd config-server
./mvnw spring-boot:run
```

2. **Eureka Server**: Após o Config Server estar em execução, inicie o serviço do Eureka Server. Navegue até o diretório do projeto `eureka-server` e execute o aplicativo Spring Boot.

```bash
cd eureka-server
./mvnw spring-boot:run
```

3. **API Gateway**: Com o Eureka Server em funcionamento, inicie o serviço do API Gateway. Vá para o diretório do projeto `api-gateway` e execute o aplicativo Spring Boot.

```bash
cd api-gateway
./mvnw spring-boot:run
```

4. **Serviços de Domínio**: Por fim, inicie os serviços de domínio, como `auth`, `usuario`, `item`, `pedido` e `carrinho`. Vá para o diretório de cada serviço individualmente e execute o aplicativo Spring Boot.

```bash
cd auth
./mvnw spring-boot:run
```

Repita o processo acima para os serviços restantes: `usuario`, `item`, `pedido`, e `carrinho`.


## Execução do Projeto com Docker Compose

### Arquivo de Execução `run_docker.sh`

Para simplificar a execução do projeto com Docker, fornecemos um script `run_docker.sh` que automatiza todo o processo de build e execução do Docker Compose.

1. Certifique-se de ter o Docker e o Maven instalados no seu sistema.

2. Execute o script `run_docker.sh` no terminal.

```bash
./run_docker.sh
```

Este script realizará as seguintes ações:

- Realiza o build de cada serviço.
- Cria e inicia os contêineres Docker para cada serviço.
- Configura o banco de dados PostgreSQL para os serviços de domínio.
- Garante que todos os serviços estejam em execução corretamente.

### Configurações do Banco de Dados

Neste processo manual, usamos configurações de desenvolvimento, empregando o banco de dados H2 em memória. Já no ambiente Docker, as configurações de produção são aplicadas, utilizando o banco de dados PostgreSQL.

## Testando a Aplicação

Para testar a aplicação, fornecemos uma coleção do Postman chamada `TECH-FIAP-05.postman_collection` e um ambiente chamado `TECH_ENV.postman_environment`. Essas ferramentas permitem que você envie solicitações HTTP para a API e valide as respostas.

### Configuração do Postman

1. **Importação das Coleções**: Abra o Postman e importe a coleção e o ambiente fornecidos. Isso criará um conjunto de solicitações pré-configuradas para testar a API.

2. **Seleção do Ambiente**: Certifique-se de selecionar o ambiente `TECH_ENV` na parte superior direita do Postman. Isso garantirá que as variáveis de ambiente, como URL base da API, estejam configuradas corretamente para suas solicitações.

### Testando a Aplicação como Administrador

Para começar a testar a aplicação como administrador, siga os passos abaixo:

1. **Utilize as Credenciais do Administrador**: Faça login utilizando as credenciais do usuário administrador gerado automaticamente ao iniciar a aplicação:
   - **E-mail**: admin@gmail.com
   - **Senha**: 123456

2. **Envie uma Solicitação POST para o Endpoint de Login**: Utilize a coleção do Postman para enviar uma solicitação POST para o endpoint de login.

3. **Obtenha o Token de Autenticação**: Após uma resposta bem-sucedida, você receberá um token de autenticação. Esse token será automaticamente usado nas solicitações subsequentes.

4. **Realize os Testes de Funcionalidades Disponíveis**:
   - **Criação de Itens**: Utilize o endpoint de criação de itens para adicionar produtos ao e-commerce.
   - **Alteração de Itens**: Utilize o endpoint de alteração de itens para modificar detalhes dos produtos, como preço, quantidade em estoque, etc.

Com esses passos, você estará apto a testar e validar as funcionalidades disponíveis para o usuário administrador na aplicação.

## Testando a Aplicação como Cliente

Para começar a testar a aplicação como cliente, siga os passos abaixo:

1. **Criar uma Conta de Cliente**: Utilize o endpoint disponível para criar uma conta de cliente na aplicação.

2. **Realizar Login**: Após criar a conta, faça login na aplicação utilizando as credenciais fornecidas durante o cadastro.

3. **Gerenciar o Carrinho de Compras**:
   - Adicionar Itens: Utilize os endpoints correspondentes para adicionar itens ao carrinho de compras.
   - Remover Itens: Use os endpoints correspondentes para remover itens do carrinho de compras.

4. **Fechar o Pedido**:
   - Com os itens desejados no carrinho, envie uma solicitação para o endpoint de fechamento de pedido, fornecendo a forma de pagamento desejada.

5. **Consultar Histórico de Pedidos**:
   - Utilize o endpoint disponível para consultar o histórico de pedidos associados ao usuário logado. Isso permitirá visualizar os pedidos concluídos anteriormente.

Certifique-se de seguir esses passos na ordem apresentada para garantir uma experiência de teste eficaz da aplicação como cliente. Utilize ferramentas como o Postman para enviar solicitações HTTP e validar as respostas da API.