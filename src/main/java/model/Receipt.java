package model;

import java.math.BigDecimal;

public class Receipt {

    private int id;
    private int reimbursementId;
    private String type;
    private BigDecimal price;

    public Receipt(int id, int reimbursementId, String type, BigDecimal price) {
        this.reimbursementId = reimbursementId;
        this.id = id;
        this.type = type;
        this.price = price;
    }


    public int getId() {
        return id;
    }
    public int getReimbursementId() {
        return reimbursementId;
    }
    public String getType() {
        return type;
    }
    public BigDecimal getPrice() {
        return price;
    }





}
