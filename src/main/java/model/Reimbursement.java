package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Reimbursement {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Receipt> receipts;
    private BigDecimal distanceDriven;


    public Reimbursement(String firstName, String lastName, LocalDateTime startDate, LocalDateTime endDate, BigDecimal distanceDriven) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distanceDriven = distanceDriven;
    }

    public Reimbursement(String firstName, String lastName, LocalDateTime startDate, LocalDateTime endDate, List<Receipt> receipts, BigDecimal distanceDriven) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.receipts = receipts;
        this.distanceDriven = distanceDriven;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {this.firstName = firstName;}

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

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", receipts=" + receipts.toString() +
                ", distanceDriven=" + distanceDriven +
                '}';
    }
}
