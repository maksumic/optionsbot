# Options Bot

**USE AT YOUR OWN RISK**

---

## Local Setup

### IBKR Setup

* Download IBKR TWS
* Edit > Global Configuration > API
    * Socket port: 7497 (paper trading) or 7496 (live trading)
    * Enable "ActiveX and Socket Clients"
    * Disable "Read-Only API"
    * Enable "Allow connections from localhost only"

From the project root directory, run:

```sh
./mvnw install:install-file -Dfile=libs/TwsApi.jar -DgroupId=com.ibkr -DartifactId=ibapi -Dversion=10.30 -Dpackaging=jar
```

### Build & Run

```sh
./mvnw clean package
```

```sh
java -jar ./target/optionsbot-0.0.1.jar
```
