# loan-application


## Designing

While designing your service it is useful to read [designing services](https://docs.kalix.io/developing/development-process-proto.html)


## Developing

This project has a bare-bones skeleton service ready to go, but in order to adapt and
extend it, it may be useful to read up on [developing services](https://docs.kalix.io/services/)
and in particular the [Java section](https://docs.kalix.io/java-protobuf/index.html)


## Building

You can use Maven to build your project, which will also take care of
generating code based on the `.proto` definitions:

```shell
mvn compile
```


## Running Locally


When running a Kalix service locally, we need to have its companion Kalix Proxy running alongside it.

To start your service locally, run:

```shell
mvn kalix:runAll
```

This command will start your Kalix service and a companion Kalix Proxy as configured in [docker-compose.yml](./docker-compose.yml) file.

> Note: if you're looking to use Google Pub/Sub, see comments inside [docker-compose.yml](./docker-compose.yml)
> on how to enable a Google Pub/Sub emulator that Kalix proxy will connect to.

With both the proxy and your service running, any defined endpoints should be available at `http://localhost:9000`. In addition to the defined gRPC interface, each method has a corresponding HTTP endpoint. Unless configured otherwise (see [Transcoding HTTP](https://docs.kalix.io/java-protobuf/writing-grpc-descriptors-protobuf.html#_transcoding_http)), this endpoint accepts POST requests at the path `/[package].[entity name]/[method]`. For example, using `curl`:

```shell
> curl -XPOST -H "Content-Type: application/json" localhost:9000/io.kx.loanapp.CounterService/GetCurrentCounter -d '{"counterId": "foo"}'
The command handler for `GetCurrentCounter` is not implemented, yet
```

For example, using [`grpcurl`](https://github.com/fullstorydev/grpcurl):

```shell
> grpcurl -plaintext -d '{"counterId": "foo"}' localhost:9000 io.kx.loanapp.CounterService/GetCurrentCounter 
ERROR:
  Code: Unknown
  Message: The command handler for `GetCurrentCounter` is not implemented, yet
```

> Note: The failure is to be expected if you have not yet provided an implementation of `GetCurrentCounter` in
> your entity.


## Deploying

To deploy your service, install the `kalix` CLI as documented in
[Setting up a local development environment](https://docs.kalix.io/setting-up/)
and configure a Docker Registry to upload your docker image to.

You will need to update the `dockerImage` property in the `pom.xml` and refer to
[Configuring registries](https://docs.kalix.io/projects/container-registries.html)
for more information on how to make your docker image available to Kalix.

Finally, you use the `kalix` CLI to create a project as described in [Create a new Project](https://docs.kalix.io/projects/create-project.html). Once you have a project you can deploy your service into the project either 
by using `mvn deploy kalix:deploy` which will package, publish your docker image, and deploy your service to Kalix, 
or by first packaging and publishing the docker image through `mvn deploy` and 
then [deploying the image through the `kalix` CLI](https://docs.kalix.io/services/deploy-service.html#_deploy).



## Useful commands:

Follow prompts for logging into docker, signup at dockerhub, create your login and token, and then do the following:
```sh
docker login
```

```sh
mvn clean compile package docker:build docker:push
```

```sh
kalix service deploy loanapp ceecer1/loan-application-1100:1.0.0
```

```sh
kalix services list
```

Once service shows ready:
```sh
kalix service expose loanapp
```

Check if grpcurl is installed ?
```sh
grpcurl
```

If not installed:
```sh
brew install grpcurl
```

Test service, submit loan app
```sh
grpcurl -d '{"loan_app_id": "uuid", "client_id": "client100", "client_monthly_income_cents": 100000, "loan_amount_cents": 200000, "loan_duration_months": 28}' mute-hill-0226.eu-central-1.kalix.app:443 io.kx.loanapp.api.LoanAppService/Submit
```

Retrieve loan app
```sh
grpcurl -d '{"loan_app_id": "uuid"}' mute-hill-0226.eu-central-1.kalix.app:443 io.kx.loanapp.api.LoanAppService/Get
```