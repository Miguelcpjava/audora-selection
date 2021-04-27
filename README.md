# Audora-Selection
Projeto da Seleção Audora

# Mapping
    * Stacks
    * PostgreSQL
    * Swagger 2
    * Lombok Project
    * JWT
    * SpringBoot 

    
## Rest API Documentation, 
    For access: http://localhost:8080/swagger-ui.html

## First Step
Create DataBase in Postgres like this:

```sql
CREATE DATABASE audora
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Portuguese_Brazil.1252'
       LC_CTYPE = 'Portuguese_Brazil.1252'
       CONNECTION LIMIT = -1;
       
```

Ready! You can run application, but don´t forget that after do it:

1º: 
```sql
INSERT INTO tbl_user(enabled, password, username, administrador)
    VALUES (true, '$2a$10$GHqUauSoqUNupt16TC3KVudJC9mf.l.wSmgLSMoy.AA37qPMWO7CS', 'client@mail.com', false);
    INSERT INTO tbl_user(enabled, password, username, administrador)
    VALUES (true, '$2a$10$gjErDA2C/UpaWfoyI4Ei7.mDq8HhbdtGD3fIGjsOm03wnAO1rmyea', 'admin@mail.com', true);
```

2º: 
```sql
    INSERT INTO tbl_categoria(nome) VALUES ('Biscoito');
    INSERT INTO tbl_categoria(nome) VALUES ('Limpeza');
```


3º: 
```sql 
INSERT INTO tbl_produto(
             nome, preco, categoria_id)
    VALUES ('Cookies', 3.00, 1);
```

**Ohhh yes!**

Let´s go next step.

With PostMan and with HttpMethod equals POST and with Url = users/auth write in the body like this:
```json
{
    "username":"admin@mail.com",
    "password" : "secret",
    "admin" : true
}
```

# Step Generate Token

So, the response will be a jwt token, copy this token and go to TestUtil and change the value instance Token to this new value, thanks!

And in PostMan on the tab Authorization choose the option Barear Token and paste in the field Token the value, without the value "Barear" the token that you copy before, you are amazing!

Finish? No!

You not enough satisfied, let´s create a new user like this:
*URL: 'users/create'

```json {
   "username": YouChoose,
    "password" : YouChooseAgain,
    "administrador" : I don't need to talk anymore,
    "enabled" : true
}
```

It´s ok! You have a new User, let´s do it again the steps generate token

Other url:

### Category Product:  
* CREATE - POST category/create
* LIST  - GET category/list/{nome}

### Product
* CREATE - POST   product/create
* LIST   - GET    product/consulta/{idProduct}
* DELETE - DELETE product/delete/{IdProduct}

### Discount
* CREATE - POST   discount/create

### Shop Cart
* SEARCH  - GET    carrinho/status/{usuarioId}/{carrinhoId}
* LIST    - GET    carrinho/listarItem/{carrinhoId}
* ADD     - POST   carrinho/adicionar/{usuarioId}/{carrinhoId}
* DELETE  - DELETE carrinho/delete/{carrinhoId}/{produtoId}
* CLOSE   - PATCH  carrinho/finalizar/{carrinhoId}
* ALTER   - PATCH  carrinho/alterar/quantidade/produto/aumentar/{carrinhoId}/{produtoId}
* ALTER   - PATCH  carrinho/alterar/quantidade/produto/diminuir/{carrinhoId}/{idProduto}
* CLEAR   - PUT    carrinho/limparCarrinho
