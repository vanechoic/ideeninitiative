package awe.ideeninitiative.model.mitarbeiter;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.builder.FachspezialistHandlungsfeldBuilder;
import awe.ideeninitiative.model.builder.FachspezialistSparteBuilder;
import awe.ideeninitiative.model.builder.FachspezialistVertriebswegBuilder;
import awe.ideeninitiative.model.builder.FachspezialistZielgruppeBuilder;
import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.enums.Zielgruppe;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.restapi.security.BenutzerRollen;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Speichert den registrierten Mitarbeiter, Admin und Fachspezialisten. Die Rollen werden über die Felder istFachspezialist
 *  und istAdmin gesetzt.
 * @author
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "benutzername", name = "eindeutigerBenutzername"),
        @UniqueConstraint(columnNames = "email", name = "eindeutigeEmail")}
)
public class Mitarbeiter extends AbstractEntity {

    @NotNull
    @Pattern(regexp="[\\wäüöÄÜÖß]+")
    private String benutzername;

    @Pattern(regexp = "[^\\^°=*#~,;:(){}§$%<>|'`´\\/\\\\]*")
    private String vorname;

    @Pattern(regexp = "[^\\^°=*#~,;:(){}§$%<>|'`´\\/\\\\]*")
    private String nachname;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "[^\\s]*")
    private String passwort;

    @OneToOne(mappedBy = "mitarbeiter", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ProfilbildDatei profilbildDatei;

    private boolean istFachspezialist;

    private boolean istAdmin;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "erfasser", orphanRemoval = true)
    private List<Idee> erstellteIdeen;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "fachspezialist", orphanRemoval = true)
    private List<Idee> zugewieseneIdeen;

    /**
     * Speichert die Fachkenntnisse über Vertriebskanäle.
     */
    @OneToMany(mappedBy = "mitarbeiter", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<FachspezialistVertriebsweg> fachspezialistVertriebswege;

    /** Speichert die Fachkenntnisse über Sparten.
     */
    @OneToMany(mappedBy = "mitarbeiter", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<FachspezialistSparte> fachspezialistSparten;

    /** Speichert die Fachkenntnisse über Zielgruppen.
     */
    @OneToMany(mappedBy = "mitarbeiter", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<FachspezialistZielgruppe> fachspezialistZielgruppen;

    /** Speichert die Fachkenntnisse über Handlungsfelder.
     */
    @OneToMany(mappedBy = "mitarbeiter", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<FachspezialistHandlungsfeld> fachspezialistHandlungsfelder;

    public Mitarbeiter() {
        this.erstellteIdeen = new ArrayList<>();
        this.zugewieseneIdeen = new ArrayList<>();
        this.fachspezialistHandlungsfelder = new ArrayList<>();
        this.fachspezialistSparten = new ArrayList<>();
        this.fachspezialistVertriebswege = new ArrayList<>();
        this.fachspezialistZielgruppen  = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public boolean istFachspezialist() {
        return istFachspezialist;
    }

    public void setIstFachspezialist(boolean istFachspezialist) {
        this.istFachspezialist = istFachspezialist;
    }

    public boolean istAdmin() {
        return istAdmin;
    }

    public void setIstAdmin(boolean istAdmin) {
        this.istAdmin = istAdmin;
    }

    /**
     * Ermittelt alle zutreffenden Rollen für den gegebenen Mitarbeiter und gibt diese zurück.
     * @return Liste der zutreffenden Benutzerrollen
     */
    public String[] ermittleBenutzerrollenAlsString() {
        List<String> rollen = new ArrayList<String>();
        rollen.add(BenutzerRollen.MITARBEITER.toString());
        if(istFachspezialist()){
            rollen.add(BenutzerRollen.FACHSPEZIALIST.toString());
        }
        if(istAdmin()){
            rollen.add(BenutzerRollen.ADMIN.toString());
        }
        return rollen.toArray(new String[rollen.size()]);
    }

    public List<FachspezialistSparte> getFachspezialistSparten() {
        return fachspezialistSparten;
    }

    public void addFachspezialistSparte(Sparte... sparten){
        Arrays.stream(sparten).forEach(sp -> fachspezialistSparten.add(FachspezialistSparteBuilder.aFachspezialistSparte().withMitarbeiter(this).withSparte(sp).build()));
    }

    public void setFachspezialistSparten(List<FachspezialistSparte> fachspezialistSparten) {
        if(fachspezialistSparten != null && !fachspezialistSparten.isEmpty()){
            fachspezialistSparten.forEach(vw -> vw.setMitarbeiter(this));
            this.fachspezialistSparten.clear();
            this.fachspezialistSparten.addAll(fachspezialistSparten);
        }
    }

    public List<Sparte> getFachspezialistSpartenWerte(){
        return fachspezialistSparten.stream()
                .map(sp-> sp.getSparte())
                .collect(Collectors.toList());
    }

    public List<FachspezialistVertriebsweg> getFachspezialistVertriebswege() {
        return fachspezialistVertriebswege;
    }

    public List<Vertriebskanal> getFachspezialistVertriebswegeWerte(){
        return fachspezialistVertriebswege.stream()
                .map( vw-> vw.getVertriebsweg())
                .collect(Collectors.toList());
    }

    public void setFachspezialistVertriebswege(List<FachspezialistVertriebsweg> fachspezialistVertriebswege) {
        if(fachspezialistVertriebswege != null && !fachspezialistVertriebswege.isEmpty()){
            fachspezialistVertriebswege.forEach(vw -> vw.setMitarbeiter(this));
            this.fachspezialistVertriebswege.clear();
            this.fachspezialistVertriebswege.addAll(fachspezialistVertriebswege);
        }
    }

    public List<FachspezialistZielgruppe> getFachspezialistZielgruppen() {
        return fachspezialistZielgruppen;
    }

    public List<Zielgruppe> getFachspezialistZielgruppenWerte(){
        return fachspezialistZielgruppen.stream()
                .map(zg -> zg.getZielgruppe())
                .collect(Collectors.toList());
    }

    public void setFachspezialistZielgruppen(List<FachspezialistZielgruppe> fachspezialistZielgruppen) {
        if(fachspezialistZielgruppen != null && !fachspezialistZielgruppen.isEmpty()){
            fachspezialistZielgruppen.forEach(vw -> vw.setMitarbeiter(this));
            this.fachspezialistZielgruppen.clear();
            this.fachspezialistZielgruppen.addAll(fachspezialistZielgruppen);
        }
    }

    public void addFachspezialistZielgruppe(Zielgruppe... zielgruppe){
        Arrays.stream(zielgruppe).forEach(zg -> fachspezialistZielgruppen.add(FachspezialistZielgruppeBuilder.aFachspezialistZielgruppe().withMitarbeiter(this).withZielgruppe(zg).build()));
    }

    public List<FachspezialistHandlungsfeld> getFachspezialistHandlungsfelder() {
        return fachspezialistHandlungsfelder;
    }

    public List<Handlungsfeld> getFachspezialistHandlungsfelderWerte(){
        return fachspezialistHandlungsfelder.stream()
                .map(hf -> hf.getHandlungsfeld())
                .collect(Collectors.toList());
    }



    public void setFachspezialistHandlungsfelder(List<FachspezialistHandlungsfeld> fachspezialistHandlungsfelder) {
        if(fachspezialistHandlungsfelder != null && !fachspezialistHandlungsfelder.isEmpty()){
            fachspezialistHandlungsfelder.forEach(vw -> vw.setMitarbeiter(this));
            this.fachspezialistHandlungsfelder.clear();
            this.fachspezialistHandlungsfelder.addAll(fachspezialistHandlungsfelder);
        }
    }

    public void addFachspezialistHandlungsfeld(Handlungsfeld... handlungsfeld){
        Arrays.stream(handlungsfeld).forEach(hf -> fachspezialistHandlungsfelder.add(FachspezialistHandlungsfeldBuilder
                .aFachspezialistHandlungsfeld().withHandlungsfeld(hf).withMitarbeiter(this).build()));
    }

    public List<Idee> getZugewieseneIdeen() {
        return zugewieseneIdeen;
    }

    public int getAnzahlZugewieseneIdeen(){
        return zugewieseneIdeen == null ? 0 : zugewieseneIdeen.size();
    }

    public void setZugewieseneIdeen(List<Idee> zugewieseneIdeen) {
        if(zugewieseneIdeen != null && !zugewieseneIdeen.isEmpty()){
            zugewieseneIdeen.forEach(i -> i.setFachspezialist(this));
            this.zugewieseneIdeen.clear();
            this.zugewieseneIdeen.addAll(zugewieseneIdeen);
        }
    }

    public List<Idee> getErstellteIdeen() {
        return erstellteIdeen;
    }

    public void setErstellteIdeen(List<Idee> erstellteIdeen) {
        if(erstellteIdeen != null && !erstellteIdeen.isEmpty()){
            zugewieseneIdeen.forEach(i -> i.setErfasser(this));
            this.erstellteIdeen.clear();
            this.erstellteIdeen.addAll(erstellteIdeen);
        }
    }

    public void addFachspezialistVertriebsweg(Vertriebskanal... vertriebsweg) {
        Arrays.stream(vertriebsweg).forEach(vw -> fachspezialistVertriebswege.add(FachspezialistVertriebswegBuilder.aFachspezialistVertriebsweg()
                .withMitarbeiter(this).withVertriebsweg(vw).build()));
    }

    public ProfilbildDatei getProfilbildDatei() {
        return profilbildDatei;
    }

    public void setProfilbildDatei(ProfilbildDatei profilbildDatei) {
        this.profilbildDatei = profilbildDatei;
    }
}
