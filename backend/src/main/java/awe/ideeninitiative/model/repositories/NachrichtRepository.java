package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NachrichtRepository extends JpaRepository<Mitarbeiter, Long> {
}
