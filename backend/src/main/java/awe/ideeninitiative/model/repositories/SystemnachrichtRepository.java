package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.Nachricht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemnachrichtRepository extends JpaRepository<Nachricht, Long> {

    public List<Nachricht> findAllByTitel(String titel);
}
