# N26 Backend Challenge
API to store and get statistics of last 60 seconds of transactions.

# How to run
1.  Install [Maven](https://maven.apache.org/download.cgi) locally
2.  Run: `mvn clean install`
3.  Run: `cd target`
4.  Rin: `java -jar challenge-0.0.1.jar`

# Adding transactions 
Run the following in your terminal:
```
curl -d '{"amount":1, "timestamp":1478192204000}' -H "Content-Type: application/json" -X POST -i localhost:8080/transactions
```

If you don't have curl, then use postman to send a POST request to `localhost:8080/transactions` with a valid json in body. Containing amount and timestamp of transaction.
Make sure you change the timestamp number to the current Unix epoch timestamp.

# Getting statistics 
The following will give you statistics about all transactions in the last 60 seconds:
```
curl -i localhost:8080/statistics
```

You can also use a browser and go to `localhost:8080/statistics` to get statistics of last 60 seconds of transactions
