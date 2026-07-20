package com.manage.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;

import com.util.Context;
import com.util.SystemProperties;

/**
 * Servlet implementation class ZohoServlet
 */
public class ZohoAccessTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private transient ZohoTimer zohoTimer;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZohoAccessTokenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
		generateZohoCode(request, response);
	}

	@Override
	public synchronized void destroy() {
		if (zohoTimer != null)
			zohoTimer.cancel();
		super.destroy();
	}
	
	public void generateZohoCode(HttpServletRequest request, HttpServletResponse response){
		
//		GetMethod get = null;
		try{
			String code = String.valueOf(request.getParameter("code"));
			boolean erorr = generateZohoAccessToken(code);
			PrintWriter pwr = response.getWriter();
			if(erorr){
				pwr.print("Token is invalid.");
			} else {
				pwr.print("Access Token Created Successfully.");
			}
			
//			String authCodeURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.authcodeurl");
//			String scope = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.auth.scope");
//			String client_id = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.clientid");
//			String response_type = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.auth.responsetype");
//			String access_type = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.auth.accesstype");
//			String prompt = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.auth.prompt");
//			String redirect_uri = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.redirecturl");
//			
//			//----------------------------Fetch ZOHO Data ----------------------
//			
//			authCodeURL += "?scope=" + scope + "&client_id=" + client_id + "&response_type=" + response_type + "&access_type=" + access_type 
//					+ "&prompt=" + prompt + "&redirect_uri=" + redirect_uri;
//  			get = new GetMethod(authCodeURL);
//			
//			HttpClient httpclient = new HttpClient();
//			int result = httpclient.executeMethod(get);
//			
//			System.out.println(result);
//			System.out.println("status     " + get.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
//		} finally {
//			get.releaseConnection();
		}
	}

	@SuppressWarnings("unchecked")
	public boolean generateZohoAccessToken(String code) {
    	PostMethod post = null;
    	try{

    		String refreshToken = "";
    		boolean erorr = false;
	    	String accessTokenURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.accesstokenurl");
			String redirect_uri = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.redirecturl");
			String client_id = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.clientid");
			String client_secret = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.clientsecret");
			String grant_type = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.auth.granttype");
			String username = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.username");
			String password = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.password");
			String refreshtime = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.refreshtime");
			
			//----------------------------Fetch ZOHO Data ----------------------
			
			post = new PostMethod(accessTokenURL);
			post.setParameter("code", code);
			post.setParameter("redirect_uri",redirect_uri);
			post.setParameter("client_id",client_id);
			post.setParameter("client_secret",client_secret);
			post.setParameter("grant_type",grant_type);
			post.setParameter("username", username);
			post.setParameter("password", password);
			
			HttpClient httpclient = new HttpClient();
			int result = httpclient.executeMethod(post);
			
			System.out.println(result);
			System.out.println("status     " + post.getResponseBodyAsString());
			
			JSONObject captureZohoAccessToken = new JSONObject(post.getResponseBodyAsString());
			Iterator<String> captureKeys = captureZohoAccessToken.keys();
			ServletContext context=getServletContext();
			while (captureKeys.hasNext()) {
				String captureKey = (String)captureKeys.next();
		        String captureKeyValue = captureZohoAccessToken.getString(captureKey);
		        
		        if("access_token".equals(captureKey)){
		        	context.setAttribute("ZohoAccessToken", captureKeyValue);
		        } else if("refresh_token".equals(captureKey)){
		        	refreshToken =  captureKeyValue;
		        } else if("error".equals(captureKey)){
		        	erorr = true;
		        }
			}
			if(!"".equals(refreshToken)){
				startZohoTimer(refreshToken, Integer.parseInt(refreshtime) * 60000);
			}
			
			return erorr;
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (post != null)
				post.releaseConnection();
		}
		return true;
	}

	private synchronized void startZohoTimer(String refreshToken, int intervalMillis) {
		if (zohoTimer != null)
			zohoTimer.cancel();
		zohoTimer = new ZohoTimer();
				zohoTimer.setRefreshToken(refreshToken);
				zohoTimer.setContext(getServletContext());
		zohoTimer.createZohoTimer(intervalMillis);
	}
	
}

class ZohoTimer {
    Timer timer;
    String refreshToken = "";
    ServletContext context = null;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
    public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public void createZohoTimer(int intervalMillis) {
		timer = new Timer("AccountantIns-ZohoTokenRefresh", true);
		timer.scheduleAtFixedRate(new RemindTask(), 0, intervalMillis);
	}

	public void cancel() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
			timer = null;
		}
	}

    class RemindTask extends TimerTask {
        public void run() {
            System.out.println("Time's up!");
            generateZohoRefreshAccessToken();
            //timer.cancel(); //Terminate the timer thread
        }
    }
	
	@SuppressWarnings("unchecked")
	public void generateZohoRefreshAccessToken() {
		PostMethod post = null;
    	try{
			String accessTokenURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.accesstokenurl");
			String client_id = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.clientid");
			String client_secret = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.clientsecret");
			String grant_type = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.refresh.granttype");
			String username = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.username");
			String password = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.password");
			
			//----------------------------Fetch ZOHO Data ----------------------
			
			post = new PostMethod(accessTokenURL);
			post.setParameter("refresh_token", getRefreshToken());
			post.setParameter("client_id",client_id);
			post.setParameter("client_secret",client_secret);
			post.setParameter("grant_type",grant_type);
			post.setParameter("username", username);
			post.setParameter("password", password);
			
			HttpClient httpclient = new HttpClient();
			int result = httpclient.executeMethod(post);
			
			System.out.println(result);
			System.out.println("status     " + post.getResponseBodyAsString());
			
			JSONObject captureZohoAccessToken = new JSONObject(post.getResponseBodyAsString());
			Iterator<String> captureKeys = captureZohoAccessToken.keys();
			while (captureKeys.hasNext()) {
				String captureKey = (String)captureKeys.next();
		        String captureKeyValue = captureZohoAccessToken.getString(captureKey);
		        
		        if("access_token".equals(captureKey)){
		        	getContext().setAttribute("ZohoAccessToken", captureKeyValue);
		        	updateZohoAccessToken(captureKeyValue);
		        }
			}
    	} catch(Exception e){
    		e.printStackTrace();
	    	} finally {
			if (post != null)
				post.releaseConnection();
		}
	}
	
	public void updateZohoAccessToken(String captureKeyValue) throws Exception{
		Context ctx = new Context();
		String project = "AccountantIns";
		ctx.setProject(project);
		ctx.put("LastUpdateTimeStamp", new Timestamp(new Date().getTime()));
		ctx.put("isZohoAccessToken", captureKeyValue);
		com.userbo.AccountUtils.updateZohoToken(ctx);
	}
}
