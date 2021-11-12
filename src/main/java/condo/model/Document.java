package condo.model;

import javafx.beans.property.SimpleStringProperty;

public class Document extends Mail {

    private final SimpleStringProperty urgency;

    public Document(String staff, String receiver, String sender, String size, String time, String date, String roomNumber, String urgency) {
        super(staff, receiver, sender, size, time, date, roomNumber);
        this.urgency = new SimpleStringProperty(urgency);
    }

    public String getUrgency() {
        return urgency.get();
    }

    public SimpleStringProperty urgencyProperty() {
        return urgency;
    }
}
