
package hello;

import com.vodafone.group.schema.common.v1.QueryOperatorCodeType;
import com.vodafone.group.schema.common.v1.QueryType;
import com.vodafone.group.schema.common.v1.ValueExpressionType;
import com.vodafone.group.schema.vbm.customer.customer_service_usage.v1.GetCustomerServiceUsageListVBMRequest;
import com.vodafone.group.schema.vbm.customer.customer_service_usage.v1.GetCustomerServiceUsageListVBMResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.NumericType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.TextType;

import javax.xml.bind.JAXBElement;


public class CustomerUsageServiceClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(CustomerUsageServiceClient.class);

	public GetCustomerServiceUsageListVBMResponseType getCustomerServiceUsage(String msisdnStr, String startDateStr, String endDateStr, String transactionLimitStr) {

		GetCustomerServiceUsageListVBMRequest request = new GetCustomerServiceUsageListVBMRequest();
		NumericType startIndex = new NumericType();
		startIndex.setValue("0");
		request.setStartIndex(startIndex);

		NumericType indexPerPage = new NumericType();
		indexPerPage.setValue("10");
		request.setIndexPerPage(indexPerPage);

		QueryType.Criteria.QueryExpression queryExpression = new QueryType.Criteria.QueryExpression();

		ValueExpressionType valueExpressionType = new ValueExpressionType();
		valueExpressionType.setQueryOperatorCode(QueryOperatorCodeType.EQUALS);
		valueExpressionType.setPath("Msisdn");
		TextType msisdn = new TextType();
		msisdn.setValue(msisdnStr);
		valueExpressionType.getValue().add(msisdn);
		queryExpression.getValueExpression().add(valueExpressionType);

		ValueExpressionType valueExpressionType1 = new ValueExpressionType();
		valueExpressionType1.setQueryOperatorCode(QueryOperatorCodeType.EQUALS);
		valueExpressionType1.setPath("StartDate");
		TextType startDate = new TextType();
		startDate.setValue(startDateStr);
		valueExpressionType1.getValue().add(startDate);
		queryExpression.getValueExpression().add(valueExpressionType1);

		ValueExpressionType valueExpressionType2 = new ValueExpressionType();
		valueExpressionType2.setQueryOperatorCode(QueryOperatorCodeType.EQUALS);
		valueExpressionType2.setPath("EndDate");
		TextType endDate = new TextType();
		endDate.setValue(endDateStr);
		valueExpressionType2.getValue().add(endDate);
		queryExpression.getValueExpression().add(valueExpressionType2);

		ValueExpressionType valueExpressionType3 = new ValueExpressionType();
		valueExpressionType3.setQueryOperatorCode(QueryOperatorCodeType.EQUALS);
		valueExpressionType3.setPath("TransactionLimit");
		TextType transactionLimit = new TextType();
		transactionLimit.setValue(transactionLimitStr);
		valueExpressionType3.getValue().add(transactionLimit);
		queryExpression.getValueExpression().add(valueExpressionType3);

		QueryType.Criteria criteria = new QueryType.Criteria();
		criteria.setQueryExpression(queryExpression);

		request.setCriteria(criteria);


		log.info(" Requested for following values  Msisdn = "+msisdnStr +", Start Date = "+ startDateStr+", End Date = "+endDateStr +", Transaction Limit = "+transactionLimitStr);

		JAXBElement<GetCustomerServiceUsageListVBMResponseType> response = (JAXBElement<GetCustomerServiceUsageListVBMResponseType>) getWebServiceTemplate()
				.marshalSendAndReceive("http://c3d-osbmgr.vodacom.corp/services/CustomerServiceUsage/v1",
						request,
						new SoapRequestHeaderModifier());

		return response.getValue();
	}

}
