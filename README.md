### Payara Micro on Cloud Foundry!!!


Just run

```
./mvnw clean package
cf push --random-route
```

You can access `https://demo-payara-micro-<random-words>.cfapps.io`.


If you want to use a database, **check [db branch](https://github.com/making/cf-payara-micro/tree/db)**.