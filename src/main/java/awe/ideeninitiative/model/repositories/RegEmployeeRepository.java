package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.employee.RegEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegEmployeeRepository extends JpaRepository<RegEmployee, Long> {
    public List<RegEmployee> findAllByUsername(String username);
}
