# Yummy Spicy

A full-stack restaurant ordering system built with Spring Boot, featuring an admin dashboard and a WeChat Mini Program for customers.

## Architecture

| Component | Stack | Port |
|---|---|---|
| Backend API | Spring Boot 2.7.3, MyBatis, MySQL, Redis | 4041 |
| Admin Dashboard | Nginx + static frontend | 80 |
| Customer App | WeChat Mini Program (UniApp) | — |

## Features

- **Menu Management** — dishes, categories, combo meals, and flavor options
- **Order System** — cart, checkout, WeChat Pay, real-time status via WebSocket
- **Admin Panel** — employee accounts, order handling, business analytics, Excel export
- **Auth** — JWT-based with separate tokens for admin and customer roles
- **Storage & Caching** — Aliyun OSS for uploads, Redis for caching

## Project Structure

```
yummy-backend/
  yummy-common/    # Shared utilities, constants, exceptions
  yummy-pojo/      # Entities, DTOs, VOs
  yummy-server/    # Controllers, services, mappers (main app)
yummy-frontend/    # Admin dashboard + Nginx config
yusi-mini-program/ # WeChat Mini Program
```

## Quick Start

### Prerequisites

- JDK 11+
- Maven 3.6+
- MySQL 8.0+
- Redis

### Backend

```bash
cd yummy-backend
mvn clean install
java -jar yummy-server/target/yummy-server-1.0-SNAPSHOT.jar
```

Configure database and Redis connections in `yummy-server/src/main/resources/application-dev.yml`.

### Frontend

```bash
cd yummy-frontend
# Start Nginx with the provided config to serve the admin dashboard
# and proxy API requests to the backend on port 4041
```

## License

This project is for educational and personal use.
