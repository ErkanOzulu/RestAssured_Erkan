package com.cydeo.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)//this is from jackson
/*
@JsonIgnoreProperties(ignoreUnknown = true) annotation can be used in the POJO class to indicate to the JSON library that any unknown properties in the JSON data should be ignored during deserialization. This means that even if the JSON data contains properties that are not defined in the POJO class, the deserialization process will not fail and the unknown properties will simply be ignored.
 */

public class Regions {
private List<Region>items;
private int count;

}
