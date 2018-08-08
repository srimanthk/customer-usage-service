package hello;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.util.Base64;

public class SoapRequestHeaderModifier implements WebServiceMessageCallback {
 private final String userName = "svc_ChatbotsTobi";
 private final String passWd = "Vodacom18";

 @Override
 public void doWithMessage(WebServiceMessage message)  {
  if (message instanceof SaajSoapMessage) {
   SaajSoapMessage soapMessageMime = (SaajSoapMessage) message;
   MimeHeaders mimeHeader = soapMessageMime.getSaajMessage().getMimeHeaders();
   mimeHeader.setHeader("Authorization", getB64Auth(userName, passWd));

   SOAPMessage soapMessage = ((SaajSoapMessage)message).getSaajMessage();

   //source.setTimestamp(new );
   SOAPHeaderElement source = null;
   SOAPHeaderElement correlation = null;
   try {
    SOAPHeader header = soapMessage.getSOAPHeader();
       source = header.addHeaderElement(new QName("http://group.vodafone.com/contract/vho/header/v1", "Source", "nh"));
       SOAPElement system = source.addChildElement("System", "nh");
       system.setTextContent("Online");
       SOAPElement time = source.addChildElement("Timestamp", "nh");
       time.setTextContent("2017-11-10T12:02:53");

       correlation = header.addHeaderElement(new QName("http://group.vodafone.com/contract/vho/header/v1", "Correlation", "nh"));
       SOAPElement ConversationID = correlation.addChildElement("ConversationID", "nh");
       ConversationID.setTextContent("b5f9405d-1f44-4cd7-8bc8-deca95bff2a8");

   } catch (SOAPException e) {
    e.printStackTrace();
   }
  }
 }

 private String getB64Auth(String login, String pass) {
  String source = login + ":" + pass;
  String retunVal = "Basic " + Base64.getUrlEncoder().encodeToString(source.getBytes());
  return retunVal;
 }
}