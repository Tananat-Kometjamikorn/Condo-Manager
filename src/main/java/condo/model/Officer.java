package condo.model;

public class Officer extends User {

    private final String lastLoginDate;
    private final String lastLoginTime;

    public Officer(String username, String name, String password, String lastLoginDate, String lastLoginTime) {
        super(username, name, password);
        this.lastLoginDate = lastLoginDate;
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

}
