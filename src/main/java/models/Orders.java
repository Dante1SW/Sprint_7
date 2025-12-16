package models;

public class Orders {
    private String firstNameClient;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    public Integer track;

    public Integer getTrack() {
        return track;
    }

    public Orders withTrack(Integer track) {
        this.track = track;
        return this;
    }

    public String getFirstNameClient() {
        return firstNameClient;
    }

    public Orders withFirstNameClient(String firstNameClient) {
        this.firstNameClient = firstNameClient;
        return this;
    }

    public String[] getColor() {
        return color;
    }

    public Orders withColor(String[] color) {
        this.color = color;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Orders withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Orders withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public Orders withMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Orders withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public int getRentTime() {
        return rentTime;
    }

    public Orders withRentTime(int rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public Orders withDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Orders withComment(String comment) {
        this.comment = comment;
        return this;
    }

    private String comment;
    private String[] color;
}
