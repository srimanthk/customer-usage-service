/*
package hello;

import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.HttpURLConnection;

public class WebServiceMessageSenderWithAuth extends HttpUrlConnectionMessageSender {

	String user;
	String password;

	public WebServiceMessageSenderWithAuth(String user, String password) {
		this.user = user;
		this.password = password;
	}

	@Override
	protected void prepareConnection(HttpURLConnection connection)
			throws IOException {
		
		BASE64Encoder enc = new sun.misc.BASE64Encoder();
		String userpassword = user+":"+password;
		String encodedAuthorization = enc.encode( userpassword.getBytes() );
		connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

		super.prepareConnection(connection);
	}
}*/
