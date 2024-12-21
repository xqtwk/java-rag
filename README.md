run
```bash
mvn spring-boot:run
```

Don't forget to put your OPENAI_API_KEY to application.properties

REST API available at `http://localhost:8080`.

can try with browser ?query=ЗАПРОС
`http://localhost:8080/robobrain/rag?query=tell me about basketball`

or with curl
`curl "http://localhost:8080/robobrain/rag?query=Tell me about basketball"`