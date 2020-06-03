package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.Nachricht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemnachrichtRepository extends JpaRepository<Nachricht, Long> {

    public List<Nachricht> findAllByTitel(String titel);

    public Optional<Nachricht> findFirstByTitel(String titel);
}
