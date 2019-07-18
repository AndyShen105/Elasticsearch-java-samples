# Some Samples of Elasticsearch
This project contains some samples of Elasticsearch which are edited by java.

## Development Environment
Java: orcle jdk-1.8 

elasticsearch: 5.4.3

idea platform
## About the Dependciens
```aidl
    <dependencies>
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>transport</artifactId>
            <version>5.4.3</version>
        </dependency>

        <dependency>
            <groupId>org.elasticsearch</groupId>
            <artifactId>elasticsearch</artifactId>
            <version>5.4.3</version>
        </dependency>

        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.8.2</version>
        </dependency>

        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.8.2</version>
        </dependency>
    </dependencies>

```
## File List
ElasticsearchAggregationAPI.java: contains some test samples of ElasticsearchAggregationAPI.

ElasticsearchDocAPI.java: contains some test samples of ElasticsearchDocAPI.

EltasticsearchSearchAPI.java: contains some test samples of EltasticsearchSearchAPI.

HelloES.java: Program Entry

ReadJson.java: util to read json file

Data/bank.json: test data used

pom.xml: dependences we used

ReadMe.md: guide of this rep

## Sample Lists

### Samples of Document API
- test-1: del an index
- test-2: create an index
- test-3: upload a file into index
- test-4: upload a data
- test-5: obtain a doc
- test-6: del a doc
- test-7: upload a file with bulkprocesser into index

###  Samples of search API
- test-8: a sample search
- test-9: search with scroll
- test-10: search with template

###  Samples of aggregation API
- test-11: simple aggregation
- test-12: aggregation stats
- test-13: geo bounds aggregations
- test-14: filter aggregation
- test-15: Geo Distance aggregation