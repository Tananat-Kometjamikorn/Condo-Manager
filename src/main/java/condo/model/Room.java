package condo.model;

import javafx.beans.property.SimpleStringProperty;

public class Room {
    private final SimpleStringProperty condoNumber;
    private final SimpleStringProperty floor;
    private final SimpleStringProperty roomNumber;
    private final SimpleStringProperty roomType;
    private final SimpleStringProperty residentName1;
    private final SimpleStringProperty residentName2;

    public Room(String condoNum, String floor, String roomNum, String roomType, String resName1, String resName2) {
        this.condoNumber = new SimpleStringProperty(condoNum);
        this.floor = new SimpleStringProperty(floor);
        this.roomNumber = new SimpleStringProperty(roomNum);
        this.roomType = new SimpleStringProperty(roomType);
        this.residentName1 = new SimpleStringProperty(resName1);
        this.residentName2 = new SimpleStringProperty(resName2);
    }

    public String getCondoNumber() {
        return condoNumber.get();
    }

    public SimpleStringProperty condoNumberProperty() {
        return condoNumber;
    }

    public void setCondoNumber(String condoNumber) {
        this.condoNumber.set(condoNumber);
    }

    public String getFloor() {
        return floor.get();
    }

    public SimpleStringProperty floorProperty() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor.set(floor);
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public SimpleStringProperty roomNumberProperty() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public String getRoomType() {
        return roomType.get();
    }

    public SimpleStringProperty roomTypeProperty() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }

    public String getResidentName1() {
        return residentName1.get();
    }

    public SimpleStringProperty residentName1Property() {
        return residentName1;
    }

    public void setResidentName1(String residentName1) {
        this.residentName1.set(residentName1);
    }

    public String getResidentName2() {
        return residentName2.get();
    }

    public SimpleStringProperty residentName2Property() {
        return residentName2;
    }

    public void setResidentName2(String residentName2) {
        this.residentName2.set(residentName2);
    }
}
