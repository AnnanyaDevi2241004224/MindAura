# 🧠 MindAura - Mental Health Support Platform

MindAura is a secure and intelligent mental health support platform built using **Spring Boot**, offering features like **anonymous journaling**, **sentiment analysis**, **crisis keyword alerts**, and **JWT-based authentication**.

---

## 🚀 Features

- 📝 **Journaling System** – Write private or public journal entries.
- 📊 **Sentiment Analysis** – Analyze mood using HuggingFace NLP models.
- 📧 **Crisis Alerts** – Detect keywords (e.g., “suicide”, “worthless”) and send alerts via email.
- 🔐 **Authentication** – Secure registration and login with JWT.
- 🧑‍💻 **Admin Dashboard APIs** – View flagged or public entries.
- 🌐 **CORS-enabled** – Frontend integration ready (e.g., React, plain JS).

---

## 🛠️ Tech Stack

| Layer        | Tech                      |
|--------------|---------------------------|
| Backend      | Spring Boot, Spring Security |
| Language     | Java 17+                  |
| Database     | MySQL / H2 (for dev)      |
| Authentication | JWT (Stateless)         |
| Email Alerts | Spring Mail               |
| NLP          | HuggingFace API           |
| Frontend     | HTML/CSS/JS or any SPA    |

---

## ⚙️ API Endpoints

### 🧑 Auth
```http
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/debug/check-password
✅ Prerequisites
Java 17+

Maven

MySQL 

HuggingFace API key

Email SMTP credentials (for alerts)
