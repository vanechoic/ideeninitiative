package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeeRepository extends JpaRepository<Idee, Long> {
    public List<Idee> findAllByErfasserBenutzername(String benutzername);

    public Optional<Idee> findFirstByErfasserAndTitel(Mitarbeiter erfasser, String titel);
}
