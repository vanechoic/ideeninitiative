package awe.ideeninitiative.model.employee;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.Branches;
import awe.ideeninitiative.model.enums.DistributionChannels;
import awe.ideeninitiative.model.enums.Fields;
import awe.ideeninitiative.model.enums.TargetGroups;
import awe.ideeninitiative.model.idea.AbstractIdea;
import awe.ideeninitiative.model.idea.InternalIdea;
import awe.ideeninitiative.model.idea.ProductIdea;

import javax.persistence.*;
import java.io.File;
import java.util.List;
import java.util.Set;

@Entity
public class RegEmployee extends AbstractEntity {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private File profilePicture;

    private boolean isSpecialist;

    /**
     * Speichert die Fachkenntnisse über Vertriebskanäle.
     */
    @ElementCollection(targetClass = DistributionChannels.class)
    @CollectionTable(name = "specialist_dist_channel",
            joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "dist_channel")
    private Set<DistributionChannels> distributionChannels;

    /** Speichert die Fachkenntnisse über Sparten.
     */
    @ElementCollection(targetClass = Branches.class)
    @CollectionTable(name = "specialist_branch",
            joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "branch")
    private Set<Branches> branches;

    /** Speichert die Fachkenntnisse über Zielgruppen.
     */
    @ElementCollection(targetClass = TargetGroups.class)
    @CollectionTable(name = "specialist_target_group",
            joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "target_group")
    private Set<Fields> targetGroups;

    /** Speichert die Fachkenntnisse über Handlungsfelder.
     */
    @ElementCollection(targetClass = Fields.class)
    @CollectionTable(name = "specialist_field",
            joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "field")
    private Set<Fields> fields;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(File profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Fields> getFields() {
        return fields;
    }

    public void setFields(Set<Fields> fields) {
        this.fields = fields;
    }

    public Set<Fields> getTargetGroups() {
        return targetGroups;
    }

    public void setTargetGroups(Set<Fields> targetGroups) {
        this.targetGroups = targetGroups;
    }

    public Set<Branches> getBranches() {
        return branches;
    }

    public void setBranches(Set<Branches> branches) {
        this.branches = branches;
    }

    public Set<DistributionChannels> getDistributionChannels() {
        return distributionChannels;
    }

    public void setDistributionChannels(Set<DistributionChannels> distributionChannels) {
        this.distributionChannels = distributionChannels;
    }

    public boolean isSpecialist() {
        return isSpecialist;
    }

    public void setSpecialist(boolean specialist) {
        isSpecialist = specialist;
    }
}
