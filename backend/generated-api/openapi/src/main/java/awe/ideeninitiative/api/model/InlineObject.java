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
 * InlineObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-05-18T14:49:38.444989600+02:00[Europe/Berlin]")

public class InlineObject  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("benutzername")
  private String benutzername;

  @JsonProperty("passwort")
  private String passwort;

  public InlineObject benutzername(String benutzername) {
    this.benutzername = benutzername;
    return this;
  }

  /**
   * Get benutzername
   * @return benutzername
  */
  @ApiModelProperty(value = "")

@Size(min=1) 
  public String getBenutzername() {
    return benutzername;
  }

  public void setBenutzername(String benutzername) {
    this.benutzername = benutzername;
  }

  public InlineObject passwort(String passwort) {
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
    InlineObject inlineObject = (InlineObject) o;
    return Objects.equals(this.benutzername, inlineObject.benutzername) &&
        Objects.equals(this.passwort, inlineObject.passwort);
  }

  @Override
  public int hashCode() {
    return Objects.hash(benutzername, passwort);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineObject {\n");
    
    sb.append("    benutzername: ").append(toIndentedString(benutzername)).append("\n");
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

