package hello.controller;

import com.vodafone.group.schema.extension.vbo.customer.customer_service_usage.v1.ExtendedLatestTransactions;
import com.vodafone.group.schema.vbm.customer.customer_service_usage.v1.GetCustomerServiceUsageListVBMResponseType;
import com.vodafone.group.schema.vbo.customer.customer_service_usage.v1.CustomerServiceUsageVBOType;
import hello.CustomerUsageServiceClient;
import hello.model.BillingStatus;
import hello.model.CustomerUsageReq;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerUsageController {

    public static final Logger logger = LoggerFactory.getLogger(CustomerUsageController.class);

    @Autowired
    CustomerUsageServiceClient client;

    public static double getDoubleFromNoisyString(String s) {
        return Double.valueOf(
                s.replaceAll(
                        "^.*?(-?\\d+(\\.\\d+)?).*$", "$1"));
    }

    private static boolean isNegative(Double d) {
        return d < 0 ? true : false;
    }

    public static double getDataValue(String value) {
        if (value.contains("KB")) {
            return getDoubleFromNoisyString(value) / 1024.00;
        } else {
            return getDoubleFromNoisyString(value);
        }
    }

    @GetMapping(path = "/usage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomerUsage(@RequestBody CustomerUsageReq req) {
        List<JSONObject> entities = new ArrayList<>();
        JSONObject entity = new JSONObject();

        logger.info(req.toString());
        List<CustomerServiceUsageVBOType> vboList = new ArrayList<>();

        GetCustomerServiceUsageListVBMResponseType customerServiceUsage = client.getCustomerServiceUsage(req.getMsisdn(), req.getStartDate(), req.getEndDate(), req.getTransactionLimit());
        if (null != customerServiceUsage) {
            vboList.addAll(customerServiceUsage.getCustomerServiceUsageVBO());
        }

        if (vboList.size() > 0) {
            List<ExtendedLatestTransactions> latestTransactions = null;
            List<ExtendedLatestTransactions> filter = new ArrayList<>();
            for (CustomerServiceUsageVBOType usage : vboList) {
                if (null != usage) {
                    latestTransactions = usage.getExtension().getLatestTransactions();


                    JSONObject jsonObject = extractData(req, latestTransactions);
                    entities.add(jsonObject);
                }
                //logger.info("" + latestTransactions.toString());
            }
            //vboList.getExtension().getLatestTransactions();
        }

        return new ResponseEntity<Object>(entities, HttpStatus.OK);
    }

    private JSONObject extractData(CustomerUsageReq req, List<ExtendedLatestTransactions> transactions) {
        JSONObject entity = new JSONObject();
        Double total = new Double("0.00");
        entity.put("auth", "unknown operation");
        switch (req.getBilling()) {
            case ACTIVE:
                for (ExtendedLatestTransactions transaction : transactions) {
                    if (transaction.getTxTypeGroup().equalsIgnoreCase(req.getType())) {
                        entity.put("auth", true);
                        return entity;
                    }
                }
                entity.put("auth", false);
                return entity;
            case DATA:
                for (ExtendedLatestTransactions transaction : transactions) {
                    if (transaction.getTxTypeGroup().equalsIgnoreCase(req.getType())) {
                        if (transaction.getType().equalsIgnoreCase(req.getBilling().name()) && (transaction.getUomDeltaDisplay().contains("KB") || transaction.getUomDeltaDisplay().contains("MB"))) {
                            Double val = getDataValue(transaction.getUomDeltaDisplay());
                            if (isNegative(val)) {
                                total += val;
                            }
                        }
                    }
                }
                entity.put("auth", total + " MB.");
                return entity;
            case MONEY:
                for (ExtendedLatestTransactions transaction : transactions) {
                    if (transaction.getTxTypeGroup().equalsIgnoreCase(req.getType())) {
                        if (transaction.getType().equalsIgnoreCase(req.getBilling().name()) && (transaction.getUomDeltaDisplay().contains("R"))) {
                            Double val = getDoubleFromNoisyString(transaction.getUomDeltaDisplay());
                            if (isNegative(val)) {
                                total += val;
                            }
                        }
                    }
                }
                entity.put("auth", "R " + total + ".");
                return entity;
            case SUMMARY:
                break;
            case SUMMARYDESC:
                break;
            case INVALID:
                break;
            default:
                return entity;
        }
        return entity;
    }
}
