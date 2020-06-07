# Cooperative Assembly
### Resumo
Solução para o Desafio Técnico da South System.

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

[Ver mais...](https://github.com/rh-southsystem/desafio-back-votos)

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
#####URL do Swagger
* [Home](http://127.0.0.1:8080/swagger-ui.html#/assembly-controller)

#### Endpoints
* /assembly/admin/agenda

Cria uma pauta que poderá ser usada numa sessão de votação.

Exemplo de request:

{<br />
  "description": "Devemos usar Java?",<br />
  "subject": "Linguagem de programação"<br />
}

Exemplo de response:

{<br />
  "id": "5edd6e502dbc32308ad5425b",<br />
  "subject": "Linguagem de programação"<br />
}

Status possíveis: 201, 400 e 500.

* /assembly/admin/session

Cria uma sessão de votação recebendo o ID da pauta e o tempo de duração da mesma em minutos.
Se a duração não for preenchida, a sessão irá durar 1 minuto.

Exemplo de request:

{<br />
  "agendaId": "5edd6e502dbc32308ad5425b",<br />
  "minutesRemaining": 60<br />
}

Exemplo de response:

{<br />
  "sessionId": "5edd6f9d2dbc32308ad5425c"<br />
}

Status possíveis: 201, 400 e 500.

* /assembly/members/agenda/all
Exibe todas as pautas já cadastradas.

Exemplo de response:

{<br />
  "list": [<br />
    {
      "id": "5edd42536877d815f01a0a4a",<br />
      "subject": "Melhor time"<br />
    },<br />
    {<br />
      "id": "5edd44c629b9c45d6eff06b3",<br />
      "subject": "Pior time"<br />
    }<br />
  ],<br />
  "quantity": 2<br />
}

Status possíveis: 200 e 500

* /assembly/members/session/open-sessions

Exibe as sessões de votação que estão ativas

Exemplo de response:

{<br />
  "list": [<br />
    {<br />
      "sessionId": "5edd6f9d2dbc32308ad5425c"<br />
    }<br />
  ],<br />
  "quantity": 1<br />
}

* assembly/members/vote
Permite que um associado realize um voto em uma sessão ativa, inserindo o ID da sessão e o CPF.
O sistema irá verificar a validade do CPF através da [API](https://user-info.herokuapp.com/users/{cpf}) que foi 
fornecida na [documentação](https://github.com/rh-southsystem/desafio-back-votos) desse desafio. **

* /assembly/members/session/results
Exibe o resultado de uma sessão finalizada, através do ID da sessão.

Exemplo de response:
{<br />
  "sessionId": "5edd42636877d815f01a0a4b",<br />
  "agendaId": "5edd42536877d815f01a0a4a",<br />
  "favor": 1,<br />
  "against": 0,<br />
  "total": 1<br />
}


#### Administração do MongoDB
O banco poderá ser administrado através do [Mongo Express](http://127.0.0.1:8081/db/db_assembly/).

#### Kafka
O resultado de cada sessão de votação será enviado pelo Kafka ao tópico "results.topic". O consumo das mensagens 
será feito pelo sistema, e as mensagens serão exibidas no log.



** A API externa se mostrou muito instável no desenvolvimento desse sistema, mostrando 
diferentes resultados para o mesmo CPF testado. Por causa disso, foi inserida uma configuração
no arquivo src/main/java/resources/application.properties que permite habilitar/desabilitar a verificação.