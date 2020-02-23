# Kotlin Ktor example

An example application using Kotlin and Ktor that includes a single
 web application with 2 background workers -

* basic web application
* data analyzer
* data collector

The example depends on the below technologies -

* Language [Kotlin](https://kotlinlang.org)
* Framework [Ktor](https://ktor.io)
* Build tool [Gradle](https://gradle.org)
* Testing tools [JUnit](https://junit.org/)
* Production [Heroku](https://www.heroku.com)

## Configuration

### Server

Configure the port that each server runs on.

```bash
export PORT=8881
```

Run servers locally using the below example -

```bash
java -jar applications/basic-server/build/libs/basic-server-1.0-SNAPSHOT.jar
``` 

## Production

The production app is deployed to [Heroku](https://www.heroku.com)
