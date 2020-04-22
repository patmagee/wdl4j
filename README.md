
# wdl4j

---
**[Workflow Description Language](http://openwdl.org/) toolkit and language bindings for Java**

![Project Status](https://img.shields.io/badge/status-alpha-red.svg?style=flat)
![GitHub](https://img.shields.io/github/license/patmagee/wdl4j?style=flat)



## Getting Started

### Building


1. Clone this repository

```shell script
git clone http://github.com/patmagee/wdl4j.gi .
cd wdl4j
```

2. Build the source code using the packaged maven wrapper

```shell script
./mvnw clean compile
```
3. Run the tests

```shell script
./mvwn test
```


### Basic Usage

The simplest way to convert a WDL String into a Document is to use the WdlV1DocumentFactory.

```java

String wdl = "version 1.0\n workflow main { }";
WdlV1DocumentFactory documentFactory = new WdlV1DocumentFactory();
Document wdlDocument = documentFactory.create(wdl);


``` 


