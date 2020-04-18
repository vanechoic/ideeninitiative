package awe.ideeninitiative.model.idea;

import awe.ideeninitiative.model.enums.Branches;
import awe.ideeninitiative.model.enums.DistributionChannels;
import awe.ideeninitiative.model.enums.Fields;
import awe.ideeninitiative.model.enums.TargetGroups;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class ProductIdea extends AbstractIdea {
    private Branches branch;

    @ElementCollection(targetClass = DistributionChannels.class)
    @CollectionTable(name = "productidea_dist_channels",
            joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "dist_channels")
    private Set<DistributionChannels> distributionChannels;

    @ElementCollection(targetClass = TargetGroups.class)
    @CollectionTable(name = "productidea_target_group",
            joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "target_groups")
    @NotNull
    private Set<Fields> targetGroups;

    private boolean existsInOtherCompanies;

    public Branches getBranch() {
        return branch;
    }

    public void setBranch(Branches branch) {
        this.branch = branch;
    }

    public Set<DistributionChannels> getDistributionChannels() {
        return distributionChannels;
    }

    public void setDistributionChannels(Set<DistributionChannels> distributionChannels) {
        this.distributionChannels = distributionChannels;
    }

    public Set<Fields> getTargetGroups() {
        return targetGroups;
    }

    public void setTargetGroups(Set<Fields> targetGroups) {
        this.targetGroups = targetGroups;
    }

    public boolean isExistsInOtherCompanies() {
        return existsInOtherCompanies;
    }
}
