# Warehouse Stockflow (Full Stack)

Warehouse Stockflow is a full‑stack inventory management app that helps you track products, monitor stock levels, and simulate ordering flows.

## Repositories
- Backend (this repo): https://github.com/gadam7/Warehouse-StockFlow-App.git
- Frontend (Angular): https://github.com/gadam7/Warehouse-StockFlow-App-Frontend.git

## Run locally (Docker)

### 1) Clone both repositories into the same parent folder
```bash
mkdir warehouse-stockflow
cd warehouse-stockflow

git clone git@github.com:gadam7/Warehouse-StockFlow-App.git warehousestockflow
git clone git@github.com:gadam7/Warehouse-StockFlow-App-Frontend.git warehousestockflowapp
```

### 2) Start the full stack
```bash
cd warehousestockflow
cp .env.example .env
docker compose up -d --build
```

### 3) Open the app
- Frontend: http://localhost:4200
- Backend health: http://localhost:8080/actuator/health
- Backend info: http://localhost:8080/actuator/info

### Useful commands
```bash
docker compose ps
docker compose logs -f backend
docker compose logs -f mysql
docker compose logs -f frontend
docker compose down
```

Reset database (delete volumes + re-run init SQL on next start):
```bash
docker compose down -v
docker compose up -d --build
```

> Database initialization: the MySQL container runs SQL scripts from `docker/mysql/init/` only when the database volume is created for the first time. Use `docker compose down -v` to reset it.

## Demo Data (Seeded)

This project ships with seeded **Categories** and **Products** in the `dev` profile, so you can start the stack and use the app immediately (browse products, view stock status, generate reports, and place mock orders).

> Note: seed data is inserted on backend startup in `dev` profile. If you reset the database volume (`docker compose down -v`), it will be re-seeded on the next start.

## Accounts / Authentication (Local Demo)

You can create your own account from the UI using a **real email address**.

- After registration, the backend sends a verification email.
- Use the verification link/code you receive to activate the account, then log in.

### Roles for demo/testing
By default, newly created users are assigned a role on registration.

- **Recommended for reviewers:** you can optionally configure the project so newly created accounts get `ROLE_ADMIN` by default (for local demo purposes), so reviewers can access all features without manually editing roles in the database.

## Features
- Product details page (price, SKU, dates, description)
- Stock status badges: **IN STOCK / LOW IN STOCK / OUT OF STOCK**
- Mock order flow that updates stock quantities
- Reports:
  - **Low stock report** (products with quantity **1–20**)
  - **Out of stock report** (products with quantity **0**)
- Backend caching for reports (Spring Cache) with scheduled cache refresh

## Automatic Stock Simulation (dev)

To make the demo more interactive, the backend runs a scheduled job in the `dev` profile:

- **Every 50 seconds**, each product’s `quantityStock` is automatically increased by **+1**.
- “You may need to refresh the page (or revisit the reports page) to see the updated values.”

This helps reviewers quickly see stock changes reflected in the UI and in the reports without manually creating many orders.


## Tech Stack
- **Backend:** Java, Spring Boot (REST API), Spring Cache, Scheduler
- **Frontend:** Angular
- **Database:** MySQL
- **Build:** Maven, Node.js, Docker Compose

## Configuration (Secrets)
This repository does **not** include real secrets.
- `application.yaml` uses environment variables (`${...}`)
- Local secret configs (e.g. `application-dev.yaml`) are intentionally ignored via `.gitignore`