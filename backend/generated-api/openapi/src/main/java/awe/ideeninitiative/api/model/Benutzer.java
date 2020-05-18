package awe.ideeninitiative.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

/**
 * Benutzer
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-05-18T14:49:38.444989600+02:00[Europe/Berlin]")

public class Benutzer  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("benutzername")
  private String benutzername;

  @JsonProperty("vorname")
  private String vorname;

  @JsonProperty("nachname")
  private String nachname;

  @JsonProperty("email")
  private String email;

  @JsonProperty("passwort")
  private String passwort;

  public Benutzer benutzername(String benutzername) {
    this.benutzername = benutzername;
    return this;
  }

  /**
   * Get benutzername
   * @return benutzername
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(min=1) 
  public String getBenutzername() {
    return benutzername;
  }

  public void setBenutzername(String benutzername) {
    this.benutzername = benutzername;
  }

  public Benutzer vorname(String vorname) {
    this.vorname = vorname;
    return this;
  }

  /**
   * Get vorname
   * @return vorname
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(min=1) 
  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public Benutzer nachname(String nachname) {
    this.nachname = nachname;
    return this;
  }

  /**
   * Get nachname
   * @return nachname
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(min=1) 
  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public Benutzer email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(min=6) 
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Benutzer passwort(String passwort) {
    this.passwort = passwort;
    return this;
  }

  /**
   * Get passwort
   * @return passwort
  */
  @ApiModelProperty(value = "")

@Size(min=8) 
  public String getPasswort() {
    return passwort;
  }

  public void setPasswort(String passwort) {
    this.passwort = passwort;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Benutzer benutzer = (Benutzer) o;
    return Objects.equals(this.benutzername, benutzer.benutzername) &&
        Objects.equals(this.vorname, benutzer.vorname) &&
        Objects.equals(this.nachname, benutzer.nachname) &&
        Objects.equals(this.email, benutzer.email) &&
        Objects.equals(this.passwort, benutzer.passwort);
  }

  @Override
  public int hashCode() {
    return Objects.hash(benutzername, vorname, nachname, email, passwort);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Benutzer {\n");
    
    sb.append("    benutzername: ").append(toIndentedString(benutzername)).append("\n");
    sb.append("    vorname: ").append(toIndentedString(vorname)).append("\n");
    sb.append("    nachname: ").append(toIndentedString(nachname)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    passwort: ").append(toIndentedString(passwort)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

