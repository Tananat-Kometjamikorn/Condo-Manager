package condo.model;

public class User {
    private final String name;
    private final String username;
    private final String password;

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkChangePassword(String currentPassword, String newPassword, String confirmNewPassword ) throws RuntimeException{
        return this.getPassword().equals(currentPassword) && !this.getPassword().equals(newPassword) && newPassword.equals(confirmNewPassword);
    }
}
