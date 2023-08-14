package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Reimbursement {

    private int id;
    private String firstName;
    private String lastName;
    private Date startDate;
    private Date endDate;
    private List<Receipt> receipts;
    private BigDecimal distanceDriven;


}
