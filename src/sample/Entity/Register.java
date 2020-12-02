package sample.Entity;

import java.util.Date;

public class Register {

    private Long regId;
    private Long pesel;
    private Long empId;
    private Long carId;
    private String dateFrom;
    private String dateTo;
    private String pickupLoc;
    private Long totalPrice;

    public Register(Long regId, Long pesel, Long empId, Long carId, String dateFrom, String dateTo, String pickupLoc, Long totalPrice) {
        this.regId = regId;
        this.pesel = pesel;
        this.empId = empId;
        this.carId = carId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.pickupLoc = pickupLoc;
        this.totalPrice = totalPrice;
    }

    public Register(Long pesel, Long empId, Long carId, String dateFrom, String dateTo, String pickupLoc, Long totalPrice) {
        this.pesel = pesel;
        this.empId = empId;
        this.carId = carId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.pickupLoc = pickupLoc;
        this.totalPrice = totalPrice;
    }

    public Long getRegId() {
        return regId;
    }

    public void setRegId(Long regId) {
        this.regId = regId;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getPickupLoc() {
        return pickupLoc;
    }

    public void setPickupLoc(String pickupLoc) {
        this.pickupLoc = pickupLoc;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
