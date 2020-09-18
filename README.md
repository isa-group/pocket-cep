# Pocket-CEP

Lightweight RESTful CEP (Complex Event Processing) Engine based on [Siddhi](https://siddhi.io/).

## Motivation

This project can be useful for anyone thinking on testing a fast changing complex event processing environment, without having to redeploy the CEP Engine everytime you want to use a new pattern, stream or endpoint for the unprocessed or processed data. We are aiming for simplifying the way the data is handled, as well as defining the different destinations of any data processed.

## Requirements

* Docker.
* Node 12. Not needed, but recommended in case you want to run the postman collections in this repository.

## Installing

Pocket-CEP can only be run in a docker container. It is recommended that you run Pocket-CEP as a daemon. In order to run an instance, use the following command:

```
docker run -d -p 9999:9999 jsantope/pocket-cep
```

## Getting Started

A minimum complete and functional application must have two streams, one for input and one for output, one patterns using these streams, and one output stream subscriber, to send the complex events processed by the pattern. We provide a bit bigger example that this one to run, but in case you want to run yours, this sums up what you would need.

To run a minimun example, you can just run the following commands:

```
npx degit https://github.com/JsAntoPe/Pocket-CEP/postman simpleExample
npx newman run simpleExample/PocketCEPAirQualityCase.postman_collection.json
```

The postman collection used in this example: [Postman Collection](https://documenter.getpostman.com/view/9546113/T1DmEyqh).

The example above will run a total of 5 streams, 4 patterns , 4 subscriptions and 3 events sent to the first stream *Air Mesurement*.

The different components of the system are explained in full detail on the wiki section *[How it works](https://github.com/JsAntoPe/Pocket-CEP/wiki/How-it-works).

Pocket-CEP is a microservice, therefore, it works under the REST protocol. All the operations have been documented following the OpenAPI Specification. It can be found here: [Pocket-CEP OpenAPI Specification](https://app.swaggerhub.com/apis/JsAntoPe/pocket-cep/0.8.0).

In case you want to see an example of each element, see the wiki section *[Examples](https://github.com/JsAntoPe/Pocket-CEP/wiki/Examples).

## Testing

For testing the service, I have used Postman, writing http requests. In case you want to run the tests, you can use this next command:

```
npx degit https://github.com/JsAntoPe/Pocket-CEP/postman simpleExample
npx newman run simpleExample/PocketCEPTesting.postman_collection.json
```
Postman collection used in the testing part: [Postman Collection](https://documenter.getpostman.com/view/9546113/T1DmEyqo).

### Performance Testing
In case you want to perform a performance testing, you can follow the steps in this [file](https://github.com/JsAntoPe/Pocket-CEP/blob/master/performance.md).
