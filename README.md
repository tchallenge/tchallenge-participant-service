# T-Challenge Pilot: Service
Web service for T-Challenge Pilot clients

### Development

Clone the repository:
```
> git clone https://github.com/tchallenge/tchallenge-pilot-service.git
> cd tchallenge-pilot-service
```

Application requires a MongoDB instance runnning on the local machine. After it's installed and up, import demo dataset from the datastorage folder (assuming MongoDB is on its standard port 27017):
```
> cd datastorage
> ./tchallenge-pilot-datastore-import.cmd (.sh)
```

The following environment variables are also required:
```
TCHALLENGE_MONGODB_PORT='27017'
TCHALLENGE_MONGODB_DATABASE='tchallenge-pilot'
TCHALLENGE_SENDGRID_ENABLED='false'
TCHALLENGE_EXPERIMENTAL_FEATURES_ENABLED='true'
TCHALLENGE_SECURITY_TOKEN_PREDEFINED_ENABLED='true'
```

Run gradle bootRun task:
```
> cd source
> gradlew bootRun
```
