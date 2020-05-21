package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeeRepository extends JpaRepository<Idee, Long> {
    public List<Idee> findAllByErfasserBenutzername(String benutzername);
}
