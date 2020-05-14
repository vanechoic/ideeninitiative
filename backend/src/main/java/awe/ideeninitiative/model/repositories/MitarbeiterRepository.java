package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Long> {
    public List<Mitarbeiter> findAllByBenutzername(String benutzername);

    public Optional<Mitarbeiter> findFirstByEmailOrBenutzername(String email, String benutzername);

    public Optional<Mitarbeiter> findFirstByBenutzername(String benutzername);
}

