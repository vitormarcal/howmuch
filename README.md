# How Much?

Já se pegou no mercado pensando: "Será que esse produto ficou mais caro desde a última vez que comprei?"

Com How Much?, essa dúvida pode ser resolvida facilmente. Basta fazer o upload do cupom fiscal, e a aplicação armazena cada item comprado, o preço, a data e até o local. Tudo fica acessível para que seja possível comparar preços e tomar decisões mais inteligentes no futuro.

Simples assim. Sem complicações. Apenas o essencial.

O projeto é uma plataforma web que utiliza o Spring Boot no backend, Vue.js no frontend e SQLite para armazenamento de dados.

## Tecnologias Utilizadas

 * Backend: Spring Boot (Java)
 * Frontend: Vue.js (com TypeScript)
 * Banco de Dados: SQLite (com Hibernate)
 * OCR: Tesseract (para extração de texto de imagens)
 * Infraestrutura: Docker

## Requisitos

Antes de rodar o projeto localmente, certifique-se de ter as seguintes ferramentas instaladas:

* JDK 17 ou superior
* Node.js (com npm)
* Docker (opcional, para rodar em containers)

## Configuração do Projeto
1. Configuração do Banco de Dados:
    * A aplicação utiliza SQLite por padrão, mas você pode configurá-la para usar PostgreSQL.
    * Verifique as configurações em application.properties ou application.yml e altere a URL do banco de dados conforme necessário.
2. Configuração de Diretórios:
   * O diretório de volumes é configurado em application.properties:
    ```
        app.volume-dir=/tmp/howmuch-app
    ```
   * Certifique-se de que o diretório está criado ou será criado automaticamente ao iniciar a aplicação.
3. Instalação das dependências do frontend:
   * Acesse o diretório do frontend e instale as dependências do Vue.js:
    ```
    cd frontend
    npm install
    
    ```
4. Rodando a Aplicação Localmente:
   * nicie o backend com o comando:
    ```
    ./mvnw spring-boot:run
    ```
   * Inicie o frontend com o comando:
    ```
    npm run serve
    ```
O frontend estará acessível em http://localhost:3000.