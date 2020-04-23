package awe.ideeninitiative.model.idea;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.employee.RegEmployee;
import awe.ideeninitiative.model.enums.IdeaStatus;

import javax.persistence.*;
import java.io.File;
import java.util.List;

/**
 * Beinhaltet die gemeinsamen Attribute und Funktionen der Produktideen und internen Ideen.
 */
@MappedSuperclass
public abstract class AbstractIdea extends AbstractEntity {

    private String title;

    private String description;

    private String justification;

    private IdeaStatus status;

    @ElementCollection
    @CollectionTable(name = "idea_file", joinColumns = @JoinColumn(name = "idea_id"))
    @Column(name = "file", columnDefinition = "BLOB")
    private List<File> files;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id", referencedColumnName = "id")
    private RegEmployee creator;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="specialist_id", referencedColumnName = "id")
    private RegEmployee specialist;

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

    public IdeaStatus getStatus() {
        return status;
    }

    public void setStatus(IdeaStatus status) {
        this.status = status;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void setCreator(RegEmployee creator) {
        this.creator = creator;
    }

    public RegEmployee getSpecialist() {
        return specialist;
    }

    public void setSpecialist(RegEmployee specialist) {
        this.specialist = specialist;
    }
}
