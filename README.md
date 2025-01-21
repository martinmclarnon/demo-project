# Demo Project with Kind, Tilt

This project manages an application that provides some complexity.
- It consists of two Vue3 Web frontends, 
- two Backend-for-frontends for each web frontend,
- two PostgreSQL databases for each Backend-for-frontend
- an order publish service that adds a message to
- a Kafka Message Broker
- and an order consumer service that adds a message to
- No-SQL MongoDB

## Table of Contents

- [Getting Started](#getting-started)
- [License](#license)

## Getting Started

#### Install HomeBrew
```bash
$ /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

#### Overview of Component Naming

| Component | Purpose | Suggested Name  |
| ----------- | ----------- | ----------- |
| Store BFF | Returns a list of books from inventory via ```GET```. |	```store-books-bff``` |
| Marketing BFF | Returns a list of blogs for readers via ```GET```. | ```marketing-blogs-bff```|
| Create Order Command API | Processes orders and publishes to a queue via ```POST```. |	```order-create-command-api``` |
| Create Order Audit App | Consumes order creation messages and persists to MongoDB. |	```order-create-audit-consumer```|

#### Summary of API Endpoints

| API Name | HTTP Method | Endpoint | Purpose |
| ----------- | ----------- |----------- | ----------- |
| Store BFF - GET Books | ```GET``` | ```/v1/store/books``` | Fetches a list of books from inventory. |
| Marketing BFF - GET Blogs | ```GET``` | ```/v1/marketing/blogs``` | Fetches a list of blogs for readers. |
| Create Order Command API | ```POST``` | ```/v1/orders``` | Creates a new order and publishes event. |