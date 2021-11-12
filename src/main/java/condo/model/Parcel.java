package condo.model;

import javafx.beans.property.SimpleStringProperty;

public class Parcel extends Mail {

    private final SimpleStringProperty trackNumber;
    private final SimpleStringProperty carrier;

    public Parcel(String staff, String receiver, String sender, String size, String time, String date, String roomNumber,
                    String trackNumber, String carrier) {
        super(staff, receiver, sender, size, time, date, roomNumber);
        this.trackNumber = new SimpleStringProperty(trackNumber);
        this.carrier = new SimpleStringProperty(carrier);
    }

    public String getTrackNumber() {
        return trackNumber.get();
    }

    public SimpleStringProperty trackNumberProperty() {
        return trackNumber;
    }

    public String getCarrier() {
        return carrier.get();
    }

    public SimpleStringProperty carrierProperty() {
        return carrier;
    }
}
