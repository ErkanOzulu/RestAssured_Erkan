package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Region {

//region_id
    //if your Jsonkey and variable name not matching, you can map it with jsonProperty
    @JsonProperty("region_id")
    private int regionId;
    private String region_name;
    private List<Link> links;


}
