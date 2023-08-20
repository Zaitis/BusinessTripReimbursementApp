package model;

import java.math.BigDecimal;

public class Receipt {

    private int id;
    private int reimbursementId;
    private Type type;
    private BigDecimal price;

    public Receipt() {}

    public Receipt(int reimbursementId, Type type, BigDecimal price) {
        this.reimbursementId = reimbursementId;
        setType(type);
        setPrice(price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(int reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null.");
        }
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be null or negative.");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", reimbursementId=" + reimbursementId +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}