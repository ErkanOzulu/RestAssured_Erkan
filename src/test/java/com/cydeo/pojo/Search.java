package com.cydeo.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Search {

    private List<Spartan> content;
    private int totalElement;


}
