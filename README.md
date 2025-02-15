# WoW_MySQL - World of Warcraft Character API Backend

## 📌 Overview
WoW_MySQL is a **Spring Boot backend application** that fetches character data from the **Blizzard API** and stores it in a **MySQL database**. This project uses **Java 23, Spring Boot, MySQL (via Docker), and Lombok**.

## 🚀 Features
- Fetches character data from the **Blizzard API**.
- Stores retrieved data in a **MySQL database**.
- Exposes REST API endpoints to access stored character data.
- Uses **Docker** for database setup.
- Built with **Spring Boot** and **Lombok** for clean code.

---

## 📂 Project Setup
Follow these steps to **download, configure, and run** the project.

### 1️⃣ **Clone the Repository**
```sh
git clone https://github.com/YOUR_GITHUB_USERNAME/WoW_MySQL.git
cd WoW_MySQL
```

### 2️⃣ **Ensure You Have Java 23 Installed**
Check your Java version:
```sh
java -version
```
If you don't have **Java 23**, install it from [OpenJDK](https://jdk.java.net/23/).

### 3️⃣ **Install Dependencies**
Use Maven to install project dependencies:
```sh
mvn clean install
```

### 4️⃣ **Setup MySQL with Docker**
Ensure **Docker Desktop** is installed and running. Then start a **MySQL container**:
```sh
docker run --name wow-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=wowdb -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -p 3307:3306 -d mysql:8.0
```

### 5️⃣ **Configure Blizzard API Credentials**
1. Go to the [Blizzard Developer Portal](https://develop.battle.net/).
2. Create an API client.
3. Copy your **Client ID** and **Client Secret**.
4. Open `src/main/resources/application.properties` and update:
   ```properties
   blizzard.client-id=YOUR_CLIENT_ID
   blizzard.client-secret=YOUR_CLIENT_SECRET
   ```

### 6️⃣ **Run the Application**
Start the Spring Boot backend:
```sh
mvn spring-boot:run
```

---

## 📡 API Usage
### ✅ **Fetch & Store a WoW Character**
```http
GET http://localhost:8080/api/characters/fetch/eu/kilrogg/mcmemes
```
This will retrieve **McMemes** from **Kilrogg (EU)** and store it in MySQL.

### ✅ **Get All Stored Characters**
```http
GET http://localhost:8080/api/characters/
```
Returns a list of all stored characters.

---

## 🛠️ **Database Access**
To manually check stored data, connect **MySQL Workbench** to:
- **Hostname:** `127.0.0.1`
- **Port:** `3307`
- **User:** `admin`
- **Password:** `admin`
- **Database:** `wowdb`

Then, run:
```sql
SELECT * FROM wowcharacter;
```

---

## 🏗️ Future Plans
- 🔹 **Add a frontend (React or Thymeleaf)** to display character data.
- 🔹 Expand to **fetch gear, achievements, and PvP stats**.
- 🔹 Implement **pagination and search functionality**.

---

## 💡 Contribution
Feel free to fork the repo, submit issues, or make pull requests!

---

## 📝 License
This project is open-source and available under the **MIT License**.

