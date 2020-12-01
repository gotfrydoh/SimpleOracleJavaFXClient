package sample.Entity;

public class CatalogOfModels {

    private Long idKM;
    private String model;
    private String brand;
    private String category;
    private int dmc;
    private int seats;
    private String specificInfo;
    private int price;

    public CatalogOfModels(Long idKM, String model, String brand, String category, int dmc, int seats, String specificInfo, int price) {
        this.idKM = idKM;
        this.model = model;
        this.brand = brand;
        this.category = category;
        this.dmc = dmc;
        this.seats = seats;
        this.specificInfo = specificInfo;
        this.price = price;
    }

//constructor without id_km
    public CatalogOfModels(String model, String brand, String category, int dmc, int seats, String specificInfo, int price) {
        this.model = model;
        this.brand = brand;
        this.category = category;
        this.dmc = dmc;
        this.seats = seats;
        this.specificInfo = specificInfo;
        this.price = price;
    }

    public Long getIdKM() {
        return idKM;
    }

    public void setIdKM(Long idKM) {
        this.idKM = idKM;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDmc() {
        return dmc;
    }

    public void setDmc(int dmc) {
        this.dmc = dmc;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getSpecificInfo() {
        return specificInfo;
    }

    public void setSpecificInfo(String specificInfo) {
        this.specificInfo = specificInfo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
