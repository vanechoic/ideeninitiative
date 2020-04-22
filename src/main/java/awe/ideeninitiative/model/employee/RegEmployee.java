package awe.ideeninitiative.model.employee;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.idea.AbstractIdea;
import awe.ideeninitiative.model.idea.InternalIdea;
import awe.ideeninitiative.model.idea.ProductIdea;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RegEmployee extends AbstractEntity {

    private String name;

    private String email;

    private String password;

    private File profilePicture;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
