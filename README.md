# API Users & Products

API for a database containing users and products as part of the IUT - Computer Science (2nd year).

# 1. Get the authorizations tokens

There is 2 tokens for 2 scopes :
- Users scope
- Products scope

Contact me to get theses tokens : lenny.gonzales@etu.univ-amu.fr

# 2. API endpoints
All the endpoints return a Response

## API Users => /users

```
curl -X GET \
http://localhost:8080/users_products-1.0-SNAPSHOT/api/users \
-H 'Authorization: {token}'
```

```
curl -X POST \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/users \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'email={email}}&password={password}'
```

```
curl -X PATCH \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/users \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'id={id}&email={email}&password={password}'
```

- /{id}
```
curl -X GET \
http://localhost:8080/users_products-1.0-SNAPSHOT/api/users/{id} \
-H 'Authorization: {token}'
```

```
curl -X DELETE \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/users/{id} \
  -H 'Authorization: {token}'
```

- /authenticate
```
curl -X POST \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/users/authenticate \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'email={email}}&password={password}'
```


## API Products => /products

```
curl -X GET \
http://localhost:8080/users_products-1.0-SNAPSHOT/api/products \
-H 'Authorization: {token}'
```
```
curl -X POST \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/products \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'name={name}&description={description}&price={price}&unit={unit}&quantity={quantity}&quantityAvailable={quantityAvailable}'
```
```
curl -X PATCH \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/products \
  -H 'Authorization: {token}' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'id={id}&name={name}&description={description}&price={price}&unit={unit}&quantity={quantity}&quantityAvailable={quantityAvailable}'
```

- /{id}
```
curl -X GET \
http://localhost:8080/users_products-1.0-SNAPSHOT/api/products/{id} \
-H 'Authorization: {token}'
```

```
curl -X DELETE \
  http://localhost:8080/users_products-1.0-SNAPSHOT/api/products/{id} \
  -H 'Authorization: {token}'
```

Made by Gonzales Lenny
