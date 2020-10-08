# Cooperative Assembly
### Resumo
Solução para o Desafio Técnico do Sicredi.

#### Desafio proposto
No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. 
A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação. 
Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:

Cadastrar uma nova pauta;
Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na 
chamada de abertura ou 1 minuto por default);
Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por 
um id único e pode votar apenas uma vez por pauta);
Contabilizar os votos e dar o resultado da votação na pauta.

#### Tecnologias utilizadas
Para esse projeto, foram utilizadas as seguintes tecnologias:
* Java 11: Linguagem utilizada no projeto;
* Spring: Inicialização da API e injeção de dependências;
* Gradle: Automação do build e gestão de dependências;
* Swagger: Documentação interativa da API;
* MongoDB: Banco de dados não relacional para a persistência dos dados;
* Docker: Administração de containers

#### Requerimentos
Para executar o sistema, é necessário ter Java 11 e Docker na máquina.

#### Inicialização do sistema
Para rodar o sistema, os contêineres do Docker devem estar em execução, 
pois, o MongoDB e o Kafka serão executados no Docker. Para isso, executar o comando abaixo no terminal
docker-compose up -d
Ao fim da execução do comando acima, o sistema deverá ser executado em uma IDE, ou rodando o comando abaixo no terminal:

java -jar ./build/libs/cooperative-assembly-0.0.1-SNAPSHOT.jar.

**Importante** Os comandos descritos nesse documento devem ser executados num terminal aberto na pasta raiz do projeto.

#### Iniciando os testes
####URL do Swagger
* [Swagger](http://127.0.0.1:8080/swagger-ui.html#/assembly-controller)


#### Administração do MongoDB
O banco poderá ser administrado através do [Mongo Express](http://127.0.0.1:8081/db/db_assembly/).

#### Kafka
O resultado de cada sessão de votação será enviado pelo Kafka ao tópico "results.topic". O consumo das mensagens 
será feito pelo sistema, e as mensagens serão exibidas no log.

#### Versionamento

O projeto foi versionado pela URL no modelo path, por ter o visual mais limpo e ser mais amigável ao desenvolvedor.



** A API externa se mostrou muito instável no desenvolvimento desse sistema, mostrando 
diferentes resultados para o mesmo CPF testado. Por causa disso, foi inserida uma configuração
no arquivo src/main/java/resources/application.properties que permite habilitar/desabilitar a verificação.
