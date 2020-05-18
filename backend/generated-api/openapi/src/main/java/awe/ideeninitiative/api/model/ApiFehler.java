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
 * ApiFehler
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-05-18T14:49:38.444989600+02:00[Europe/Berlin]")

public class ApiFehler  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("httpStatusCode")
  private Integer httpStatusCode;

  @JsonProperty("fehlertyp")
  private String fehlertyp;

  @JsonProperty("fehlertext")
  private String fehlertext;

  public ApiFehler httpStatusCode(Integer httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
    return this;
  }

  /**
   * Get httpStatusCode
   * @return httpStatusCode
  */
  @ApiModelProperty(value = "")


  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }

  public void setHttpStatusCode(Integer httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
  }

  public ApiFehler fehlertyp(String fehlertyp) {
    this.fehlertyp = fehlertyp;
    return this;
  }

  /**
   * Get fehlertyp
   * @return fehlertyp
  */
  @ApiModelProperty(value = "")


  public String getFehlertyp() {
    return fehlertyp;
  }

  public void setFehlertyp(String fehlertyp) {
    this.fehlertyp = fehlertyp;
  }

  public ApiFehler fehlertext(String fehlertext) {
    this.fehlertext = fehlertext;
    return this;
  }

  /**
   * Get fehlertext
   * @return fehlertext
  */
  @ApiModelProperty(value = "")


  public String getFehlertext() {
    return fehlertext;
  }

  public void setFehlertext(String fehlertext) {
    this.fehlertext = fehlertext;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiFehler apiFehler = (ApiFehler) o;
    return Objects.equals(this.httpStatusCode, apiFehler.httpStatusCode) &&
        Objects.equals(this.fehlertyp, apiFehler.fehlertyp) &&
        Objects.equals(this.fehlertext, apiFehler.fehlertext);
  }

  @Override
  public int hashCode() {
    return Objects.hash(httpStatusCode, fehlertyp, fehlertext);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiFehler {\n");
    
    sb.append("    httpStatusCode: ").append(toIndentedString(httpStatusCode)).append("\n");
    sb.append("    fehlertyp: ").append(toIndentedString(fehlertyp)).append("\n");
    sb.append("    fehlertext: ").append(toIndentedString(fehlertext)).append("\n");
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

