package hello.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

public class CustomerUsageReq implements Serializable {


    private String msisdn;
    private String startDate;
    private String transactionLimit;
    private String endDate;
    private String type;
    private BillingStatus billing;
    private String category;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(String transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BillingStatus getBilling() {
        return billing;
    }

    public void setBilling(BillingStatus billing) {
        this.billing = billing;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CustomerUsageReq{" +
                "msisdn='" + msisdn + '\'' +
                ", startDate='" + startDate + '\'' +
                ", transactionLimit='" + transactionLimit + '\'' +
                ", endDate='" + endDate + '\'' +
                ", type='" + type + '\'' +
                ", billing='" + billing + '\'' +
                ", Category='" + category + '\'' +
                '}';
    }
}
