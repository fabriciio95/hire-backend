# Hire - Back-End
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/fabriciio95/hire-backend/blob/master/LICENSE) 

# Sobre o projeto

http://apphire.herokuapp.com/


Hire back-end é uma API REST construída para fins de **aprendizado** que visa gerar oportunidades a trabalhadores autônomos, expondo seu perfil para que clientes possam visualizar, entrar em contato e dar sua avaliação dos serviços prestados.

## Modelo Lógico
![Logico](https://github.com/fabriciio95/hire-front-end/blob/master/public/img/modelo-logico-hire.jpg)

# Tecnologias utilizadas
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Gradle
- Java Bean Validation
- Lombok
- JWT

# Arquitetura
- REST

# Implementação do Front-End
- [Hire-Front-End](https://github.com/fabriciio95/hire-front-end)

# Implantação em produção
- Back end: Heroku
- Front end: Heroku
- Banco de dados: Postgresql

# Como executar o projeto
Pré-requisitos: Java 11, Gradle

```bash
# clonar repositório
git clone https://github.com/fabriciio95/hire-backend

# Entre na pasta raiz do projeto:
cd hire-backend

# E para rodar o projeto, você pode executar:
gradle bootRun
```

# Manual das requisições da API
Pré-requesitos: Postman

## Buscando profissionais pela descrição
Para realizar uma busca por um profissional pela sua descrição no banco de dados, você deve fazer uma requisição utilizando o método **GET** do protocolo HTTP passando um texto para ser buscado pela URL:
```url
http://localhost:8080/search/java
```
**Sendo que java é o parâmetro para ser realizado a busca.** E então, caso encontre profissionais que correspondam a busca, será retornado uma resposta com status 200 e o objeto JSON com os campos: id, nome, descricao, valorHora e fotoBase64 preenchidos com os dados dos profissionais encontrados. Caso contrário será retornado um status 404.

![search](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/url-search.png)


## Salvando um novo usuário
Para salvar um novo usuário, você deve enviar uma requisição utilizando o método **POST** do protocolo HTTP com a URL:
```url
http://localhost:8080/usuario
```
E no corpo da requisição, você deverá enviar um objeto JSON com os campos:
```json
{
    "usuario": "",
    "senha": "",
    "nome" : "",
    "fotoBase64" : ""
}
```
**Sendo que no campo fotoBase64 deve conter uma imagem codificada em base 64.**
 

Caso a requisição for bem-sucedida será retornado uma resposta com status 201 e no corpo um objeto JSON com os campos: id, usuario e nome preenchidos com os dados do usuário salvo.

![Salvar Usuario](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/save-usuario.png)

## Salvando um novo profissional
Para salvar um novo profissional, você deve enviar uma requisição utilizando o método **POST** do protocolo HTTP com a URL:
```url
http://localhost:8080/profissional
```
E no corpo da requisição, você deverá enviar um objeto JSON com os campos:
```json
{
    "usuario": "",
    "senha": "",
    "nome" : "",
    "endereco": "",
    "email": "",
    "descricao": "",
    "valorHora" : "",
    "telefone": "",
    "euQuero" : "AMBOS || SER_CONTRATADO",
    "fotoBase64" : ""
}
```
**Sendo que no campo fotoBase64 deve conter uma imagem codificada em base 64 e no campo euQuero deve conter obrigatóriamente apenas um dos valores: AMBOS ou SER_CONTRATADO.**

![Salvar Profissional](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/save-profissional-request.png)


E caso a requisição for bem-sucedida será retornado uma resposta com status 201 e no corpo um objeto JSON com os campos: id, usuario, nome, endereco, email, descricao, valorHora, telefone e euQuero preenchidos com os dados do profissional salvo.

![Salvar Profissional](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/save-profissional-response.png)


## Fazendo o login
Para fazer o login, você deve enviar uma requisição utilizando o método **POST** do protocolo HTTP com a URL:
```url
http://localhost:8080/login
```
E no corpo da requisição você precisará informar um usuário e senha válidos para serem autenticados e então receberá um token JWT que será utilizado posteriormente para requisições que necessitem de autorização. Para isso, você primeiro precisará enviar um objeto JSON com os campos:
```json
{
    "usuario" : "",
    "senha" : ""
}
```

![Login](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/login-request.png)

E se, e somente se, o usuário e senha forem válidos será retornado uma resposta com status 200 com um cabeçalho **Authorization** com o token JWT gerado pela API.

![Login](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/login-response.png)

## Atualizando os dados de um usuário
Para atualizar os dados de um usuário, você deve fazer uma requisição utilizando o método **PUT** do protocolo HTTP passando o **id** do usuário na URL:
```url
http://localhost:8080/usuario/2
```
**Sendo que o 2 é o id do usuário a ser atualizado.** Você também **obrigatoriamente** deverá enviar na requisição um cabeçalho **Authorization** com o token JWT obtido após o processo de login.

![Atualizar Usuário](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/update-usuario-header-request.png)

E no corpo da requisição, você deverá enviar um objeto JSON com os campos:
```json
{
    "usuario": "",
    "senha": "",
    "nome" : "",
    "fotoBase64" : ""
}
```
**Sendo que no campo fotoBase64 deve conter uma imagem codificada em base 64.**
Caso a requisição for bem-sucedida será retornado uma resposta com status 200 e no corpo um objeto JSON com os campos: id, usuario e nome preenchidos com os dados atualizados do usuário.

![Atualizar Usuário](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/update-usuario-body-response.png)

## Atualizando os dados de um profissional
Para atualizar os dados de um profissional, você deve fazer uma requisição utilizando o método **PUT** do protocolo HTTP passando o **id** do profissional na URL:
```url
http://localhost:8080/usuario/2
```
**Sendo que o 2 é o id do profissional a ser atualizado.** Você também **obrigatoriamente** deverá enviar na requisição um cabeçalho **Authorization** com o token JWT obtido após o processo de login.

![Atualizar Profissional](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/update-profissional-header.png)

E no corpo da requisição, você deverá enviar um objeto JSON com os campos:
```json
{
    "usuario": "",
    "senha": "",
    "nome" : "",
    "endereco": "",
    "email": "",
    "descricao": "",
    "valorHora" : "",
    "telefone": "",
    "euQuero" : "AMBOS || SER_CONTRATADO",
    "fotoBase64" : ""
}
```
**Sendo que no campo fotoBase64 deve conter uma imagem codificada em base 64 e no campo euQuero deve conter obrigatóriamente apenas um dos valores: AMBOS ou SER_CONTRATADO.**

![Atualizar Profissional](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/update-profissional-body.png)

E caso a requisição for bem-sucedida será retornado uma resposta com status 200 e no corpo um objeto JSON com os campos: id, usuario, nome, endereco, email, descricao, valorHora, telefone e euQuero preenchidos com os dados atualizado do profissional.

![Atualizar Profissional](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/update-profissional-response.png)

## Obtendo os dados de um usuário ou profissional
Para obter os dados de um usuário ou de um profissional, você deve fazer uma requisição utilizando o método **GET** do protocolo HTTP passando o **id** do profissional ou usuário desejado na URL:
```json
http://localhost:8080/usuario-profissional/2
```
**Sendo que o 2 é o id do profissional ou usuário desejado.** Você também **obrigatoriamente** deverá enviar na requisição um cabeçalho **Authorization** com o token JWT obtido após o processo de login.

![Obter Usuario Profissional](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/obter-usuarioprofissional-header.png)

**Caso o id passado como parâmetro seja um de um usuário, será retornado uma resposta com status 200 e o objeto JSON com os campos: id, usuario, nome e euQuero preenchidos com os dados do usuário.**

![Obter Usuario](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/obter-usuarioprofissional-response-user.png)

**Caso o id passado como parâmetro seja de um profissional, será retornado uma resposta com status 200 e o objeto JSON com os campos: id, usuario, nome, endereco, email, descricao, valorHora, telefone e euQuero preenchidos com os dados do profissional.**

![Obter Profissional](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/obter-usuarioprofissional-response-worker.png)

Caso contrário será retornado um status 404.




## Obtendo os dados e avaliações de um profissional
Para obter os dados e avaliações de um profissional, você deve fazer uma requisição utilizando o método **GET** do protocolo HTTP passando o **id** do profissional desejado na URL:
```url
 http://localhost:8080/profissional/perfil/10
 ```
 **Sendo que o 10 é o id do profissional desejado.** Você também **obrigatoriamente** deverá enviar na requisição um cabeçalho **Authorization** com o token JWT obtido após o processo de login.

![Obter Perfil](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/obter-perfil-header.png)

**Caso encontre um profissional que corresponda ao id passado como parâmetro, será retornado uma resposta com status 200 e o objeto JSON com os campos: id, nome, endereco, email, descricao, valorHora, telefone e fotoBase64 preenchidos com os dados do profissional.**

![Obter Profissional](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/obter-perfil-response-worker.png)

**Além de um array avaliacoes com objetos com os campos: id, comentario, idAutor, nomeAutor, idProfissional e fotoAutorBase64.**

![Obter Avaliações](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/obter-perfil-response-avaliacoes.png)


## Publicando uma avaliação de um profissional
Para publicar uma avaliação de um profissional, você deve fazer uma requisição utilizando o método **POST** do protocolo HTTP com a URL:
```url
http://localhost:8080/avaliacao
``` 
Você também **obrigatoriamente** deverá enviar na requisição um cabeçalho **Authorization** com o token JWT obtido após o processo de login.

![Publicar Avaliação](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/publicar-avaliacao-header.png)

E no corpo da requisição, você deverá enviar um objeto JSON com os campos:
```json
{
    "comentario" : "",
    "idAutor" : ,
    "idProfissional": 
}
```
**Sendo que nos campos idAutor e idProfissional devem ser passados os ids do usuário autor do comentário e do profissional que está sendo avaliado respectivamente como números (sem estar dentro de aspas).**

![Publicar Avaliação](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/publicar-avaliacao-body.png)

**Caso o id do autor e do profissional sejam iguais será retornado uma resposta com status 400, além de um objeto JSON com os campos status da resposta, dataHora da requisição e titulo com a mensagem "Você não pode fazer uma avaliação de si mesmo".**

![Publicar Avaliação](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/publicar-avaliacao-response-erro.png)

**Caso a requisição seja bem-sucedida, será retornado uma resposta com status 201 e o objeto JSON com os campos: id, comentario, idAutor, nomeAutor, idProfissional e fotoAutorBase64.**

![Publicar Avaliação](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-hire-backend/publicar-avaliacao-response-ok.png)

## Testando a API implantada no Heroku
**Para testar a API implantada no heroku, você precisa apenas fazer a seguinte alteração nas urls dos exemplos acima:**
```url
http://localhost:8080
```
**Para:**
```url
  https://tohireapp.herokuapp.com
```
**Lembrando que caso o servidor não esteja ativo no Heroku as primeiras requisições podem não funcionar, então apenas faça uma requisição e aguarde alguns instantes para o servidor seja inicializado no Heroku.**

# Autor

Fabricio Siqueira Macedo

https://linkedin.com/in/fabricio-siqueira-macedo
