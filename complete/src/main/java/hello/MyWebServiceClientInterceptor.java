/*
package hello;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapElement;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;

import java.util.Iterator;

public class MyWebServiceClientInterceptor implements ClientInterceptor {

    public boolean handleFault(MessageContext context) throws WebServiceClientException {
        return true;
    }

    public boolean handleResponse(MessageContext context) throws WebServiceClientException {
        SoapMessage soapMessage = (SoapMessage) context.getResponse();
        SoapHeader soapHeader = soapMessage.getSoapHeader();

        Iterator<SoapHeaderElement> qn = soapHeader.examineHeaderElements(MY_SESSIONID_QNAME);

        while (qn.hasNext()) {
            SoapElement elem = qn.next();
            SoapHeaderElement headerElem = (SoapHeaderElement) elem;
            if (StringUtil.validString(headerElem.getText())) {
                if (!headerElem.getText().equals(sessionId)) {
                    sessionId = headerElem.getText();
                    logger.info("Bound to Session ID: " + sessionId);
                }
            }
        }
        return true;
    }

    public boolean handleRequest(MessageContext context) throws WebServiceClientException {
        return true;
    }
}*/
