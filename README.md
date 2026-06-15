# ParaBank Test Automation Framework (GUI & API)

This is a comprehensive test automation reference project designed to validate both the web user interface (GUI) and the backend REST services (API) of the **ParaBank** demo banking application. The primary goal of this repository is to demonstrate modern test automation design patterns (such as the Page Object Model) and showcase how frontend and backend validation can seamlessly coexist within a single framework.

---

## 🛠️ Tech Stack & Tools

* **Programming Language:** Java 26 (or newer JDK)
* **Build Tool:** Maven
* **GUI Automation:** Selenium WebDriver (4.18+)
* **API Testing:** RestAssured (5.4+)
* **Test Framework:** JUnit 5 (Jupyter & Platform Suite)
* **Assertions:** JUnit 5 Assertions & Hamcrest Matchers
* **Design Pattern:** Page Object Model (POM)

---

## 📋 Test Coverage

The test suites within this project are logically and structurally isolated, yet they remain tightly integrated through JUnit 5 Suites for comprehensive execution.

### 🖥️ GUI Automation Suite (10 Test Cases)
The frontend tests strictly adhere to the Page Object Model (POM) architecture, ensuring high reusability, clean locator separation, and sustainable maintenance.
* **Authentication:** Robust verification of successful and failed login workflows.
* **Data-driven Testing:** Externalized data ingestion and processing.
* **Data Operations:** Form submissions, looping record lists, and multi-page data grid traversal.
* **Profile Management:** Modifying, saving, and verifying persistent user profile metadata.
* **Session Controls & Clean-up:** Fail-safe application database reset operations and secure session logouts.

### 🔌 REST API Suite (5 Test Cases)
The backend test suite interacts directly with the system's endpoints using the RestAssured library.
* **API Chaining:** Advanced runtime data extraction from JSON responses (e.g., dynamic harvesting of Customer IDs and Account IDs) and injecting them as downstream path/query parameters in subsequent requests.
* **Response Validation:** Strict assertions evaluating HTTP status codes (200 OK) and deeply nested JSON response payloads (data types, arrays, and bounds) using Hamcrest matchers.
* **Account Scenarios:** Querying, listing, and profiling programmatic financial account creation.

---

## 🚀 Execution Guide

To execute the test suites locally, clone the repository and choose one of the deployment options outlined below:

### 1. Via IntelliJ IDEA (Recommended)
The most efficient way to run the test cases inside the IDE is by target-firing the dedicated JUnit 5 Suite wrapper classes:
* **To run all GUI tests:** Navigate to the `tests.testcases.AllTestsSuite` class and click the **Run** icon.
* **To run all API tests:** Navigate to the `api.ApiTestSuite` class and click the **Run** icon.

### 2. Via Command Line Interface (Maven)
To trigger the entire test suite across both scopes inside your terminal, execute the following command:
```bash
mvn test