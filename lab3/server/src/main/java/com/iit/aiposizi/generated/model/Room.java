package com.iit.aiposizi.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.iit.aiposizi.generated.model.Office;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Room
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-05-17T23:29:22.869419+03:00[Europe/Minsk]")

public class Room   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("totalPlaces")
  private Integer totalPlaces;

  @JsonProperty("freePlaces")
  private Long freePlaces;

  @JsonProperty("number")
  private Integer number;

  @JsonProperty("office")
  private Office office;

  public Room id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Room totalPlaces(Integer totalPlaces) {
    this.totalPlaces = totalPlaces;
    return this;
  }

  /**
   * Get totalPlaces
   * @return totalPlaces
  */
  @ApiModelProperty(value = "")


  public Integer getTotalPlaces() {
    return totalPlaces;
  }

  public void setTotalPlaces(Integer totalPlaces) {
    this.totalPlaces = totalPlaces;
  }

  public Room freePlaces(Long freePlaces) {
    this.freePlaces = freePlaces;
    return this;
  }

  /**
   * Get freePlaces
   * @return freePlaces
  */
  @ApiModelProperty(value = "")


  public Long getFreePlaces() {
    return freePlaces;
  }

  public void setFreePlaces(Long freePlaces) {
    this.freePlaces = freePlaces;
  }

  public Room number(Integer number) {
    this.number = number;
    return this;
  }

  /**
   * Get number
   * @return number
  */
  @ApiModelProperty(value = "")


  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Room office(Office office) {
    this.office = office;
    return this;
  }

  /**
   * Get office
   * @return office
  */
  @ApiModelProperty(value = "")

  @Valid

  public Office getOffice() {
    return office;
  }

  public void setOffice(Office office) {
    this.office = office;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Room room = (Room) o;
    return Objects.equals(this.id, room.id) &&
        Objects.equals(this.totalPlaces, room.totalPlaces) &&
        Objects.equals(this.freePlaces, room.freePlaces) &&
        Objects.equals(this.number, room.number) &&
        Objects.equals(this.office, room.office);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, totalPlaces, freePlaces, number, office);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Room {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    totalPlaces: ").append(toIndentedString(totalPlaces)).append("\n");
    sb.append("    freePlaces: ").append(toIndentedString(freePlaces)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    office: ").append(toIndentedString(office)).append("\n");
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

