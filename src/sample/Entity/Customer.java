package sample.Entity;

public class Customer {

    private Long PESEL;
    private String name;
    private String lastName;
    private Long phoneNumber;
    private String adress;
    private String zipCode;
    private String country;
    private String localizationOfRegistration;

    public Customer(Long PESEL, String name, String lastName, Long phoneNumber, String adress, String zipCode, String country, String localizationOfRegistration) {
        this.PESEL = PESEL;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
        this.zipCode = zipCode;
        this.country = country;
        this.localizationOfRegistration = localizationOfRegistration;
    }

    public Long getPESEL() {
        return PESEL;
    }

    public void setPESEL(Long PESEL) {
        this.PESEL = PESEL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocalizationOfRegistration() {
        return localizationOfRegistration;
    }

    public void setLocalizationOfRegistration(String localizationOfRegistration) {
        this.localizationOfRegistration = localizationOfRegistration;
    }
}
