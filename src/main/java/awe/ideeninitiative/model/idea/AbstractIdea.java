package awe.ideeninitiative.model.idea;

import awe.ideeninitiative.model.employee.RegEmployee;

import javax.persistence.*;

/**
 * Beinhaltet die gemeinsamen Attribute und Funktionen der Produktideen und internen Ideen.
 */
@MappedSuperclass
public abstract class AbstractIdea {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String title;

    private String description;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private RegEmployee creator;
}
