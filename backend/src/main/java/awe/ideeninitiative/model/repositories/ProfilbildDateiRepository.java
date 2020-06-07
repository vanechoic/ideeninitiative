package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.mitarbeiter.ProfilbildDatei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilbildDateiRepository  extends JpaRepository<ProfilbildDatei, Long> {

    public Optional<ProfilbildDatei> findByMitarbeiterBenutzername(String benutzername);
}
