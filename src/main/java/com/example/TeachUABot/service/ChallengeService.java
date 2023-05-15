package com.example.TeachUABot.service;

import com.example.TeachUABot.model.ChallengeModel;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

@Component
public class ChallengeService {
    public static String getChallenge(Long message, ChallengeModel model) throws IOException {
        URL url = new URL("http://localhost:8080/dev/api/challenge/" + message);
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()){
            result +=scanner.nextLine();
        }
        JSONObject object = new JSONObject(result);

        model.setId(object.getLong("id"));
        model.setName(object.getString("name"));
        model.setTitle(object.getString("title"));
        model.setDescription(object.getString("description"));
        model.setSortNumber(object.getLong("sortNumber"));
        model.setIsActive(object.getBoolean("isActive"));


        return "Information about challenge:\n" + "Name: " + model.getName() + "\n" +
                "Title: " + model.getTitle() + "\n" +
                "Description: " + model.getDescription() + "\n" +
                "SortNumber: " + model.getSortNumber() + "\n" +
                "IsActive: " + model.getIsActive() + "\n";
    }
}
