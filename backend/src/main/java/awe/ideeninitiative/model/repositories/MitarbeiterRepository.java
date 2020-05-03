package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Long> {
    public List<Mitarbeiter> findAllByBenutzername(String benutzername);
}
