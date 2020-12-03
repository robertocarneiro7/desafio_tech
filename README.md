# Desafio Tech

O **desafio_tech** é uma aplicação que foi desenvolvida como um teste para uma vaga de desenvolvedor.

A aplicação observa o diretório **~/data/in**, caso seja adicionado um arquivo do tipo **.dat**, então o arquivo é processado e é gerado um arquivo de saída no diretório **~/data/out**

## Tecnologias utilizadas
1. Java 13
2. Spring-Boot
3. Spring-Boot-Devtools(Foi utilizado **FileSystemWatcher.class** e **FileChangeListener.class** para ficar observando se ocorreu adição de arquivo em um diretório)
4. Spring-Boot-Starter-Log4j2
5. Lombok
6. Docker

## Como rodar a aplicação?

Para utilizar a aplicação é necessário atender alguns pré-requisitos:
1. Criar diretório ***data*** no caminho ***~/***
2. Criar diretório ***in*** no caminho ***~/data***
3. Instalar ***docker*** na máquina

Após os pré-requisitos serem atendidos, é necessário apenas entrar na pasta raiz do projeto e executar o seguinte comando:
- docker-compose up --build -d

Para visualizar os logs da aplicação enquanto ela está sendo executada, basta utilizar o seguinte comando:
- docker logs -f --tail 10000 desafio-tech

Para visualizar os logs antigos da aplicação, basta acessar o seguinte diretório no seu computador:
- ~/data/logs