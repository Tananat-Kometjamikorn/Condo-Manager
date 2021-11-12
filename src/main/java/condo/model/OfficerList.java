package condo.model;

import java.util.ArrayList;

public class OfficerList  {
    private final ArrayList<Officer> officers;

    public OfficerList() {
        this.officers = new ArrayList<>();
    }

    public void add(Officer officer){
        officers.add(officer);
    }

    public ArrayList<Officer> toList(){
        return officers;
    }

    public Officer checkUsernameAndPassword(String username, String password) throws RuntimeException {
        for (Officer o : officers) {
            if (o.getUsername().equals(username) && o.getPassword().equals(password)) {
                return o;
            }
        }
        return null;
    }
}
