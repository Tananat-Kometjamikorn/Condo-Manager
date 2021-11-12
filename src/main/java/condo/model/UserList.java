package condo.model;

import java.util.ArrayList;

public class UserList{

    private final ArrayList<User> users;

    public UserList() {
        this.users = new ArrayList<>();
    }

    public User checkUsernameAndPassword(String username, String password) throws RuntimeException {
        for (User a : users) {
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                return a;
            }
        }
        return null;
    }

    public void add(User user){
        users.add(user);
    }

    public ArrayList<User> toList() {
        return users;
    }
}
