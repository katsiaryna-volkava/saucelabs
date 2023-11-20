package org.portfolio.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.portfolio.enums.UserData;
import org.portfolio.enums.UserRole;
import org.portfolio.util.ResourcesUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCredentialsFetchService {
    private static final Type REVIEW_TYPE = new TypeToken<List<UserData>>() {
    }.getType();

    private static final String USER_CREDENTIALS_FILE_NAME = "login_data.json";
    private final Gson gson = new Gson();


    public UserData fetchLoginData(UserRole userRole) throws FileNotFoundException, URISyntaxException {
        File file = ResourcesUtils.fetchResource(USER_CREDENTIALS_FILE_NAME);

        try (JsonReader reader = new JsonReader(new FileReader(file))) {
            List<UserData> data = gson.fromJson(reader, REVIEW_TYPE);
            return data.stream()
                    .filter(user -> user.getLogin().equals(userRole.name().toLowerCase()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("User not found for role: " + userRole));

        } catch (IOException e) {
            throw new FileNotFoundException("Error reading user credentials file: " + e.getMessage());
        }
    }

    public String fetchAuthToken(Path credentialsFilePath) throws FileNotFoundException {
        try (FileReader fileReader = new FileReader(credentialsFilePath.toFile())) {
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

            String inputText = jsonObject.toString();
            var regex = ".*token\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"(.*)\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"ssid";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(inputText);

            String token = "";
            while (matcher.find()) {
                token = matcher.group(1);
            }

            return token;
        } catch (Exception e) {
            throw new FileNotFoundException("Unable to read or parse JSON file: " + e.getMessage());
        }
    }
}

