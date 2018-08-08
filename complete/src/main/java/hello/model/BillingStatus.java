package hello.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.Arrays;

public enum BillingStatus implements Serializable {
    ACTIVE("active"),
    MONEY("money"),
    DATA("data"),
    SUMMARY("summary"),
    SUMMARYDESC("summarydesc"),
    INVALID("");

    private String billingStatus;

    BillingStatus(String billingStatus){
        this.billingStatus = billingStatus;
    }

    public static BillingStatus getBillingStatus(String s){
        if(ACTIVE.name().equalsIgnoreCase(s)){
            return ACTIVE;
        }else if(MONEY.billingStatus.equalsIgnoreCase(s)){
            return MONEY;
        }else if(DATA.billingStatus.equalsIgnoreCase(s)){
            return DATA;
        }else if (SUMMARY.billingStatus.equalsIgnoreCase(s)){
            return SUMMARY;
        }else if (SUMMARY.billingStatus.equalsIgnoreCase(s)){
            return SUMMARY;
        }else if (SUMMARYDESC.billingStatus.equalsIgnoreCase(s)){
            return SUMMARYDESC;
        }
        throw new IllegalArgumentException("No Enum specified for this string");
    }

    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static BillingStatus parse(String value) {
        return Arrays.stream(BillingStatus.values())
                .filter(billingStatus -> billingStatus.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseGet(() -> BillingStatus.INVALID);
    }
}
