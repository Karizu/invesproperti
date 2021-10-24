package com.selada.invesproperti.model.request;

public class PaymentRequest {
    private String projectId;
    private int pricePerLot;
    private int lot;
    private int total;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getPricePerLot() {
        return pricePerLot;
    }

    public void setPricePerLot(int pricePerLot) {
        this.pricePerLot = pricePerLot;
    }

    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
