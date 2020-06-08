# Ideeninitiative
## Projekt einrichten
### Frontend: VSCode
Installiert sein müssen:
- npm / Version
- Editor
#### Vorgehen
1. **Hilfreiche Plugins installieren (optional):** Vue...
2. **Projekt importieren:** Öffne den Ordner frontend/
3. **Abhängigkeiten installieren:** "npm i" im Terminal
4. **Starten der Anwendung:** "npm run serve" im Terminal 

### Backend: IntelliJ
Installiert sein müssen:


#### Vorgehen
1. **Hilfreiche Plugins installieren:** Gradle
2. **Projekt importieren:** Importieren des Ordners backend
3. **Gradle funktionsfähig machen:** Übersicht der Gradle-Tasks öffnen (siehe Abbildung)

## Deployment
Das Deployment und verpacken der Anwendung in eine zusammenhängende .jar geschieht mit Gradle im Backend. 
1. **Gradle clean:** Entfernt alle zuletzt beim Build erstellten Dateien.
2. **Gradle build:** Kompiliert, testet und "baut" das Projekt neu in den backend/build-Ordner. Dabei wird unter backend/build/libs die .jar erstellt.
3. **Konfigurationsdatei anlegen:** Analog der obigen Beschreibung wird eine Konfigurationsdatei erstellt. Diese wird nun neben die .jar ins gleiche Verzeichnis gelegt.
4. **Jar starten**:  Im Terminal "java -jar ./[name der jar]" ausführen. Dies startet die Anwendung. Bei korrekter Konfiguration sollte der Aufruf über den definierten Port zur Startseite der Anwendung führen.
