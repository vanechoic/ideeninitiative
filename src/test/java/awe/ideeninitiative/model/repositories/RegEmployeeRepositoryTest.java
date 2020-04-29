package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.employee.RegEmployee;
import awe.ideeninitiative.model.employee.RegEmployeeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegEmployeeRepositoryTest {

    @Autowired private RegEmployeeRepository regEmployeeRepository;

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private RegEmployee bob;

    @Test
    public void testCreateAndSaveNewEmployee(){
        given.einNeuerMitarbeiterNamensBob();
        when.derMitarbeiterBobGespeichertWird();
        then.verfuegtBobUeberEineID();
    }

    private class Given{
        public void einNeuerMitarbeiterNamensBob(){
            String username = "BobDerBaumeister";
            String vorname = "Bob";
            String nachname = "Baumeister";
            String email = "bob.der@baumeister.de";
            bob = RegEmployeeBuilder.aRegEmployee().withUsername(username)//
                .withFirstName(vorname)//
                .withLastName(nachname)//
                .withEmail(email).build();
        }
    }

    private class When{

        public void derMitarbeiterBobGespeichertWird() {
            regEmployeeRepository.save(bob);
        }
    }

    private class Then{

        public void verfuegtBobUeberEineID() {
            assertNotNull(bob.getId());
            assertTrue(bob.getId() > 0);
        }
    }
}