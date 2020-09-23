# Octo Events
Octo Events is an application that listens to Github Events via webhooks and expose by an api for later use.

## Requirements
 - [JDK 8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
 - [Maven](https://maven.apache.org/download.cgi)
 - [NGROK](https://ngrok.com/download)

## Build and Run Application
```
$ cd octo-events
$ mvn clean install spring-boot:run
```

## Github Integration
- Use ngrok to install/debug the webhook calls, it generates a public url that will route to your local host:
`$ ngrok http 8080`
- Use `POST /events` to save events via Webhooks.

## Using API
#### Swagger
You can use the endpoint `/swagger-ui.html` to get more information about API.

#### Search Events
##### Request:
`GET /issues/1000/events`

##### Response:
`
200 OK
[
    { "action": "open", created_at: "...",},
    { "action": "closed", created_at: "...",}
]
`
