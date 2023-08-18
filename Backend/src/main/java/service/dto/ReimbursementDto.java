package service.dto;

import model.Receipt;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDto {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Receipt> receipts;
    private BigDecimal distanceDriven;
    private BigDecimal total;

    public ReimbursementDto(ReimbursementDto.Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.receipts = builder.receipts;
        this.distanceDriven = builder.distanceDriven;
        this.total = builder.total;
    }
    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private List<Receipt> receipts = new ArrayList<>();
        private BigDecimal distanceDriven;
        private BigDecimal total;



        public Builder() {}

        public ReimbursementDto.Builder id(int id) {
            this.id = id;
            return this;
        }

        public ReimbursementDto.Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ReimbursementDto.Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ReimbursementDto.Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public ReimbursementDto.Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }



        public ReimbursementDto.Builder distanceDriven(BigDecimal distanceDriven) {
            this.distanceDriven = distanceDriven;
            return this;
        }

        public ReimbursementDto.Builder receipts(List<Receipt> receipts) {
            this.receipts = receipts;
            return this;
        }

        public ReimbursementDto.Builder total(BigDecimal total) {
            this.total = total;
            return this;
        }
        public ReimbursementDto build() {
            return new ReimbursementDto(this);
        }
    }

    public List<Receipt> getReceipts() {
        return receipts;
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

    public BigDecimal getDistanceDriven() {
        return distanceDriven;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "ReimbursementDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", receipts=" + receipts +
                ", distanceDriven=" + distanceDriven +
                ", total=" + total +
                '}';
    }
}
