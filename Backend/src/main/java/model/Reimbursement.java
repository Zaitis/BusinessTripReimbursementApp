package model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reimbursement {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Receipt> receipts;
    private BigDecimal distanceDriven;

    private Reimbursement(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.receipts = builder.receipts;
        this.distanceDriven = builder.distanceDriven;
    }

    public Reimbursement(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public void setDistanceDriven(BigDecimal distanceDriven) {
        this.distanceDriven = distanceDriven;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public BigDecimal getDistanceDriven() {
        return distanceDriven;
    }

    public void addReceipt(Receipt receipt) {
        if (receipts == null) {
            receipts = new ArrayList<>();
        }
        receipts.add(receipt);
    }

    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private List<Receipt> receipts = new ArrayList<>();
        private BigDecimal distanceDriven;



        public Builder() {}

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }



        public Builder distanceDriven(BigDecimal distanceDriven) {
            this.distanceDriven = distanceDriven;
            return this;
        }

        public Builder receipts(List<Receipt> receipts) {
            this.receipts = receipts;
            return this;
        }

        public Reimbursement build() {
            return new Reimbursement(this);
        }
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", receipts=" + receipts +
                ", distanceDriven=" + distanceDriven +
                '}';
    }
}