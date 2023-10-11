package com.cydeo.utilities;

import com.cydeo.pojo.Spartan;
import com.github.javafaker.Faker;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {

    public static Map<String, Object> spartan() {

        Faker faker = new Faker();

        String name = faker.name().firstName();
        String gender = faker.demographic().sex();
        String phone = faker.number().digits(10);

        Map<String, Object> spartan = new LinkedHashMap<>();

        spartan.put("name", name);
        spartan.put("gender", gender);
        spartan.put("phone", Long.parseLong(phone));

        return spartan;

    }

}
