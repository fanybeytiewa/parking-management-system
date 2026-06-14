# 🚗 Parking Space Management System

A lightweight, robust, and beautifully designed desktop application for managing parking allocations. Built with **JavaFX** and **JDBC**, this project features a clean, responsive UI and an embedded zero-configuration database.

Developed as a coursework project for the **Software Systems** module at the **Technical University of Varna**.

---

## ✨ Features

* **Complete CRUD Functionality:** Add, edit, view, and delete parking spaces seamlessly.
* **Smart Data Validation:** Built-in checks prevent duplicate parking numbers and ensure data integrity before database insertion.
* **Auto-Initializing Database:** Uses an embedded **H2 Database**. The `DatabaseInitializer` automatically generates the schema (`parking_spaces` table) on the first run—no external SQL server needed!
* **Enterprise-Grade UI:** Features a custom CSS theme (`styles.css`) for a modern, clean, and corporate look, utilizing `TableView`, `ContextMenu`, and intuitive form layouts.

---

## 🛠️ Tech Stack

* **Language:** Java 17+
* **GUI Framework:** JavaFX (Controls, FXML, Custom CSS)
* **Database:** H2 Database Engine (Embedded)
* **Architecture:** DAO (Data Access Object) Pattern + MVC-like separation

---

## 📂 Architecture & Structure

The codebase is organized with a strict Separation of Concerns (SoC):

* **Model (`ParkingSpace`, `SpaceType`):** Pure Java POJOs and Enums representing the business logic.
* **DAO Layer (`ParkingSpaceDAO`, `DatabaseConnection`):** Encapsulates all SQL queries and JDBC connection management.
* **Controllers (`HelloController`, `AddController`):** Handle UI events, form validation, and bridge the View with the DAO layer.
* **Initialization (`HelloApplication`, `DatabaseInitializer`):** Application entry point that sets up the Stage and ensures the database is ready.

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone [https://github.com/fanybeytiewa/parking-management-system.git](https://github.com/fanybeytiewa/parking-management-system.git)
