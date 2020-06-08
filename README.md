# Ideeninitiative
## Projekt einrichten
### Frontend: VSCode
Installiert sein müssen:
- npm (die aktuelle Version gibt es [hier](https://nodejs.org/en/download/) zum Download)

#### Vorgehen:
1. **Hilfreiche Extensions installieren (optional):**
Für bessere Lesbarkeit von Vue.js empfehlen wir [Vetur](https://marketplace.visualstudio.com/items?itemName=octref.vetur).
Für npm-Support [diese]([https://marketplace.visualstudio.com/items?itemName=eg2.vscode-npm-script](https://marketplace.visualstudio.com/items?itemName=eg2.vscode-npm-script)) Extension.
2. **Projekt importieren:** Klicken Sie oben in der Leiste auf "File >> Open Folder..." und wählen Sie den frontend-Ordner aus. 
3. **Abhängigkeiten installieren:** Damit die Anwendung alle benötigten Dependencies / Plugins zum Starten hat, muss im Terminal (obere Leiste: "Terminal >> New Terminal) einmal **"npm i"** im frontend-Ordner ausgeführt werden.
4. **Starten der Anwendung:** Dafür **"npm run serve"** im Terminal eingeben. Bei erfolgreichem Start sollte eine Ausgabe erfolgen, auf welchem Port die Anwendung gestartet wurde. Standardmäßig ist dies 8080. Nun kann die Anwendung im Browser über den gezeigten Port angesprochen werden.

### Backend: IntelliJ
#### Vorgehen:
 1. **Ggf. Plugins installieren:** Gradle, JUnit
 2. **Projekt importieren:** Klicken Sie oben in der Leiste auf "File >> Open..." und wählen Sie den backend-Ordner aus. 
 3. **Übersicht der Gradle-Tasks öffnen:** Klicken Sie oben in der Leiste auf "View >> Tool Windows >> Gradle". Es sollte ein Teilfenster "Gradle" erscheinen. Ggf. müssen Sie es erst ausklappen (prüfen Sie den Rand links oder rechts).
 4. **Den magischen Gradle-Knopf drücken:** Dieser Punkt ist SEHR WICHTIG, damit das Projekt vernünftig als Gradle-Projekt erkannt und mit all seinen Dependencies importiert wird. Innerhalb des Gradle-Fensters gibt es eine Menüleiste. Ganz links ist ein Refresh-Button. Dieser muss am besten immer einmal gedrückt werden, wenn das Backend-Projekt neu in  IntelliJ geöffnet wird. 
 5. **Datenbankkonfiguration anlegen:** Da jeder Entwickler lokal seine eigene MySQL-Datenbank installiert hat, müssen eigene Zugangsdaten  hinterlegt werden. Dafür muss im Ordner backend/src/main/resources/config eine neue Datei mit dem Namen "application-[Ihr Name].properties" angelegt werden. Der Inhalt muss einmal den Datenbanknamen, Benutzernamen und das Passwort enthalten. Diese Dateien werden  am besten nicht mit in  die  Versionskontrolle genommen und dienen hier nur als Referenzen. Beispiel für eine Konfigurationsdatei:

    spring.jpa.hibernate.ddl-auto=[ initial einmal "**create**", damit die Tabellen erstellt werden und danach "**update**"]  
    spring.datasource.url=jdbc:mysql://localhost:3306/**[Ihre Datenbankbezeichnung]**?serverTimezone=CET&useLegacyDatetimeCode=false  
    spring.datasource.username=**[Ihr DB-Benutzername]**  
    spring.datasource.password=**[Ihr DB-Passwort]**

 6. **Neues Profil aktivieren:** Damit die neu angelegte Datenbankkonfiguration gegriffen wird, muss sichergestellt werden, dass Ihr Profil gegriffen wird. Dafür ersetzen Sie in der **backend/config/application.yml** in die Auflistung der Profile (spring:profiles:active) den ersten Eintrag "Nils" mit Ihrem Profilnamen. Dies stellt die Verbindung her zwischen der angelegten Datei und der beim Anwendungsstart genutzten Konfiguration.
 7. **Das Projekt bauen:** Damit einmal alles kompiliert und getestet wird, einmal den **"clean"- und danach den "build"-Task** ausführen. Diese lassen sich entweder über das Gradle-Fenster unter "backend >> Tasks >> build" mit einem Doppelklick oder über das Terminal im backend-Ordner mit dem Befehl "./gradlew clean build" starten.
 8. **Anwendung starten:** Um die Anwendung zu starten, machen Sie in der Projektübersicht einen Rechtsklick auf die Klasse **"IdeeninitiativeApplication" unter backend/src/main/java/awe/ideeninitiative** und wählen **"Run"**. In der Konsole sollte das Spring-Logo erscheinen, ggf. gefolgt von  einigen weiteren Befehlen. Nach einigen Sekunden sollten keine weiteren Befehle folgen. Dann können Sie  zum Testen z.B. Endpunkte über localhost:6969 aufrufen oder mit parallel laufendem Frontend direkt über das Frontend mit dem Backend interagieren.

## Deployment
Das Deployment und verpacken der Anwendung in eine zusammenhängende .jar geschieht mit Gradle im Backend. Installiert sein müssen:
- npm (die aktuelle Version gibt es [hier](https://nodejs.org/en/download/) zum Download)
### Vorgehen:
Neue .jar generieren
1. **Gradle clean:** Entfernt alle zuletzt beim Build erstellten Dateien.
2. **Gradle build:** Kompiliert, testet und "baut" das Projekt neu in den backend/build-Ordner. Dabei wird unter backend/build/libs die .jar erstellt.

hier geht's los, wenn die .jar bereits da ist:

4. **Konfigurationsdatei anlegen:** Ähnlich der oben beschriebenen Datenbankkonfiguration, wird hier ebenfalls eine Konfigurationsdatei benötigt. Sie trägt den Namen **"application.properties" und wird neben die .jar ins gleiche Verzeichnis gelegt**.
Pflichtfelder sind der Datenbankzugang, das JWT-Secret und der Port 6969. (Wird ein anderer Port benötigt, muss dieser im Frontend in der config/.env.production und im Backend in der application.yml angepasst und neu deployed werden, damit Frontend und Backend auf dem gleichen Stand sind.)
*Ein Beispiel für eine funktionierende Konfigdatei finden Sie auf der CD unter /deployment/application.properties. Beachten Sie bitte, dass Sie dort Ihre eigene DB-Konfigurationswerte hinterlegen müssen, damit die Anwendung startet.*
6. **Anwendung starten**:  Im Verzeichnis der .jar und Konfiguration (hier: /deployment) öffnen Sie das Terminal und führen **"java -jar ./backend-0.0.1-SNAPSHOT.jar"** aus. Dies startet die Anwendung. Bei korrekter Konfiguration sollte der Aufruf der Anwendung im Browser über den definierten Port 6969 zur Startseite der Anwendung führen.
*Hinweis: Durch die Bündelung von Frontend- und Backend in eine .jar, sind alle Endpunkte über Port 6969 ansprechbar. Nur während der Entwicklung im lokalen Modus, weichen die Ports ab (Frontend: 8080, Backend: 6969).*
