package com.iit.aiposizi.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PlaceEmployeeRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-05-17T23:29:22.869419+03:00[Europe/Minsk]")

public class PlaceEmployeeRequest   {
  @JsonProperty("companyName")
  private String companyName;

  @JsonProperty("placeNumber")
  private Integer placeNumber;

  @JsonProperty("roomNumber")
  private Integer roomNumber;

  public PlaceEmployeeRequest companyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

  /**
   * Get companyName
   * @return companyName
  */
  @ApiModelProperty(value = "")


  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public PlaceEmployeeRequest placeNumber(Integer placeNumber) {
    this.placeNumber = placeNumber;
    return this;
  }

  /**
   * Get placeNumber
   * @return placeNumber
  */
  @ApiModelProperty(value = "")


  public Integer getPlaceNumber() {
    return placeNumber;
  }

  public void setPlaceNumber(Integer placeNumber) {
    this.placeNumber = placeNumber;
  }

  public PlaceEmployeeRequest roomNumber(Integer roomNumber) {
    this.roomNumber = roomNumber;
    return this;
  }

  /**
   * Get roomNumber
   * @return roomNumber
  */
  @ApiModelProperty(value = "")


  public Integer getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(Integer roomNumber) {
    this.roomNumber = roomNumber;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlaceEmployeeRequest placeEmployeeRequest = (PlaceEmployeeRequest) o;
    return Objects.equals(this.companyName, placeEmployeeRequest.companyName) &&
        Objects.equals(this.placeNumber, placeEmployeeRequest.placeNumber) &&
        Objects.equals(this.roomNumber, placeEmployeeRequest.roomNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(companyName, placeNumber, roomNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlaceEmployeeRequest {\n");
    
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    placeNumber: ").append(toIndentedString(placeNumber)).append("\n");
    sb.append("    roomNumber: ").append(toIndentedString(roomNumber)).append("\n");
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

