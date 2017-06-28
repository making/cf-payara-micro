### Payara Micro on Cloud Foundry!!!


Just run

```
./mvnw clean package
cf push --random-route
```

You can access `https://tiny-service-registry-<random-words>.cfapps.io`.


### Register a service instance

```
curl -XPOST -H "Content-Length: 0" "https://tiny-service-registry-<random-words>.cfapps.io/api/services/foo?host=xxxx&port=xxx"
```

### Delete a service instance

```
curl -XDELETE "https://tiny-service-registry-<random-words>.cfapps.io/api/services/foo?host=xxxx&port=xxx"
```

### Get all service instances

```
curl https://tiny-service-registry-<random-words>.cfapps.io/api/services/foo
```