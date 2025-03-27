# JavaRAG — Retrieval-Augmented Generation (RAG) App with Spring Boot & OpenAI
## What is this?
This is a simple yet powerful Retrieval-Augmented Generation (RAG) application built with **Spring Boot**,
using **OpenAI** for chat completion and embedding. It allows querying local documents (JSON, TXT, PDF, MD) via OpenAI's models by integrating vector search.


Retrieval-Augmented Generation enhances LLMs by supplying relevant documents from a private knowledge base before generating the answer. This makes answers more grounded and traceable.
## Table of contents
<!-- TOC -->
* [JavaRAG — Retrieval-Augmented Generation (RAG) App with Spring Boot & OpenAI](#javarag--retrieval-augmented-generation-rag-app-with-spring-boot--openai)
  * [What is this?](#what-is-this)
  * [Table of contents](#table-of-contents)
  * [Features](#features)
  * [Potential use](#potential-use)
  * [How to run?](#how-to-run)
  * [Endpoints](#endpoints)
    * [endpoint for query](#endpoint-for-query)
    * [endpoint to trigger asynchronous file reindexing POST](#endpoint-to-trigger-asynchronous-file-reindexing-post)
  * [Notes](#notes)
  * [How to split chunks of data in files?](#how-to-split-chunks-of-data-in-files)
<!-- TOC -->

## Features

- Retrieval-Augmented Generation using OpenAI
- Supports `json`, `txt`, `md`, and `pdf` document formats
- File-based vector store (in-memory)
- REST endpoint to query the knowledge base
- Asynchronous background file indexing
- Trigger runtime reindexing via API
- Built with `spring-ai` 1.0.0-SNAPSHOT
## Potential use
* This kind of setup is commonly used to build:
* Internal knowledge bases
* AI chatbots over custom data
* Private assistants with no external data leakage
* Domain-specific copilots

## How to run?
```bash
mvn spring-boot:run
```
## Endpoints
### endpoint for query
`http://localhost:8080/api/v1/rag/query`

Request for a try:
  `
  {
  "query": "tell me about basketball"
  }
  `
### endpoint to trigger asynchronous file reindexing POST
`/api/v1/index`
## Notes
* Don't forget to put your spring.ai.openai.api-key to **application.properties**.
* API runs on `8080` port.
* RAG is able to read 4 types of files: **.JSON, .PDF, .MD, .TXT**.
* All data files should be put to `src/main/resources/data` directory
* The vector store is in-memory, so re-indexing is needed on each restart.
* To persist embeddings, consider integrating with a vector database like Postgres pgvector, Pinecone, or Qdrant.
* PDF reading uses spring-ai-pdf-document-reader.
## How to split chunks of data in files?
The data files in repository contain data to demonstrate the work and how data chunks should be split.

| Filetype | Way to split| what counts as a chunk         |
|----------|------------------------|--------------------------------|
| .txt     | empty line            | Each paragraph                 |
| .md      | with headings (#)     | Each section                   |
| .json    | Each object in array. | Each "title" + "content" combo |
