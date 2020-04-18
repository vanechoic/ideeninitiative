package awe.ideeninitiative.model.employee;

import awe.ideeninitiative.model.enums.Branches;
import awe.ideeninitiative.model.enums.DistributionChannels;
import awe.ideeninitiative.model.enums.Fields;
import awe.ideeninitiative.model.enums.TargetGroups;

import javax.persistence.*;
import java.util.Set;

/**
 * Bildet den Fachspezialisten mit seinen Spezialisierungen ab.
 */
@Entity
public class Specialist extends RegEmployee{

    @ElementCollection(targetClass = DistributionChannels.class)
    @CollectionTable(name = "specialist_dist_channels",
            joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "dist_channels")
    private Set<DistributionChannels> distributionChannels;

    @ElementCollection(targetClass = Branches.class)
    @CollectionTable(name = "specialist_branches",
            joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "branches")
    private Set<Branches> branches;

    @ElementCollection(targetClass = TargetGroups.class)
    @CollectionTable(name = "specialist_target_group",
            joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "target_groups")
    private Set<Fields> targetGroups;

    @ElementCollection(targetClass = Fields.class)
    @CollectionTable(name = "specialist_field",
            joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "fields")
    private Set<Fields> fields;

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
}
