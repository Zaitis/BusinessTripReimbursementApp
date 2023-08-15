package model;

import java.math.BigDecimal;

public class Receipt {
    private int id;
    private int reimbursementId;
    private String type;
    private BigDecimal price;

    public Receipt(int reimbursementId, String type, BigDecimal price) {
        this.reimbursementId = reimbursementId;
        this.type = type;
        this.price = price;
    }

    public Receipt() {}

    public void setId(int id) { this.id = id; }

    public void setReimbursementId(int reimbursementId) { this.reimbursementId = reimbursementId; }

    public void setType(String type) { this.type = type; }

    public void setPrice(BigDecimal price) { this.price = price; }

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

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", reimbursementId=" + reimbursementId +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
