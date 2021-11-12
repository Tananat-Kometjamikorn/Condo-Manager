package condo.model;

import javafx.beans.property.SimpleStringProperty;

public class Mail {
    private final SimpleStringProperty staff;
    private final SimpleStringProperty receiver;
    private final SimpleStringProperty sender;
    private final SimpleStringProperty size;
    private final SimpleStringProperty time;
    private final SimpleStringProperty date;
    private final SimpleStringProperty roomNumber;

    public Mail(String staff, String receiver, String sender, String size, String time, String date,
                String roomNumber) {
        this.staff = new SimpleStringProperty(staff);
        this.receiver = new SimpleStringProperty(receiver);
        this.sender = new SimpleStringProperty(sender);
        this.size = new SimpleStringProperty(size);
        this.time = new SimpleStringProperty(time);
        this.date = new SimpleStringProperty(date);
        this.roomNumber = new SimpleStringProperty(roomNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mail mail = (Mail) o;
        return getStaff().equals(mail.getStaff())&&
                getReceiver().equals(mail.getReceiver())&&
                getSender().equals(mail.getSender())&&
                getSize().equals(mail.getSize())&&
                getDate().equals(mail.getDate())&&
                getTime().equals(getTime())&&
                getRoomNumber().equals(mail.getRoomNumber());
    }

    public String getStaff() {
        return staff.get();
    }

    public SimpleStringProperty staffProperty() {
        return staff;
    }

    public String getReceiver() {
        return receiver.get();
    }

    public SimpleStringProperty receiverProperty() {
        return receiver;
    }

    public String getSender() {
        return sender.get();
    }

    public SimpleStringProperty senderProperty() {
        return sender;
    }

    public String getSize() {
        return size.get();
    }

    public SimpleStringProperty sizeProperty() {
        return size;
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public SimpleStringProperty roomNumberProperty() {
        return roomNumber;
    }

}
