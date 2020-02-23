# Kotlin ktor starter

An [application continuum](https://www.appcontinuum.io/) style example using Kotlin and Ktor
that includes a single web application with two background workers.

* Basic web application
* Data analyzer
* Data collector

### Technology stack

This codebase is written in a language called [Kotlin](https://kotlinlang.org) that is able to run on the JVM with full
Java compatibility.
It uses the [Ktor](https://ktor.io) web framework, and runs on the [Netty](https://netty.io/) web server.
HTML templates are written using [Freemarker](https://freemarker.apache.org).
The codebase is tested with [JUnit](https://junit.org/) and uses [Gradle](https://gradle.org) to build a jarfile.

## Getting Started

1.  Build a Java Archive (jar) file.
    ```bash
    ./gradlew clean build
    ```

1.  Configure the port that each server runs on.
    ```bash
    export PORT=8881
    ```

1.  Run the servers locally using the below examples.

    ```bash
    java -jar applications/basic-server/build/libs/basic-server-1.0-SNAPSHOT.jar
    ```

    Data collector

    ```bash
    java -jar applications/data-collector-server/build/libs/data-collector-server-1.0-SNAPSHOT.jar
    ```

    Data analyzer
    
    ```bash
    java -jar applications/data-analyzer-server/build/libs/data-analyzer-server-1.0-SNAPSHOT.jar
    ```
    
## Running with Docker

1. Build with Docker.

    ```bash
    docker build -t kotlin-ktor-starter . --platform linux/amd64
    ```

1.  Run with docker.

    ```bash
    docker run -e PORT=8881 -p 8881:8881 kotlin-ktor-starter
    ```

That's a wrap for now.
