# 🏘️ Cooperative Housing Society Management System

A modern **JavaFX-based desktop application** designed to streamline residential society operations by providing a centralized platform for **member management, maintenance tracking, and complaint resolution**. The system offers a user-friendly interface, real-time dashboard insights, and efficient record management through a modular and scalable architecture.

---

![Java](https://img.shields.io/badge/Java-21+-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![OOP](https://img.shields.io/badge/Object%20Oriented%20Programming-OOP-success)
![Design Pattern](https://img.shields.io/badge/Design%20Pattern-Singleton-purple)
![Desktop Application](https://img.shields.io/badge/Desktop%20Application-JavaFX-green)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen)

---

# 📌 Project Overview

Managing a residential housing society involves maintaining member records, collecting maintenance fees, and resolving resident complaints efficiently.

This project provides a digital solution that automates these tasks through an interactive desktop application built using JavaFX. It enables society administrators to monitor key metrics, manage residents, track maintenance payments, and handle complaints through a single dashboard.

---

# ✨ Key Features

## 📊 Dashboard Module

- Real-time overview of society operations
- Displays:
  - Total Members
  - Active Members
  - Pending Maintenance Records
  - Open Complaints
- Shows recent complaints and maintenance activities
- Provides quick access to critical information

---

## 👥 Member Management

- Register new society members
- Store resident information:
  - Flat Number
  - Owner/Tenant Name
  - Contact Number
  - Email Address
  - Flat Type
  - Membership Type
- Input validation and duplicate checking
- Activate/Deactivate members

---

## 💰 Maintenance Management

- Generate monthly maintenance bills
- Track payment records
- Automatically classify payments as:
  - ✅ Paid
  - ⚠️ Partial
  - ❌ Pending
- Prevent duplicate bill generation
- View maintenance history in a structured table

---

## 📝 Complaint Management

- Raise complaints under multiple categories:
  - Water
  - Electricity
  - Lift
  - Security
  - Parking
  - Sanitation
  - Other
- Track complaint lifecycle:
  - Open
  - In Progress
  - Resolved
- Update complaint status dynamically

---

# 🏗️ System Architecture

```text
                 ┌────────────────────┐
                 │      Dashboard      │
                 └─────────┬──────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼

 ┌────────────┐   ┌──────────────┐   ┌──────────────┐
 │  Members   │   │ Maintenance  │   │ Complaints  │
 └─────┬──────┘   └──────┬───────┘   └──────┬───────┘
       │                 │                  │
       └─────────────────┴──────────────────┘
                         │
                         ▼

              ┌─────────────────┐
              │   SocietyData   │
              │ (Singleton)     │
              └─────────────────┘
```

---

# 🧠 OOP Concepts Used

### Encapsulation
- Data stored using private fields and public methods.

### Abstraction
- Functional modules separated into independent panels.

### Inheritance
- JavaFX UI components inherit from base JavaFX classes.

### Polymorphism
- Utilized through JavaFX event handling and UI controls.

### Singleton Design Pattern
- Centralized data management using the `SocietyData` class.

---

# 🛠️ Technology Stack

| Technology | Purpose |
|------------|----------|
| Java | Core Programming Language |
| JavaFX | Desktop GUI Development |
| CSS | UI Styling |
| Eclipse IDE | Development Environment |
| OOP | Software Design |
| Singleton Pattern | Data Management |

---

# 📂 Project Structure

```text
SocietyManager
│
├── src
│   └── society
│       ├── Main.java
│       ├── MainView.java
│       ├── DashboardPanel.java
│       ├── Member.java
│       ├── MemberPanel.java
│       ├── MaintenanceRecord.java
│       ├── MaintenancePanel.java
│       ├── Complaint.java
│       ├── ComplaintPanel.java
│       ├── SocietyData.java
│       └── styles.css
│
└── README.md
```

---

# 🚀 Installation & Setup

## Prerequisites

- Java JDK 17 or above
- JavaFX SDK 21
- Eclipse IDE

---

## Clone Repository

```bash
git clone https://github.com/yourusername/Cooperative-Housing-Society-Management-System.git
```

---

## Configure JavaFX

Add JavaFX SDK libraries to your project.

### VM Arguments

```bash
--module-path "PATH_TO_JAVAFX/lib" --add-modules javafx.controls,javafx.fxml
```

### macOS

```bash
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -XstartOnFirstThread
```

---

## Run Application

Execute:

```text
Main.java
```

---

# 📸 Screenshots

## Dashboard
<img width="100%" alt="Dashboard" src=""C:\Users\lokesh.ck\Desktop\SHREYA\SEM - IV\OOSD Lab\Screenshots\Dashboard.png"">

## Member Management
<img width="100%" alt="Members" src=""C:\Users\lokesh.ck\Desktop\SHREYA\SEM - IV\OOSD Lab\Screenshots\Member Management.png"">

## Maintenance Management
<img width="100%" alt="Maintenance" src=""C:\Users\lokesh.ck\Desktop\SHREYA\SEM - IV\OOSD Lab\Screenshots\Maintainance Management.png"">

## Complaint Management
<img width="100%" alt="Complaints" src=""C:\Users\lokesh.ck\Desktop\SHREYA\SEM - IV\OOSD Lab\Screenshots\Complaint Management.png"">

---

# 🔮 Future Enhancements

- Database Integration (MySQL/PostgreSQL)
- User Authentication System
- Online Payment Gateway
- Report Generation (PDF/Excel)
- Resident Portal
- Mobile Application Support
- Advanced Analytics Dashboard

---

# 🎯 Learning Outcomes

Through this project, I gained hands-on experience in:

- JavaFX Application Development
- Object-Oriented Design
- Design Patterns
- Desktop Application Architecture
- UI/UX Design Principles
- Data Validation & State Management

---

# 👩‍💻 Author

**Shreya L**

B.Tech Computer Science Cyber Security | Manipal Institute of Technology | Bangalore

---
