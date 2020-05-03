# ideeninitiative
###Voraussetzungen:
Gegebenenfalls müsst ihr vor Beginn folgendes installieren:
 * Git
 * Gradle
 * Java11 (z.B. AdoptOpenJdk)
 * IntelliJ
 * MySQL

###Projekt herunterladen:
git clone 

###Projekt in IntelliJ einrichten:
1. "Import Project" und ausgechecktes Projekt auswählen
2. Persönliche Konfiguration anlegen analog zur application-vanechoic.properties unter src/main/resources/config:
    * application-PROFILNAME.properties anlegen
    * PROFILNAME mit in die Auflistung in config/application.yml aufnehmen und vanechoic entfernen (tbd: Profil hübscher konfigurierbar machen)
    * Eigene Datenbankkonfiguration hinterlegen (url, password, username)
    * ggf. weitere Konfigurationen in der eigenen Datei ergänzen


###Anwendung starten:
Rechtsklick auf die Klasse "IdeeninitiativeApplication" und auf "Run" klicken. Dies startet die gesamte Anwendung.
In der Konsole sollte die Ausgabe mit "Started..." enden. Dann kann man sich sicher sein,
das alles funktioniert hat und die Anwendung läuft :)

Sollten Fehler dabei auftreten, wie:
* TABELLE existiert nicht: Hast du die Datenbank bei dir angelegt und gleich benannt? Versuche in der Konfig create durch update zu ersetzen oder andersherum
* Läuft MySQL gerade?
* Musst du ggf. einen anderen Port hinterlegen, weil der schon in Benutzung ist?

###Projektstruktur:
######src/main/java/awe/ideeninitiative/model
Beinhaltet die Klassen aus dem Klassendiagramm. Aus ihnen werden die Datenbanktabellen mittels JPA und Hibernate generiert.
Jede Klasse erbt von er AbstractEntity, damit man die ID, sowie die Timestamps nur einmal im Code deklariert hat.
Jede Klasse, die eine Tabelle sein soll, braucht die @Entity-Annotation.