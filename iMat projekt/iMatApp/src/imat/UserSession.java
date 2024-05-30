package imat;

public class UserSession {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
        System.out.println("Current user set in UserSession: " + (user != null ? user.getFirstName() + " " + user.getLastName() : "null"));
    }
}
