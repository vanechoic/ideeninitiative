package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.idee.InterneIdeeHandlungsfeld;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterneIdeeHandlungsfeldRepository extends JpaRepository<InterneIdeeHandlungsfeld, Long> {
    public Optional<InterneIdeeHandlungsfeld>  findAllByIdee(Idee idee);
}
