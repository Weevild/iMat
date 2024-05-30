package imat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class UserDataHandler {

    private static final String USER_FILE = "user_data.json";
    private static List<User> userList = new ArrayList<>();

    public static List<User> getUserList() {
        return userList;
    }

    public static void saveUserList() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter(); // Enable pretty printing
        try {
            writer.writeValue(new File(USER_FILE), userList); // Use writer instead of mapper
            System.out.println("User list saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUserList() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(USER_FILE);
        if (file.exists() && file.length() > 0) { // Check if file exists and is not empty
            try {
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class);
                userList = mapper.readValue(file, listType);
                System.out.println("User list loaded from file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User file not found or is empty. A new file will be created.");
            saveUserList();
        }
    }
}
