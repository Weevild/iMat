package imat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
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
        try {
            mapper.writeValue(new File(USER_FILE), userList);
            System.out.println("User list saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUserList() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(USER_FILE);
        if (file.exists()) {
            if (file.length() == 0) {
                System.out.println("User file is empty. Initializing with an empty list.");
                userList = new ArrayList<>();
                saveUserList();
                return;
            }

            try {
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class);
                userList = mapper.readValue(file, listType);
                System.out.println("User list loaded from file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Create the file if it doesn't exist
            saveUserList();
            System.out.println("User file not found. A new file has been created.");
        }
    }
}
