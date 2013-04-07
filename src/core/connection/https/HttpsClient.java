package core.connection.https;

import android.content.Context;
import android.util.Log;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import com.app.killerapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;

public class HttpsClient extends DefaultHttpClient 
{
	private Context appContext = null;
	private static Scheme httpsScheme = null;
	private static Scheme httpScheme = null;
	final private String DEBUG_TAG = "HttpsClient";

	public HttpsClient(Context appContext) {
		this.appContext = appContext;

		if (httpScheme == null || httpsScheme == null) {
			httpScheme = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
			httpsScheme = new Scheme("https", mySSLSocketFactory(), 443);
		}
		
		getConnectionManager().getSchemeRegistry().register(httpScheme);
		getConnectionManager().getSchemeRegistry().register(httpsScheme);
	}

	private SSLSocketFactory mySSLSocketFactory() {
		SSLSocketFactory ret = null;
		try {
			final KeyStore ks = KeyStore.getInstance("BKS");
			final InputStream inputStream = appContext.getResources().openRawResource(R.raw.certs);
			ks.load(inputStream, appContext.getString(R.string.store_pass).toCharArray());
			inputStream.close();

			ret = new SSLSocketFactory(ks);
			ret.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			return ret;
		} catch (UnrecoverableKeyException ex) {
			Log.d(DEBUG_TAG, ex.getMessage());
		} catch (KeyStoreException ex) {
			Log.d(DEBUG_TAG, ex.getMessage());
		} catch (KeyManagementException ex) {
			Log.d(DEBUG_TAG, ex.getMessage());
		} catch (NoSuchAlgorithmException ex) {
			Log.d(DEBUG_TAG, ex.getMessage());
		} catch (IOException ex) {
			Log.d(DEBUG_TAG, ex.getMessage());
		} catch (Exception ex) {
			Log.d(DEBUG_TAG, ex.getMessage());
		}
		return null;
	}
}