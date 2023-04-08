# API Users & Products

API for a database containing users and products as part of the IUT - Computer Science (2nd year).

# 1. Get the authorizations tokens

There is 2 tokens for 2 scopes :
- Users scope
- Products scope

Contact me to get these tokens : lenny.gonzales@etu.univ-amu.fr

# 2. API endpoints
All the endpoints return a Response

## API Users => /users

---
<b>Get all users</b>
```
curl -X GET \
http://localhost:8080/users_products-1.0-SNAPSHOT/api/users \
-H 'Authorization: {token}'
```
---
<b>Create a user</b>
```
curl -X POST \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/users \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'email={email}}&password={password}'
```
---
<b>Modify a user</b>
```
curl -X PUT \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/users \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'id={id}&email={email}&password={password}'
```
---
<b>Get a user by its id</b>
- /{id}
```
curl -X GET \
http://localhost:8080/users_products-1.0-SNAPSHOT/api/users/{id} \
-H 'Authorization: {token}'
```
---
<b>Delete a user by its id</b>
```
curl -X DELETE \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/users/{id} \
  -H 'Authorization: {token}'
```

---
<b>Know if a user and its password exists</b>
- /authenticate
```
curl -X POST \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/users/authenticate \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'email={email}}&password={password}'
```


## API Products => /products

---
<b>Get all products</b>
```
curl -X GET \
http://localhost:8080/users_products-1.0-SNAPSHOT/api/products \
-H 'Authorization: {token}'
```
---
<b>Create a product</b>
```
curl -X POST \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/products \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'name={name}&description={description}&price={price}&unit={unit}&quantity={quantity}&quantityAvailable={quantityAvailable}'
```
---
<b>Modify a product</b>
```
curl -X PUT \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/products \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'id={id}&name={name}&description={description}&price={price}&unit={unit}&quantity={quantity}&quantityAvailable={quantityAvailable}'
```

---
<b>Get a product by its id</b>
- /{id}
```
curl -X GET \
http://localhost:8080/users_products-1.0-SNAPSHOT/api/products/{id} \
-H 'Authorization: {token}'
```
---
<b>Delete a product by its id</b>
```
curl -X DELETE \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/products/{id} \
  -H 'Authorization: {token}'
```

Made by Gonzales Lenny
