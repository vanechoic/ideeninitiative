package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IdeeRepository extends JpaRepository<Idee, Long> {
    public List<Idee> findAllByErfasserBenutzername(String benutzername);
    public List<Idee> findAllByFachspezialistBenutzernameAndBearbeitungsstatusLike(String benutzername, Ideenstatus bearbeitungsstatus);
    public List<Idee> findAllByBearbeitungsstatusNotLike(Ideenstatus ideenstatus);
    public List<Idee> findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(String titel, LocalDateTime erstellzeitpunkt, String erfasserBenutzername);
    public Optional<Idee> findFirstByTitelAndErfasserBenutzername(String titel, String erfasser);
}
