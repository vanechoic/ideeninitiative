package awe.ideeninitiative.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private LocalDateTime erstellzeitpunkt;

    @UpdateTimestamp
    private LocalDateTime aktualisierungszeitpunkt;

    public Long getId(){
        return id;
    }

    public LocalDateTime getErstellzeitpunkt() {
        return erstellzeitpunkt;
    }

    public LocalDateTime getAktualisierungszeitpunkt() {
        return aktualisierungszeitpunkt;
    }
  
    public void setId(Long id) {
        this.id = id;
    }
}
