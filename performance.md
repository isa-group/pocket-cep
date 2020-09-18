# Performance Analysis Tutorial
In the directory *Postman/performanceTesting*, there are all the documents needed to perform the same performance testing that the one shown
in the document. We explain below 

### *Postman* Collection json

Each directory for each scenario has its own exported *Postman* collection. In the case of the complex one, it contains a total of three 
streams, two patterns and a subscription. In the case of the simple, two streams, one pattern and a subscription. This files can be run
with *newman*, or be imported into *Postman*.

This collection must be run once the docker has been deployed. Be aware that the docker container is not deployed in a constant time. In
case you try to run the collection before, it will return an connection error.

### EventArtillery

This is the script for *Artillery*. The arrival rate is the number of requests per second, and the duration is the total time of the 
experiment, measured in seconds. The simple scenario has an additional file containing a function to add the timestamp in the simple scenario.

Command to run the artillery script:
```
artillery run EventArtillery.yml
```

The script uses a file called Event.csv for the data. Each event is composed of an index, which is the number of the column, and the value, in my
case a random number between 1 and 10. The value does not need to comply with this last rule, is just need to be a number.

### *server.py*

This file represent the file that will take all the final complex events, and it will add the timestamp to each of them as they reach the
system. We will end with all the timestamp needed to measure the processing of our system.

The server takes to arguments, the first is the port in which operates, and the second is the name of the file in which it will write all
the requests it receives from the docker. Right now, the docker sometimes fail to send some requests, so do not worry if at the csv appears
less data than the expected.

Example of a command:
```
python3 server.py 8081 20perSecond5min
```

## Steps to perform the testing.
* First, start the server, that will be the destination of all the complex event processed during the execution.
* Second, run the docker container of *Pocket-CEP*.
* Third, after the docker has been deployed, run the collection json of the chosen experiment.
* Last, run the *Artillery* script.
