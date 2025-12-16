package models;

public class Courier {
    public Courier withId(Integer id) {
        this.id = id;
        return  this;
    }

    public String getPassword() {
        return password;
    }

    public Courier withPassword(String password) {
        this.password = password;
        return  this;
    }

    public String getLogin() {
        return login;
    }

    public Courier withLogin(String login) {
        this.login = login;
        return  this;
    }

    public String getFirstNameCourier() {
        return firstNameCourier;
    }

    public Courier withFirstName(String firstName) {
        this.firstNameCourier = firstName;
        return  this;
    }

    public String login;
    private String password;
    private String firstNameCourier;
    public Integer id;
}