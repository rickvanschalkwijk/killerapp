package core.connection;

public class ServiceFactory {
	
	private static final Connector connector = new HTTPConnector();
	
	public static Connector getConnectorInstance() {
		return connector;
	}
}
