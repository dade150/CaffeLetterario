# 📚 Caffè Letterario - Gestionale Web (Java EE)

Applicazione web per la gestione di un locale che unisce ristorazione e lettura. Il sistema permette la gestione del menu, dei libri, delle recensioni e delle prenotazioni.

---

## 🛠️ 1. Requisiti Hardware e Software
* **Java Development Kit (JDK):** Versione 11 o 17.
* **Web Server:** Apache Tomcat 9.0.x (si consiglia la 9 per massima compatibilità con javax.servlet).
* **Database:** Docker Desktop installato.
* **IDE:** IntelliJ IDEA (con plugin Smart Tomcat) o Eclipse.

---

## 📦 2. Librerie Necessarie (Dependencies)
Per il corretto funzionamento, il progetto richiede i seguenti file `.jar`. Se non sono presenti nella cartella `web/WEB-INF/lib`, vanno scaricati e aggiunti manualmente:

1.  **MySQL Connector/J (8.0.33):** Necessario per la connessione tra Java e il database MySQL.
    * [Download Connector/J](https://dev.mysql.com/downloads/connector/j/)
2.  **JSTL (JavaServer Pages Standard Tag Library 1.2):** Necessaria per i tag `<c:forEach>`, `<c:if>`, ecc. nelle pagine JSP.
    * File richiesti: `jstl-1.2.jar` e `standard.jar`.
3.  **Servlet API:** Inclusa solitamente in Tomcat, ma necessaria per la compilazione.

**Come aggiungerle su IntelliJ:**
1. Vai in `File` > `Project Structure` > `Modules`.
2. Seleziona la scheda `Dependencies`.
3. Clicca sul tasto `+` > `JARs or Directories` e seleziona i file scaricati.
4. Assicurati che siano incluse nel deployment di Tomcat.

---

## 🐳 3. Configurazione Database (Docker)
Il database è isolato in un container Docker per evitare conflitti di installazione locale.

**Comando per avviare il database:**
```bash
docker run --name mysql-caffe \
  -e MYSQL_ROOT_PASSWORD=Poldo123@ \
  -e MYSQL_DATABASE=caffe_letterario \
  -e MYSQL_USER=ecampus \
  -e MYSQL_PASSWORD=Poldo123@ \
  -p 3306:3306 -d mysql:8.0