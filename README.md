# Cooperative-Housing-Society-Management-System-using-JavaFX

A modern **JavaFX-based desktop application** designed to efficiently manage residential society operations, including member records, maintenance billing, and complaint tracking — all through an intuitive and visually appealing interface.

---

## 🚀 Features

### 📊 Dashboard

* Real-time overview of society data
* Displays:

  * Total Members
  * Active Members
  * Pending Maintenance
  * Open Complaints
* Shows recent complaints and maintenance updates

---

### 👥 Member Management

* Add and manage resident details
* Fields include:

  * Flat Number
  * Name
  * Contact & Email
  * Membership Type (Owner/Tenant)
* Input validation (e.g., contact number format)
* Toggle member status (Active / Inactive)
* Tabular display using JavaFX TableView

---

### 💰 Maintenance Management

* Generate monthly maintenance bills
* Record payments for each member
* Automatic status calculation:

  * ✅ Paid
  * ⚠️ Partial
  * ❌ Pending
* Prevents duplicate bill generation

---

### 📝 Complaint Management

* Raise complaints with categories:

  * Water, Electricity, Lift, Security, etc.
* Track complaints through lifecycle:

  * Open → In Progress → Resolved
* Filter and update complaint status dynamically

---

## 🧠 System Design

* Built using **Object-Oriented Programming (OOP)** principles
* Uses **Singleton Pattern** for centralized data management
* Implements **Observable Lists** for real-time UI updates
* Modular architecture with separate panels for each feature

---

## 🛠️ Tech Stack

* **Language:** Java
* **Framework:** JavaFX
* **IDE:** Eclipse
* **UI Styling:** CSS

---

## 🎯 Highlights

* Clean and modern **dark-themed UI**
* Dynamic and responsive interface
* Structured and scalable codebase
* Real-time data reflection across modules

---

## 📸 Screenshots

> *(Add your screenshots here)*

* Dashboard View
* Member Management
* Maintenance Module
* Complaint Management

---

## 🚀 How to Run

1. Install **Java (JDK 17 or 21 recommended)**
2. Download **JavaFX SDK (21.0.10)**
3. Add JavaFX libraries to project classpath
4. Run with VM arguments:

```bash
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

*(Mac users: add `-XstartOnFirstThread`)*

---

## 🔮 Future Enhancements

* Database integration (MySQL / PostgreSQL)
* User authentication system
* Role-based access (Admin / Resident)
* Report generation and analytics
* Web or mobile version

---

## 👩‍💻 Author

**Shreya L**

2nd year Cyber Security Student MIT Bangalore

---
