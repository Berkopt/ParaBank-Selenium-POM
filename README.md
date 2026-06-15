# ParaBank Test Automation Framework (GUI & API)

Ez egy komplex tesztautomatizálási mintaprojekt, amely a **ParaBank** demó banki alkalmazás webes felületét (GUI) és backend szolgáltatásait (API) teszteli. A projekt elsődleges célja a modern tesztautomatizálási tervezési minták (pl. Page Object Model), valamint a frontend és backend tesztek egyetlen keretrendszerbe foglalásának bemutatása.

---

## 🛠️ Alkalmazott Technológiák & Eszközök

* **Programozási nyelv:** Java 25 (vagy újabb JDK)
* **Build eszköz:** Maven
* **GUI Automatizálás:** Selenium WebDriver (4.18+)
* **API Tesztelés:** RestAssured (5.4+)
* **Teszt keretrendszer:** JUnit 5 (Jupyter & Platform Suite)
* **Állítások (Assertions):** Hamcrest Matchers
* **Tervezési minta:** Page Object Model (POM)

---

## 📋 Tesztlefedettség

A projektben a tesztek logikailag és szerkezetileg is különválnak, de közös JUnit 5 Suite-okon keresztül egyben is futtathatóak.

### 🖥️ GUI Tesztek (10 db teszteset)
A felületi tesztek a Page Object Model (POM) struktúrát követik, biztosítva az újrahasznosíthatóságot és a könnyű karbantarthatóságot.
* Sikeres és sikertelen bejelentkezési forgatókönyvek.
* Adatforrás (Data-driven) alapú tesztelés.
* Új adatbevitel, listázás és többoldalas lista-elemek kezelése.
* Meglévő adatok módosítása, mentése és törlése.
* Biztonságos kijelentkezés ellenőrzése.

### 🔌 API Tesztek (5 db teszteset)
A backend tesztek a RestAssured könyvtár segítségével közvetlenül a REST végpontokat hívják meg.
* **API Chaining (Láncolás):** Dinamikus adatkinyerés a JSON válaszokból (pl. Customer ID, Account ID kinyerése), majd ezek automatikus továbbküldése a következő API hívások paramétereibe.
* Státuszkódok (200 OK) és JSON struktúrák (pl. típusok, tömbi méretek) validálása Hamcrest segítségével.
* Számlák listázása és ellenőrzése.

---

## 🚀 Futtatás

A tesztek futtatásához klónozd a repót, majd használd az alábbi lehetőségek egyikét:

### 1. IntelliJ IDEA-ból (Ajánlott)
A tesztek futtatásához célszerű a JUnit 5 Suite osztályokat használni, így egy kattintással indítható a teljes csomag:
* **GUI tesztek futtatása:** `tests.testcases.AllTestsSuite` osztály megnyitása -> Futtatás gomb.
* **API tesztek futtatása:** `api.ApiTestSuite` osztály megnyitása -> Futtatás gomb.

### 2. Parancssorból (Maven)
A teljes tesztcsomag futtatása:
```bash
mvn test