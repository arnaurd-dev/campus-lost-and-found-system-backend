# Campus Lost And Found System Backend

An enterprise RESTful Web API for the **Campus Lost And Found System**, developed as part of the Web Technology and Internet Mid-Term Examination at the Adventist University of Central Africa (AUCA).

---

## 👨‍🎓 Student & Examination Profile

* **Student Name:** NGOGA Desire Arnaud
* **Student ID:** 27627
* **Project Name:** Campus Lost And Found System
* **Course Code & Name:** INSY 8322 - Web Technology and Internet
* **Lecturer:** Patrick DUSHIMIMANA
* **Academic Year:** 2026-2027 (Semester 1)
* **Group Day:** Tuesday
* **Faculty:** Faculty of Information Technology
* **Institution:** Adventist University of Central Africa (AUCA)

---

## 📌 Domain Overview & Architecture

The **Campus Lost And Found System** provides a centralized system to record, track, match, and return lost or found items across campus collection points. 

The architecture follows the exact design patterns of the reference Kigali Clinic project:

| Kigali Clinic Entity | Campus Lost & Found Entity | Role in System |
| :--- | :--- | :--- |
| **Patient** | `Reporter` | The student or staff member filing the report (unique key: `campusId`). |
| **Doctor** | `Custodian` | The staff member handling reports, assigned to a collection point and categories. |
| **Office** | `CollectionPoint` | The physical desk or room on campus where custodians operate. |
| **Specialization** | `ItemCategory` | Categories of items handled (e.g., Electronics, Documents, Bags, Keys). |
| **Appointment** | `ItemReport` | The dated transaction record tracking the lifecycle of an item. |
| **Prescription** | `ReturnReceipt` | Official receipt issued only after an item report reaches `RETURNED` status. |

---

## 🏗️ Entity Model & Database Schema

Every entity uses a JPA-generated `UUID` as its primary key.

```text
  [Reporter] (campusId)
       │ 1
       │ has many
       ▼ *
  [ItemReport] ◀───── handled by ───── [Custodian]
       │ 1                                   │ *
       │ issued for                          │ occupies 1
       ▼ *                                   ▼ 1
 [ReturnReceipt]                       [CollectionPoint]
                                             │ *
                                             │ holds many
                                             ▼ *
                                       [ItemCategory]
```

### Table Definitions & Relationships
* **`reporter`**: `id` (UUID, PK), `firstName`, `lastName`, `campusId` (Unique), `phone`, `email`.
* **`collection_point`**: `id` (UUID, PK), `pointNumber`, `name`. Knows nothing about custodians.
* **`item_category`**: `id` (UUID, PK), `name`. `@ManyToMany(mappedBy = "itemCategories")` marked `@JsonIgnore` to prevent recursive JSON loops.
* **`custodian`**: `id` (UUID, PK), `firstName`, `lastName`, `phone`.
  * `@ManyToOne` `CollectionPoint` (`collection_point_id`, nullable).
  * `@ManyToMany` `ItemCategory` via join table `custodian_item_category`.
* **`item_report`**: `id` (UUID, PK), `reportDate`, `status` (`ReportStatus` enum: `REPORTED`, `MATCHED`, `RETURNED`, `CLOSED`), `itemName`, `locationFound`.
  * `@ManyToOne` `Reporter` (`reporter_id`).
  * `@ManyToOne` `Custodian` (`custodian_id`).
* **`return_receipt`**: `id` (UUID, PK), `itemName`, `verificationNote`, `itemsReturned`, `issuedDate`.
  * `@ManyToOne` `ItemReport` (`item_report_id`).

---

## 🗄️ Database Setup & Configuration

### 1. Database Creation
In PostgreSQL (pgAdmin or psql shell):
```sql
CREATE DATABASE campus_lost_and_found_db;
```

### 2. Configuration Settings (`application.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/campus_lost_and_found_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.port=8080
```

---

## 🚀 How to Run the Application

### Option 1: Via Maven Wrapper in Terminal
```powershell
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

### Option 2: In VS Code / IntelliJ IDEA
Open `src/main/java/com/auca/campus_lost_and_found_system/CampusLostAndFoundSystemApplication.java` and click **Run**.

The application starts on port `8080`.

---

## 📦 Step 0: Seed Data Reference

The database has been seeded with data satisfying every query scenario of the examination:
* **Item Categories (4):** Electronics, Documents, Bags, Keys. (*Nobody holds Keys*).
* **Collection Points (4):** Points 1, 2, 3, 4. (*Point 4 has no custodian*).
* **Custodians (3):** 
  * Patrick Mugisha (Point 1, holds Electronics & Documents).
  * Alice Uwase (Point 2, holds Bags).
  * Eric Kwizera (Point 3, holds Documents).
* **Reporters (5):**
  * Arnaud Ngoga (`AUCA-27627`)
  * Desire Ngoga (`AUCA-27628`)
  * Claire Mukamana (`AUCA-27629`)
  * David Habimana (`AUCA-27630`)
  * Grace Ingabire (`AUCA-27631`)
* **Item Reports (10):** Spread over October & November 2026 across all 4 statuses (`REPORTED`, `MATCHED`, `RETURNED`, `CLOSED`). Two reports on `2026-10-20` for Patrick. At least 2 reports in `RETURNED` status.

---

## 📋 Complete API Endpoints Specification

### Part A: Domain Model & CRUD (15 Marks)
| Requirement | Method | Endpoint | Expected Behaviour |
| :--- | :--- | :--- | :--- |
| **A1** | - | - | 5 Entities, 1 Enum, Foreign Keys, Join Table, `@JsonIgnore`. |
| **A2** | POST | `/api/{path}/save` | Returns `201 Created` with `"{Entity} saved successfully"`. |
| **A2** | GET | `/api/{path}/all` | Returns `200 OK` with all entity records. |
| **A2** | GET | `/api/{path}/{id}` | Returns `200 OK` or `404 Not Found` if id does not exist. |
| **A2** | PUT | `/api/{path}/update/{id}` | Returns `200 OK` or `404 Not Found` if id does not exist. |
| **A2** | DELETE | `/api/{path}/delete/{id}` | Returns `200 OK` or `404 Not Found` if id does not exist. |
*Paths:* `item-categories`, `collection-points`, `custodians`, `reporters`, `item-reports`.

---

### Part B: Derived Queries (15 Marks)
| Requirement | Method | Endpoint | Query Type | Logic |
| :--- | :--- | :--- | :--- | :--- |
| **B1** | GET | `/api/reporters/by-last-name?lastName=ngoga` | DERIVED | `findByLastNameIgnoreCaseOrderByFirstNameAsc` (case-insensitive, sorted A-Z). |
| **B2** | GET | `/api/item-reports/by-status?status=REPORTED` | DERIVED | `findByStatusOrderByReportDateAsc` (filtered by enum, earliest date first). |
| **B3** | GET | `/api/item-reports/between?start=2026-10-01&end=2026-10-31` | DERIVED | `findByReportDateBetweenOrderByReportDateAsc` (parsed as `LocalDate` in controller). |

---

### Part C: JPQL with Joins (20 Marks)
| Requirement | Method | Endpoint | Query Type | Logic |
| :--- | :--- | :--- | :--- | :--- |
| **C1** | GET | `/api/custodians/by-item-category?name=electronics` | JPQL | `JOIN c.itemCategories` with `LOWER()` comparison. |
| **C2** | GET | `/api/collection-points/free` | JPQL | `NOT EXISTS` subquery returning points without a custodian. |
| **C3** | GET | `/api/item-categories/unused` | JPQL | `IS EMPTY` on `c.custodians` (returns Keys). |
| **C4** | GET | `/api/reporters/of-custodian/{custodianId}` | JPQL | `SELECT DISTINCT ir.reporter` with 404 validation. |

---

### Part D: Business Rules (25 Marks)
| Requirement | Method | Endpoint | Type | Validation & Rules |
| :--- | :--- | :--- | :--- | :--- |
| **D1** | POST | `/api/item-reports/save` | Business Rule | Past date check (`400 Bad Request`); conflict check via `existsByCustodianAndReportDateAndStatusNot` (`409 Conflict`); defaults to `REPORTED`. |
| **D2** | PATCH | `/api/item-reports/{id}/status?status=MATCHED` | Business Rule | State transition control (`REPORTED` → `MATCHED`/`CLOSED`, `MATCHED` → `RETURNED`/`CLOSED`). Invalid moves return `400 "Cannot change status from X to Y"`. |
| **D3** | PUT | `/api/custodians/{custodianId}/item-categories/{catId}` | Business Rule | Adds category to custodian; returns `409` if already assigned; `404` if not found. |
| **D4** | PATCH | `/api/custodians/{custodianId}/collection-point?pointNumber=4` | Business Rule | Assigns point via `findByPointNumber`; returns `409` if taken via `existsByCollectionPoint`. |

---

### Part E: ReturnReceipt Entity & Operations (15 Marks)
| Requirement | Method | Endpoint | Query Type | Logic |
| :--- | :--- | :--- | :--- | :--- |
| **E1** | POST | `/api/return-receipts/save?itemReportId={id}` | Rule | Only allowed for `RETURNED` reports (400 if not returned); sets `issuedDate` to today (`201 Created`). |
| **E2** | GET | `/api/return-receipts/reporter/{campusId}` | JPQL | Path navigation `r.itemReport.reporter.campusId ORDER BY r.issuedDate DESC`. |
| **E3** | DELETE | `/api/return-receipts/delete/{id}` | Derived | Deletes receipt; protects item report deletion with `409 Conflict` via `existsByItemReport`. |

---

### Part F: Reports & Bulk Updates (10 Marks)
| Requirement | Method | Endpoint | Query Type | Logic |
| :--- | :--- | :--- | :--- | :--- |
| **F1** | GET | `/api/item-reports/stats/by-status` | JPQL | `GROUP BY ir.status` and `COUNT(ir)`. |
| **F2** | PATCH | `/api/item-reports/cancel-day?custodianId={id}&date={date}` | JPQL | Bulk `@Modifying` `@Transactional` `UPDATE` setting reports to `CLOSED` except `RETURNED`. Returns count string. |

---

### Part G: Bonus Requirements (+10 Marks)
| Requirement | Method | Endpoint | Query Type | Description |
| :--- | :--- | :--- | :--- | :--- |
| **Bonus 1** | GET | `/api/dashboard/summary` | Query / Count | Returns `LinkedHashMap` summary: `reporters`, `custodians`, `freeCollectionPoints`, `reportsToday`, `reported`, `receipts`. |
| **Bonus 2** | GET | `/api/item-reports/page?page=0&size=5&sort=reportDate,desc` | Pageable | Dynamic pagination using Spring Data `Pageable` with sorting. |

---

## 🧪 Postman Collection & Live Demo Verification

The complete Postman test suite is exported at the root of this repository:
```text
campus-lost-and-found-system-midterm.postman_collection.json
```

### How to Import and Run:
1. Open the **Postman Desktop App**.
2. Click **Import** (top left corner).
3. Select `campus-lost-and-found-system-midterm.postman_collection.json` from the root of this cloned project.
4. All requests are named strictly by requirement ID (`A2`, `B1`, `C4`, `D2`, etc.).

### Live Demo Quick Reference (Success vs. Error Cases):
| ID | Requirement | Success Case (200 / 201) | Error Case (400 / 404 / 409) |
| :--- | :--- | :--- | :--- |
| **B1** | By Last Name | `?lastName=ngoga` → Returns Arnaud & Desire (200) | `?lastName=unknown` → Returns `[]` (200) |
| **C4** | Reporters of Custodian | Patrick's UUID → Returns unique reporters (200) | Unknown UUID → `404 Not Found` |
| **D1** | Safe Saving | Valid future date → `201 Created` | Past date (`400`) / Conflicting date (`409`) |
| **D2** | Status Flow | `REPORTED` → `MATCHED` (`200 OK`) | `RETURNED` → `REPORTED` (`400 Bad Request`) |
| **D4** | Move Custodian | Move to free Point 4 (`200 OK`) | Move to occupied Point 2 (`409 Conflict`) |
| **E1** | Return Receipt | Write for RETURNED report (`201 Created`) | Write for REPORTED report (`400 Bad Request`) |
| **E3** | Delete & Protect | Normal delete (`200 OK`) | Delete report with receipts (`409 Conflict`) |
