package imat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String postalCode;
    private String city;
    private String apartmentNumber;
    private String password;
    private String holdersName;
    private int expirationMonth;
    private int expirationYear;
    private String cardNumber;
    private int securityCode;
    private LocalDate expirationDate;

    // Default constructor for Jackson
    public User() {
    }

    public User(String firstName, String lastName, String phone, String email, String address, String postalCode, String city, String apartmentNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.apartmentNumber = apartmentNumber;
        this.password = password;

        // Initialize credit card information with default values
        this.holdersName = "";
        this.expirationMonth = 0;
        this.expirationYear = 0;
        this.cardNumber = "";
        this.securityCode = 0;
    }

    // Getters and setters with JsonProperty for Jackson

    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty
    public String getPhone() {
        return phone;
    }

    @JsonProperty
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @JsonProperty
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty
    public String getCity() {
        return city;
    }

    @JsonProperty
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty
    public String getApartmentNumber() {
        return apartmentNumber;
    }

    @JsonProperty
    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public String getHoldersName() {
        return holdersName;
    }

    @JsonProperty
    public void setHoldersName(String holdersName) {
        this.holdersName = holdersName;
    }

    @JsonProperty
    public int getExpirationMonth() {
        return expirationMonth;
    }

    @JsonProperty
    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @JsonProperty
    public int getExpirationYear() {
        return expirationYear;
    }

    @JsonProperty
    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    @JsonProperty
    public String getCardNumber() {
        return cardNumber;
    }

    @JsonProperty
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @JsonProperty
    public int getSecurityCode() {
        return securityCode;
    }

    @JsonProperty
    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    @JsonProperty
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @JsonProperty
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        if (expirationDate != null) {
            this.expirationMonth = expirationDate.getMonthValue();
            this.expirationYear = expirationDate.getYear();
        }
    }
}
