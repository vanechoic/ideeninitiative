package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.enums.Zielgruppe;
import awe.ideeninitiative.model.mitarbeiter.FachspezialistVertriebsweg;
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

    public List<Mitarbeiter>  findAllByEmail(String email);

    public List<Mitarbeiter> findAllByIstFachspezialistTrueAndFachspezialistHandlungsfelderHandlungsfeldLike(Handlungsfeld handlungsfeld);
    public List<Mitarbeiter> findDistinctByIstFachspezialistTrueAndFachspezialistVertriebswegeVertriebswegInOrFachspezialistSpartenSparteInOrFachspezialistZielgruppenZielgruppeIn(List<Vertriebskanal> vertriebswege, List<Sparte> sparte, List<Zielgruppe> zielgruppen);
}

