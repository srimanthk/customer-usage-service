
package hello;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Configuration
public class CustomerUsageServiceConfiguration {

	private static final Logger log = LoggerFactory.getLogger(CustomerUsageServiceConfiguration.class);

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setPackagesToScan("com.vodafone.group.schema.*");

		return marshaller;
	}

	@Bean
	public WebServiceTemplate webServiceTemplate() throws Exception {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(marshaller());
		webServiceTemplate.setUnmarshaller(marshaller());
		webServiceTemplate.setDefaultUri("http://c3d-osbmgr.vodacom.corp/services/CustomerServiceUsage/v1");
		webServiceTemplate.setMessageSender(httpComponentsMessageSender());
		webServiceTemplate.setInterceptors(new ClientInterceptor[]{new ClientInterceptor() {
			@Override
			public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				try {
					messageContext.getRequest().writeTo(os);
				} catch (IOException e) {
					throw new WebServiceIOException(e.getMessage(), e);
				}

				String request = new String(os.toByteArray());
				log.info("Request Envelope: " + request);
				return true;

			}

			@Override
			public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				try {
					messageContext.getResponse().writeTo(os);
				} catch (IOException e) {
					throw new WebServiceIOException(e.getMessage(), e);
				}

				String response = new String(os.toByteArray());
				log.info("Response Envelope: " + response);
				return true;
			}

			@Override
			public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				try {
					messageContext.getResponse().writeTo(os);
				} catch (IOException e) {
					throw new WebServiceIOException(e.getMessage(), e);
				}

				String response = new String(os.toByteArray());
				log.info("Fault Envelope: " + response);
				return true;
			}

			@Override
			public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

			}


		}});

		return webServiceTemplate;
	}

	@Bean
	public CustomerUsageServiceClient customerUsageServiceClient(Jaxb2Marshaller marshaller) {
		CustomerUsageServiceClient client = new CustomerUsageServiceClient();
		client.setDefaultUri("http://c3d-osbmgr.vodacom.corp/services/CustomerServiceUsage/v1");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

	@Bean
	public HttpComponentsMessageSender httpComponentsMessageSender() {
		HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
		// set the basic authorization credentials
		httpComponentsMessageSender.setCredentials(usernamePasswordCredentials());

		return httpComponentsMessageSender;
	}

	@Bean
	public UsernamePasswordCredentials usernamePasswordCredentials() {
		// pass the user name and password to be used
		return new UsernamePasswordCredentials("svc_ChatbotsTobi","Vodacom18");
	}
}


