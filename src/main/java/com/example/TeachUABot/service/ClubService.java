package com.example.TeachUABot.service;

import com.example.TeachUABot.model.ClubModel;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

@Component
public class ClubService {
    public static String getClub(Long message, ClubModel model) throws IOException {
        URL url = new URL("http://localhost:8080/dev/api/club/" + message);
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()){
            result +=scanner.nextLine();
        }
        JSONObject object = new JSONObject(result);

        model.setId(object.getLong("id"));
        model.setName(object.getString("name"));
        model.setAgeFrom(object.getInt("ageFrom"));
        model.setAgeTo(object.getInt("ageTo"));
        model.setDescription(object.getString("description"));

        return "Information about Club:\n" +
                "Name: " + model.getName() + "\n" +
                "Age from: " + model.getAgeFrom() + "\n" +
                "Age To: " + model.getAgeTo() + "\n" +
                "Description: " + model.getDescription() + "\n";
    }
}
