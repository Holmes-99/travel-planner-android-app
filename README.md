# ✈️ Travel Planner App

![Android](https://img.shields.io/badge/Android-Java-3DDC84?style=flat&logo=android&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=flat&logo=springboot&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-003B57?style=flat&logo=sqlite&logoColor=white)
![Status](https://img.shields.io/badge/Status-Complete-3fb950?style=flat)

> Full-stack Android travel planner built with Java & Spring Boot — ENCS515 course project @ Birzeit University 🇵🇸

---

## 📱 Features

- 🔐 Login & registration with SHA-1 encrypted passwords
- 🌍 Browse trips fetched from a RESTful API
- 🔍 Search & filter trips by destination or country
- ❤️ Add trips to favorites
- 📋 Reserve trips with quantity and type selection
- ⭐ Special section — top rated trips (rating ≥ 4.5)
- 👤 Profile management with update functionality
- 🛡️ Admin panel — manage users, trips & reservations
- 📞 Contact screen with call / maps / email intents
- 🎨 Navigation Drawer with 8 sections

---

## 🏗️ Architecture

| Layer | Tech | Role |
|-------|------|------|
| Android frontend | Java · SQLite · RecyclerView | All UI, local DB, fragments |
| HTTP layer | HttpURLConnection · AsyncTask | Fetches trips from REST API |
| Spring Boot backend | Java · JPA · MySQL | REST endpoints for trips, users, reservations |
| External API | MockAPI.io | Hosts trip data for APK demo |

---

## 🛠️ Android concepts used

`Navigation Drawer` · `Fragments` · `RecyclerView` · `SQLite (DataBaseHelper)` · `SharedPreferences singleton` · `AsyncTask + HttpURLConnection` · `Tween animations (fade / scale / slide)` · `AlertDialog` · `Spinner` · `Intent (call / maps / email)`

---

## 📁 Project structure

```
app/src/main/java/
├── activities/     SplashActivity · IntroActivity · LoginActivity
│                   RegisterActivity · HomeActivity · AdminActivity
├── fragments/      HomeFragment · TripsFragment · TripDetailFragment
│                   FavoritesFragment · ReservationsFragment
│                   SpecialFragment · ProfileFragment · ContactFragment
│                   Admin* (users · trips · add · reservations)
├── adapters/       TripAdapter (RecyclerView)
├── database/       DataBaseHelper (4 tables)
├── network/        HttpManager · TripJsonParser · ConnectionAsyncTask
└── utils/          SharedPreManager (singleton)

src/main/java/com/example/travelplanner/
├── controllers/    TripController · UserController · ReservationController
├── models/         Trip · User · Reservation
├── repositories/   JpaRepository interfaces
└── services/       Business logic layer
```

---

## 🚀 Running locally

### Backend (Spring Boot)
```bash
# Requires MySQL running on port 3306 with database `travel_db`
cd travelplanner
./mvnw spring-boot:run
# API available at http://localhost:8080
```

### Android app
1. Open the project in Android Studio
2. Make sure an emulator is running (Pixel 3a XL · API 28)
3. Press ▶️ — the app fetches trips from MockAPI on first launch

---

## 👥 Team

**Shatha Abualrob** (1231279) · **Lara Daifallah** (1230239)  
ENCS515 Advanced Computer Systems Engineering Lab · First Semester 2026  
Birzeit University 🇵🇸

[![GitHub](https://img.shields.io/badge/GitHub-Holmes--99-181717?style=flat&logo=github)](https://github.com/Holmes-99)
