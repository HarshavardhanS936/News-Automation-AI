# Neo-Hack News Aggregator

A next-generation news aggregation platform that helps users consume news intelligently. Not only does it aggregate headlines, but it also analyzes them for sentiment, bias, and credibility to provide a holistic view of the information landscape.

## 🚀 Features

- **Smart Aggregation**: Collects news from multiple sources (NewsAPI, The Guardian, RSS Feeds).
- **Sentiment Analysis**: Evaluates articles to determine if the tone is positive, negative, or neutral.
- **Bias Detection**: Highlights potential bias in articles based on sensationalism and loaded language.
- **Trending Intelligence**: Identifies breaking, sustained, and emerging trends using real-time momentum scoring.
- **Personalized Feed**: Delivers content tailored to user preferences and reading history.
- **Multi-Language Support**: Frontend supports internationalization (i18n).

## 🛠️ Tech Stack

### Frontend
- **React (Vite)**
- **Tailwind CSS**
- **TypeScript**

### Backend
- **Spring Boot (Java 17+)**
- **Maven**
- **MySQL Database**

## ⚙️ Getting Started

### 1. Database Setup
Ensure you have a MySQL server running locally. By default, the application is configured to connect to:
- **URL**: `jdbc:mysql://127.0.0.1:3306/news_aggregator`
- **Username**: `root`
- **Password**: `root`

*(The database `news_aggregator` will be created automatically if it doesn't exist)*

### 2. Running the Backend
Navigate to the `backend` directory and run the Spring Boot application using Maven:
```bash
cd backend
./mvnw spring-boot:run
```
*(The backend typically runs on port 8081)*

### 3. Running the Frontend
Navigate to the `frontend` directory, install the dependencies, and start the development server:
```bash
cd frontend
npm install
npm run dev
```
*(The frontend will be accessible at http://localhost:5173 by default)*

## 🔐 Authentication & Admin Access

The application uses JWT-based authentication. 

### How to get Admin Access
There are no hardcoded default credentials for security reasons. Instead, the system is designed to automatically grant admin privileges to a specific username upon registration.

To log in as an administrator:
1. Start the application and go to the **Registration** page on the frontend.
2. Register a new account with the exact username: **`admin`**
3. Choose any password you prefer.
4. Log in with the username `admin` and the password you just created.

The system will automatically detect the username `admin` during registration and assign the `ROLE_ADMIN` permissions to your account. You will then have access to all admin-specific features such as managing users, managing articles, and viewing system status.
