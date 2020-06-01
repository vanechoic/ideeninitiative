package awe.ideeninitiative.restapi.mapper;

import awe.ideeninitiative.api.model.BenutzerDTO;
import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.exception.InterneIdeeOhneHandlungsfeldException;
import awe.ideeninitiative.model.builder.*;
import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.enums.Zielgruppe;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.idee.InterneIdeeHandlungsfeld;
import awe.ideeninitiative.model.idee.ProduktideeVertriebsweg;
import awe.ideeninitiative.model.idee.ProduktideeZielgruppe;
import awe.ideeninitiative.model.mitarbeiter.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BenutzerMapper {
    public List<BenutzerDTO> mappeMitarbeiterZuBenutzerDTO(List<Mitarbeiter> mitarbeiter) {
        if(mitarbeiter != null && !mitarbeiter.isEmpty()){
            List<BenutzerDTO> benutzerDTOs = new ArrayList<>();
            mitarbeiter.stream().forEach(mit -> benutzerDTOs.add(mappeMitarbeiterZuBenutzerDTO(mit)));
            return benutzerDTOs;
        }
        return null;
    }

    public BenutzerDTO mappeMitarbeiterZuBenutzerDTO(Mitarbeiter mitarbeiter) {
        if(mitarbeiter == null){
            return null;
        }
        BenutzerDTO benutzer = BenutzerDTOBuilder.aBenutzerDTO().withBenutzername(mitarbeiter.getBenutzername())//
                .withEmail(mitarbeiter.getEmail()).withVorname(mitarbeiter.getVorname())
                .withIstFachspezialist(mitarbeiter.istFachspezialist())
                .withIstAdmin(mitarbeiter.istAdmin())
                .withNachname(mitarbeiter.getNachname())
                .build();
        //Passwort wird bewusst nicht mit ans Frontend gegeben
        benutzer.setFachspezialistVertriebswege(mappeFachspezialistVertriebswegeZuStringListe(mitarbeiter.getFachspezialistVertriebswege()));
        benutzer.setFachspezialistHandlungsfelder(mappeFachspezialistHandlungsfelderZuStringListe(mitarbeiter.getFachspezialistHandlungsfelder()));
        benutzer.setFachspezialistSparten(mappeFachspezialistSpartenZuStringListe(mitarbeiter.getFachspezialistSparten()));
        benutzer.setFachspezialistZielgruppen(mappeFachspezialistZielgruppenZuStringListe(mitarbeiter.getFachspezialistZielgruppen()));
        return benutzer;
    }

    public Mitarbeiter mappeBenutzerDTOZuMitarbeiter(BenutzerDTO benutzerDTO){
        //Erstellzeitpunkt nicht von Relevanz
        Mitarbeiter mitarbeiter = MitarbeiterBuilder.aMitarbeiter()
                .withBenutzername(benutzerDTO.getBenutzername()).withEmail(benutzerDTO.getEmail())
                .withVorname(benutzerDTO.getVorname()).withNachname(benutzerDTO.getNachname())
                .withPasswort(benutzerDTO.getPasswort())
                .withIstFachspezialist(benutzerDTO.getIstFachspezialist() == null ? false : benutzerDTO.getIstFachspezialist())
                .withIstAdmin(benutzerDTO.getIstAdmin() == null ? false : benutzerDTO.getIstAdmin())
                //.TODO: withProfilbild(benutzerDTO.getProfilbild())
                .build();
        mappeBenutzerDTOSpartenZuMitarbeiterSparten(benutzerDTO, mitarbeiter);
        mappeBenutzerDTOHandlungsfeldZuMitarbeiterHandlungsfelder(benutzerDTO, mitarbeiter);
        mappeBenutzerDTOVertriebswegeZuMitarbeiterVertriebswege(benutzerDTO, mitarbeiter);
        mappeBenutzerDTOZielgruppenZuMitarbeiterZielgruppen(benutzerDTO, mitarbeiter);
        return mitarbeiter;
    }

    private void mappeBenutzerDTOZielgruppenZuMitarbeiterZielgruppen(BenutzerDTO benutzerDTO, Mitarbeiter mitarbeiter) {
        if(benutzerDTO.getFachspezialistZielgruppen() != null && !benutzerDTO.getFachspezialistZielgruppen().isEmpty()){
            mitarbeiter.setFachspezialistZielgruppen(mappeStringListeZuFachspezialistZielgruppen(mitarbeiter, benutzerDTO.getFachspezialistZielgruppen()));
        }
    }

    private List<FachspezialistZielgruppe> mappeStringListeZuFachspezialistZielgruppen(Mitarbeiter mitarbeiter, List<String> stringListe) {
        if(mitarbeiter != null && stringListe != null && !stringListe.isEmpty()){
            List<FachspezialistZielgruppe> zielgruppen = new ArrayList<>();
            stringListe.stream().forEach(zielgruppeString -> zielgruppen.add(FachspezialistZielgruppeBuilder.aFachspezialistZielgruppe().withMitarbeiter(mitarbeiter).withZielgruppe(Zielgruppe.valueOf(zielgruppeString.toUpperCase())).build()));
            return zielgruppen;
        }
        return null;
    }

    private void mappeBenutzerDTOVertriebswegeZuMitarbeiterVertriebswege(BenutzerDTO benutzerDTO, Mitarbeiter mitarbeiter) {
        if(benutzerDTO.getFachspezialistVertriebswege() != null && !benutzerDTO.getFachspezialistVertriebswege().isEmpty()){
            mitarbeiter.setFachspezialistVertriebswege(mappeStringListeZuFachspezialistVertriebswege(mitarbeiter, benutzerDTO.getFachspezialistVertriebswege()));
        }
    }

    private List<FachspezialistVertriebsweg> mappeStringListeZuFachspezialistVertriebswege(Mitarbeiter mitarbeiter, List<String> stringListe) {
        if(mitarbeiter != null && stringListe != null && !stringListe.isEmpty()){
            List<FachspezialistVertriebsweg> vertriebswege = new ArrayList<>();
            stringListe.stream().forEach(vertriebswegString -> vertriebswege.add(FachspezialistVertriebswegBuilder.aFachspezialistVertriebsweg().withMitarbeiter(mitarbeiter).withVertriebsweg(Vertriebskanal.valueOf(vertriebswegString.toUpperCase())).build()));
            return vertriebswege;
        }
        return null;
    }

    private void mappeBenutzerDTOSpartenZuMitarbeiterSparten(BenutzerDTO benutzerDTO, Mitarbeiter mitarbeiter) {
        if(benutzerDTO.getFachspezialistSparten() != null && !benutzerDTO.getFachspezialistSparten().isEmpty()){
            mitarbeiter.setFachspezialistSparten(mappeStringListeZuFachspezialistSparten(mitarbeiter, benutzerDTO.getFachspezialistSparten()));
        }
    }

    private List<FachspezialistSparte> mappeStringListeZuFachspezialistSparten(Mitarbeiter mitarbeiter, List<String> stringListe) {
        if(mitarbeiter != null && stringListe != null && !stringListe.isEmpty()){
            List<FachspezialistSparte> sparten = new ArrayList<>();
            stringListe.stream().forEach(sparteString -> sparten.add(FachspezialistSparteBuilder.aFachspezialistSparte().withMitarbeiter(mitarbeiter).withSparte(Sparte.valueOf(sparteString.toUpperCase())).build()));
            return sparten;
        }
        return null;
    }

    private void mappeBenutzerDTOHandlungsfeldZuMitarbeiterHandlungsfelder(BenutzerDTO benutzerDTO, Mitarbeiter mitarbeiter) {
        if(benutzerDTO.getFachspezialistHandlungsfelder() != null && !benutzerDTO.getFachspezialistHandlungsfelder().isEmpty()){
            mitarbeiter.setFachspezialistHandlungsfelder(mappeStringListeZuFachspezialistHandlungsfelder(mitarbeiter, benutzerDTO.getFachspezialistHandlungsfelder()));
        }
    }

    private List<FachspezialistHandlungsfeld> mappeStringListeZuFachspezialistHandlungsfelder(Mitarbeiter mitarbeiter, List<String> stringListe) {
        if(mitarbeiter != null && stringListe != null && !stringListe.isEmpty()){
            List<FachspezialistHandlungsfeld> handlungsfelder = new ArrayList<>();
            stringListe.stream().forEach(handlungsfeldString -> handlungsfelder.add(FachspezialistHandlungsfeldBuilder.aFachspezialistHandlungsfeld()
                    .withMitarbeiter(mitarbeiter).withHandlungsfeld(Handlungsfeld.valueOf(handlungsfeldString.toUpperCase())).build()));
            return handlungsfelder;
        }
        return null;
    }

    private List<String> mappeFachspezialistSpartenZuStringListe(List<FachspezialistSparte> sparten) {
        if(sparten == null || sparten.isEmpty()){
            return null;
        }
        return sparten.stream() //TODO: Generics?
                .map(vw -> vw.getSparte().toString())
                .collect(Collectors.toList());
    }

    private List<String> mappeFachspezialistVertriebswegeZuStringListe(List<FachspezialistVertriebsweg> vertriebswege) {
        if(vertriebswege == null || vertriebswege.isEmpty()){
            return null;
        }
        return vertriebswege.stream()
                .map(vw -> vw.getVertriebsweg().toString())
                .collect(Collectors.toList());
    }

    private List<String> mappeFachspezialistHandlungsfelderZuStringListe(List<FachspezialistHandlungsfeld> handlungsfelder) {
        if(handlungsfelder == null || handlungsfelder.isEmpty()){
            return null;
        }
        return handlungsfelder.stream()
                .map(vw -> vw.getHandlungsfeld().toString())
                .collect(Collectors.toList());
    }

    private List<String> mappeFachspezialistZielgruppenZuStringListe(List<FachspezialistZielgruppe> zielgruppen) {
        if(zielgruppen == null || zielgruppen.isEmpty()){
            return null;
        }
        return zielgruppen.stream()
                .map(zg -> zg.getZielgruppe().toString())
                .collect(Collectors.toList());
    }

}
