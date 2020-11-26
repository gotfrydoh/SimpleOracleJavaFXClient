package sample.Entity;

public class Employee {

    private Long id;
    private String name;
    private String lastName;
    private Long phoneNumber;
    private String adress;
    private String zipCode;
    private String localizationOfWork;

    //constructor without Id to make sql statements
    public Employee(String name, String lastName, Long phoneNumber, String adress, String zipCode, String localizationOfWork) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
        this.zipCode = zipCode;
        this.localizationOfWork = localizationOfWork;
    }

    public Employee(Long id, String name, String lastName, Long phoneNumber, String adress, String zipCode, String localizationOfWork) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
        this.zipCode = zipCode;
        this.localizationOfWork = localizationOfWork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLocalizationOfWork() {
        return localizationOfWork;
    }

    public void setLocalizationOfWork(String localizationOfWork) {
        this.localizationOfWork = localizationOfWork;
    }
}
