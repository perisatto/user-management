
# MenuGuru

Este repositório implementa um sistema fictício (MenuGuru) de gestão de pedidos para restaurantes como parte do trabalho de avaliação do curso de Pós Graduação em Software Architecture da FIAP.

O MenuGuru tem como objetivo principal registrar e acompanhar o status de pedidos para restaurantes, onde o cliente pode realizar seu pedido e acompanhar o status do mesmo até a retirada.

Funcionalidades:
* Cadastro e Identificação de Clientes
* Gestão de Produtos (criação, consulta, edição e remoção)
* Gestão de Pedidos (solicitação, consulta e finalização de pedidos)


## Pré-Requisitos

* JDK 22 ou mais recente (<https://jdk.java.net/>)
* Apache Maven versão 3.9.6 ou versão mais recente (<https://maven.apache.org/download.cgi>).

Certifique-se que a variável JAVA_HOME e M2_HOME estão apontadas corretamente para as versões indicadas/mais recentes.

## Execução 

Após realizar o download deste repositório, 

    $mvn package
    
Você também pode executar o comando acima ignorando os testes unitários:

    $mvn package -Dmaven.test.skip

O projeto será compilado e o executavel será gerado no diretório "/target". Após a execução do build você receberá a mensagem de Build Success abaixo:

    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------

Configure o arquivo de variáveis de ambiente com a senha a ser utilizada para o usuário root do banco de dados, substituindo "<password>" pela senha escolhida (o valor deve ser informado entre aspas):

    $echo MYSQL_ROOT_PASSWORD="<password>" >> .env
    $echo MYSQL_PASSWORD="<password>" >> .env


Com o projeto compilado e variáveis de ambiente configuradas é hora de iniciar a aplicação utilizando Docker. Na raiz do projeto utilize o comando "docker-compose" para iniciar a aplicação:

    $docker-compose up


### Operações

As funcionalidades do sistema MenuGuru podem ser acessadas através de APIs REST. As operações disponíveis podem ser consultadas através do contrato disponível em linguagem Openapi 3.0.* (Swagger) na pasta [Swagger](https://github.com/perisatto/menuguru/blob/feature/feature/products/src/main/resources/swagger/menuguru.yaml) deste respositório.

> Para consulta do contrato da API de forma mais amigável, utilize um visualizador Openapi disponível em <https://editor-next.swagger.io/> ou outra ferramenta de sua preferência. 

Após o ambiente configurado e a aplicação executando, as operações podem ser acessadas utilizando API Client. Neste repositório você pode encontrar uma coleção atualizada das APIs para serem utilizadas no API Client [Postman](https://www.postman.com/). Esta coleção está disponível na pasta [postman](https://github.com/perisatto/menuguru/tree/feature/feature/products/src/main/resources/postman) deste repositório.