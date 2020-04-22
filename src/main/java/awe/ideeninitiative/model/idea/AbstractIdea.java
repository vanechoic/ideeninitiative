package awe.ideeninitiative.model.idea;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.employee.RegEmployee;

import javax.persistence.*;

/**
 * Beinhaltet die gemeinsamen Attribute und Funktionen der Produktideen und internen Ideen.
 */
@MappedSuperclass
public abstract class AbstractIdea extends AbstractEntity {

    private String title;

    private String description;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id", referencedColumnName = "id")
    private RegEmployee creator;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RegEmployee getCreator() {
        return creator;
    }
}
