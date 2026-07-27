package com.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

public final class SharedHttpClient {
	private static final MultiThreadedHttpConnectionManager CONNECTIONS = new MultiThreadedHttpConnectionManager();
	private static final HttpClient CLIENT;

	static {
		CONNECTIONS.getParams().setConnectionTimeout(10000);
		CONNECTIONS.getParams().setSoTimeout(30000);
		CONNECTIONS.getParams().setDefaultMaxConnectionsPerHost(20);
		CONNECTIONS.getParams().setMaxTotalConnections(100);
		CLIENT = new HttpClient(CONNECTIONS);
	}

	private SharedHttpClient() {
	}

	public static HttpClient get() {
		return CLIENT;
	}
}
