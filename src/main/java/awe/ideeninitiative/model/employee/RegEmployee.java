package awe.ideeninitiative.model.employee;

import awe.ideeninitiative.model.idea.AbstractIdea;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
public class RegEmployee {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private String email;

    private String password;

    private File profilePicture;
/*
    @OneToMany
    @JoinColumn(name="employee_id", insertable=false, updatable=false)
    private List<AbstractIdea> ideas;*/

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
