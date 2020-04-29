package awe.ideeninitiative.model.employee;

import awe.ideeninitiative.model.enums.Branches;
import awe.ideeninitiative.model.enums.DistributionChannels;
import awe.ideeninitiative.model.enums.Fields;

import java.io.File;
import java.util.Set;

public final class RegEmployeeBuilder {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private File profilePicture;
    private Set<DistributionChannels> distributionChannels;
    private Set<Branches> branches;
    private Set<Fields> targetGroups;
    private Set<Fields> fields;

    private RegEmployeeBuilder() {
    }

    public static RegEmployeeBuilder aRegEmployee() {
        return new RegEmployeeBuilder();
    }

    public RegEmployeeBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public RegEmployeeBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public RegEmployeeBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public RegEmployeeBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public RegEmployeeBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public RegEmployeeBuilder withProfilePicture(File profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public RegEmployeeBuilder withDistributionChannels(Set<DistributionChannels> distributionChannels) {
        this.distributionChannels = distributionChannels;
        return this;
    }

    public RegEmployeeBuilder withBranches(Set<Branches> branches) {
        this.branches = branches;
        return this;
    }

    public RegEmployeeBuilder withTargetGroups(Set<Fields> targetGroups) {
        this.targetGroups = targetGroups;
        return this;
    }

    public RegEmployeeBuilder withFields(Set<Fields> fields) {
        this.fields = fields;
        return this;
    }

    public RegEmployeeBuilder but() {
        return aRegEmployee().withUsername(username).withFirstName(firstName).withLastName(lastName).withEmail(email).withPassword(password).withProfilePicture(profilePicture).withDistributionChannels(distributionChannels).withBranches(branches).withTargetGroups(targetGroups).withFields(fields);
    }

    public RegEmployee build() {
        RegEmployee regEmployee = new RegEmployee();
        regEmployee.setUsername(username);
        regEmployee.setFirstName(firstName);
        regEmployee.setLastName(lastName);
        regEmployee.setEmail(email);
        regEmployee.setPassword(password);
        regEmployee.setProfilePicture(profilePicture);
        regEmployee.setDistributionChannels(distributionChannels);
        regEmployee.setBranches(branches);
        regEmployee.setTargetGroups(targetGroups);
        regEmployee.setFields(fields);
        return regEmployee;
    }
}
