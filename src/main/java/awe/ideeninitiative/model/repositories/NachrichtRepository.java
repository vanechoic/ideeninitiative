package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NachrichtRepository extends JpaRepository<Mitarbeiter, Long> {
}
