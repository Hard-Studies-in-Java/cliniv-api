[![Heroku](https://heroku-badge.herokuapp.com/?app=heroku-badge&style=flat)](https://gestao-prontuario.herokuapp.com/swagger-ui.html)
# FT - Gestão de Prontuário - API
O FT - Gestão de Prontuário - API (FTGP-API) é uma API Rest OpenSource criada inicialmente como doação para hospitais que não possuem nenhuma solução de automação no processo de prontuário. O FTGP-API é integrado com um client baseado em Angular, o [FTGP-UI](https://github.com/viniciosarodrigues/gestao-de-prontuario-ui).


Atualmente encontra-se na versão 1.0.0
[Swagger](https://gestao-prontuario.herokuapp.com/swagger-ui.html)

Funcionalidades da API
- Cadastro de Pacientes
- Cadastro de Responsáveis (Enfermeiro, Médico, Técnico, etc)
- Cadastro de Eventos (Consultas, exames, prescriçoes, etc)
- Armazenamento de documentos (Resultado de exames, radiografia, tumografia, etc)
- Histórico de visita em forma de timeline
- Anamnese

# Como densenvolver?
Para contribuir com o projeto, existem alguns requisítos mínimos de configurações, são eles?
- Java 11 + **(Obrigatório)**
- MySql Server (Community) **(Obrigatório)**
- Lombok 1.16.0 + **(Obrigatório)**
- Eclipse ou STS **(Obrigatório)**
- No caso do Eclipse, usar o plugin do Spring Tools *(Opcional)*

## Configurando o Lombok
Caso não tenha o Lombok, já existe uma dependência do mesmo no projeto, basta importar o projeto no **Eclipse/STS** e aguardar que a dependẽncia seja baixada. Com o Jar do Lombok baixado no repositório local do Maven, siga o passo-a-passo logo abaixo:

- Navegar até o repositório do Lombok
-`C:\users\{usuario}\.m2\repository\org\projectlombok\lombok\{versao_baixada}`
- Executar o jar do lombok
-`java -jar lombok.{versao}.jar`
- Selecionar o Eclipse/STS que irá receber o Lombok
- Clicar em Install/Update
- Sair do instalador
- Abrir a IDE novamente
- Rebuild no projeto

Existe um manual mais detalhado de como instalar o plugin no Eclipse: [Criando uma API Rest de cadastro de contatos em 5 minutos com Spring Boot + Lombok](https://medium.com/@viniciosarodrigues/criando-uma-api-rest-de-cadastro-de-contatos-em-5-minutos-com-spring-boot-ce5ba775d2d8)

## Como rodar a API?
Primeiramente deve configurar o banco de dados MySql. Não precisa executar os scripts manualmente, o Flyway realiza o trabalho de migração. Com o MySql configurado, bastar alterar as informações de conexão no **application.properties**.

Por ser um projeto SpringBoot, não se faz necessário o uso de nenhum servidor externo, pois o framework já disponibiliza um Tomcat embarcado, desta forma basta clicar com o botão direito na raiz do projeto, **Run As** -> **SpringBoot App**.
Também pode rodar apartir do **ApplicationMain** encontrado no pacote base da aplicação.

## Como contribuir
Caso queira contribuir, basta realizar um fork do repositório, fazer a implementação desejada, **criar uma issue de push** e realizar pull request para a **master**.

# Arquitetura (Em desenvolvimento...)
Esta área especifica definições técnicas da aplicação, suas características e comportamentos.
## Modelagem de dados
![image](https://user-images.githubusercontent.com/7918549/77848948-8813ec80-719e-11ea-97ce-19fde9aacc42.png)

## Contato
Qualquer dúvida ou sugestão, favor enviar para o e-mai *viniciosarodrigues@gmail.com* ou enviar mensagem privada pelo próprio GitGub.
