package condo.model;

import java.util.ArrayList;

public class RoomList {
    private final ArrayList<Room> rooms;

    public RoomList() {
        this.rooms = new ArrayList<>();
    }

    public boolean checkRoomMatch(String roomNumber, String receiver){
        for (Room r : rooms){
            if ((r.getRoomNumber().equals(roomNumber)&&r.getResidentName1().equals(receiver))||
                    (r.getRoomNumber().equals(roomNumber)&&r.getResidentName2().equals(receiver))){
                return true;
            }
        }
        return false;
    }

    public void add(Room room){
        rooms.add(room);
    }

    public ArrayList<Room> toList(){
        return rooms;
    }

}
