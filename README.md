### Payara Micro on Cloud Foundry!!!


#### Run on Cloud Foundry

```
./mvnw clean package
cf create-service cleardb spark message-db
cf push --random-route
```

You can access `https://demo-payara-micro-<random-words>.cfapps.io`.

> This example uses [Pivotal Web Services](https://run.pivotal.io).

#### Get all messages

```
curl https://demo-payara-micro-<random-words>.cfapps.io
```

##### Create a message


```
curl https://demo-payara-micro-<random-words>.cfapps.io -d text=Hello
```

#### Run Locally

```
./mvnw clean package
export VCAP_APPLICATION={}
export VCAP_SERVICES='{"p-mysql":[{"credentials":{"uri":"mysql://root:@localhost:3306/message"},"name":"message-db"}]}'
java -jar target/ROOT.jar
```