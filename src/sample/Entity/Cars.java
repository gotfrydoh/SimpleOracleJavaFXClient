package sample.Entity;

public class Cars {

    private Long carID;
    private Long ID_KM;
    private String locOfCar;
    private String carModel;


    public Cars(Long carID, String carModel, Long ID_KM, String locOfCar) {
        this.carID = carID;
        this.ID_KM = ID_KM;
        this.locOfCar = locOfCar;
        this.carModel = carModel;
    }

    //constructor without carID
    public Cars(Long ID_KM,String carModel ,String locOfCar ) {
        this.ID_KM = ID_KM;
        this.locOfCar = locOfCar;
        this.carModel = carModel;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
    }

    public Long getID_KM() {
        return ID_KM;
    }

    public void setID_KM(Long ID_KM) {
        this.ID_KM = ID_KM;
    }

    public String getLocOfCar() {
        return locOfCar;
    }

    public void setLocOfCar(String locOfCar) {
        this.locOfCar = locOfCar;
    }
}
