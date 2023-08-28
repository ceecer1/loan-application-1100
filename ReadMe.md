Useful commands:

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