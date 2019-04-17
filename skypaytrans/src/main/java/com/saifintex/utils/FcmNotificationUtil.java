package com.saifintex.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.saifintex.exception.FCMException;
import com.saifintex.web.domain.RemainingOTPDomain;


public class FcmNotificationUtil{
	
	private static final String API_URL_FCM="https://fcm.googleapis.com/fcm/send";

	 private static final Log logger = LogFactory.getLog(FcmNotificationUtil.class);
	 private static final String SMS_API_KEY="A6b48e64825b60dc5cea1a2e291021d54";
	 private static final String SMS_API_BASE_URL="https://api-promo.solutionsinfini.com/v4/?";
	 private static final String SMS_API_METHOD="sms";
	 private static final String SMS_API_SENDER="BULKSMS";
	 
	 private static final String OTP_SMS_API_KEY="Ac0000d11884c56259f12ddafc498a596";
	 private static final String OTP_SMS_API_BASE_URL="https://alerts.solutionsinfini.com/api/v4/?";
	 private static final String OTP_SMS_API_METHOD="sms";
	 private static final String OTP_SMS_API_SENDER="Saifin";
	 private static final String TRANS_SMS_API_INVITE_SENDER="SAIINV";
	 private static final String SMS_API_METHOD_JSON="sms.json";
	 
	 
	 
	 
	 @Value("${app.sms.api.key}")
	 private  String smsApiKey;

	 @Value("${app.sms.api.base.url}")
	 private String smsBaseUrl;
	
	 @Value("${app.sms.api.method}")
	 private String smsMethod;

	 
	
	public static void sendNotification(String token, String message, String authKey, JsonObject data)
			throws FCMException {
		NotificationToRecipientThread thread = new NotificationToRecipientThread(token, authKey, message, data);
		ThreadUtils.start(thread);

	}



	public static String notifyAllUsers(List<String> tokens, String message, String authKey,String type) {
		String FMCurl = API_URL_FCM;
		URL url = null;
		String arrayToken[] = new String[tokens.size()];
		HttpURLConnection conn = null;
		JsonObject data=new JsonObject();
		//data.addProperty("type", "broadcast");
		data.addProperty("type", type);
		data.addProperty("message",message );
		int i = 0;
		for (String token : tokens) {
			arrayToken[i] = token;
			i++;
		}
		Gson gson = new GsonBuilder().create();
		String jsonArray = gson.toJson(arrayToken);
		System.out.println("JSON ARRAY===" + jsonArray);
		JsonArray array = new JsonArray();
		for (String token : arrayToken) {
			array.add(token);
		}

		try {
			url = new URL(FMCurl);

			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + authKey);
			conn.setRequestProperty("Content-Type", "application/json");

			JsonObject json = new JsonObject();
			/*
			 * JsonArray ids=new JsonArray(); ids.add(jsonArray);
			 */
			// json.addProperty("registration_ids",builder.toString());
			//JsonObject info = new JsonObject();
			/*info.addProperty("title", "Notificatoin Title"); // Notification title
			info.addProperty("body", message); // Notification body
			json.add("notification", info);*/
			// json.add("registration_ids",array);
			json.addProperty("to", "/topics/news");
			  json.add("data", data); 
			// info.addProperty("message", "hello");
			//json.add("data", info);
			System.out.println("Json=" + json.toString());
			
			 
			 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString().replaceAll("\\\\", ""));
			wr.flush();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}

		InputStream is = null;
		StringBuffer sb = null;
		try {
			is = conn.getInputStream();
			int ch;
			sb = new StringBuffer();
			while ((ch = is.read()) != -1) {
				sb.append((char) ch);
			}
			logger.info("response=================" + sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				logger.error(e);
				}
			}
		}

		return sb.toString();
	}
	
	public static String notifyToRecipients(List<String> tokens, String message, String authKey,JsonObject data) {
		String FMCurl = API_URL_FCM;
		URL url = null;
		String arrayToken[] = new String[tokens.size()];
		HttpURLConnection conn = null;
		data.addProperty("message",message );
		int i = 0;
		for (String token : tokens) {
			arrayToken[i] = token;
			i++;
		}
		Gson gson = new GsonBuilder().create();
		String jsonArray = gson.toJson(arrayToken);
		System.out.println("JSON ARRAY===" + jsonArray);
		JsonArray array = new JsonArray();
		for (String token : arrayToken) {
			array.add(token);
		}

		try {
			url = new URL(FMCurl);

			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + authKey);
			conn.setRequestProperty("Content-Type", "application/json");

			JsonObject json = new JsonObject();
			
			  JsonArray ids=new JsonArray(); ids.add(jsonArray);
			 
			// json.addProperty("registration_ids",builder.toString());
			//JsonObject info = new JsonObject();
			//info.addProperty("title", "Notificatoin Title"); // Notification title
			//info.addProperty("body", message); // Notification body
			//json.add("notification", info);
			json.add("registration_ids",array);
		//	json.addProperty("to", "/topics/news");
			  json.add("data", data); 
			// info.addProperty("message", "hello");
			//json.add("data", info);
			System.out.println("Json=" + json.toString());
			
			 
			 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString().replaceAll("\\\\", ""));
			wr.flush();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}

		InputStream is = null;
		StringBuffer sb = null;
		try {
			is = conn.getInputStream();
			int ch;
			sb = new StringBuffer();
			while ((ch = is.read()) != -1) {
				sb.append((char) ch);
			}
			logger.info("response=================" + sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				logger.error(e);
				}
			}
		}

		return sb.toString();
	}
	
	public static void sendSms(String message,String to,String type) {
	 RestTemplate restTemplate=new RestTemplate();
	 Map<String,String> params=new HashMap<String,String>();
	
	 params.put("api_key", OTP_SMS_API_KEY);
	 params.put("method",OTP_SMS_API_METHOD);
	 params.put("message", message);
	 params.put("to", to);
	 params.put("sender", SMS_API_SENDER);
	String url=OTP_SMS_API_BASE_URL+"api_key="+OTP_SMS_API_KEY+"&method="+OTP_SMS_API_METHOD+"&message="+message+"&to="+to+"&sender="+TRANS_SMS_API_INVITE_SENDER;
	/* UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("api_key", SMS_API_KEY)
			 .queryParam("method", SMS_API_METHOD)
			 .queryParam("message", message)
	 	     .queryParam("to",to)
	 	   .queryParam("sender",SMS_API_SENDER);
	 restTemplate.(builder,
		         String.class);*/
	String result=restTemplate.getForObject(url,String.class);
   logger.info("Sms Result="+result);
	
	
 }
 
 public static void sendOTPSms(String message,String to,String type) {
	 RestTemplate restTemplate=new RestTemplate();
	 Map<String,String> params=new HashMap<String,String>();
	
	 params.put("api_key", OTP_SMS_API_KEY);
	 params.put("method",OTP_SMS_API_METHOD);
	 params.put("message", message);
	 params.put("to", to);
	 params.put("sender", OTP_SMS_API_SENDER);
	String url=OTP_SMS_API_BASE_URL+"api_key="+OTP_SMS_API_KEY+"&method="+OTP_SMS_API_METHOD+"&message="+message+"&to="+to+"&sender="+OTP_SMS_API_SENDER;
	/* UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("api_key", SMS_API_KEY)
			 .queryParam("method", SMS_API_METHOD)
			 .queryParam("message", message)
	 	     .queryParam("to",to)
	 	   .queryParam("sender",SMS_API_SENDER);
	 restTemplate.(builder,
		         String.class);*/
	String result=restTemplate.getForObject(url,String.class);
   logger.info("Sms Result="+result);
	
	
 }
 
 public static long getRemainingOTPFromServiceProvider() {
	 RestTemplate restTemplate=new RestTemplate();
	 Map<String,String> params=new HashMap<String,String>();
	
	 params.put("api_key", OTP_SMS_API_KEY);
	 params.put("method",OTP_SMS_API_METHOD);
	
	 params.put("sender", OTP_SMS_API_SENDER);
	String url=OTP_SMS_API_BASE_URL+"api_key="+OTP_SMS_API_KEY+"&method=account.credits";
	/* UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("api_key", SMS_API_KEY)
			 .queryParam("method", SMS_API_METHOD)
			 .queryParam("message", message)
	 	     .queryParam("to",to)
	 	   .queryParam("sender",SMS_API_SENDER);
	 restTemplate.(builder,
		         String.class);*/
	String result=restTemplate.getForObject(url,String.class);
    ObjectMapper mapper=new ObjectMapper();
    RemainingOTPDomain domain=null;
    try {
    	domain=mapper.readValue(result, RemainingOTPDomain.class);
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	
	
	return domain.getData().getCredits();
 }
 
 
 
 
 public static String notifySelectedusers(List<String> tokens, String message, String authKey) {
		String FMCurl = API_URL_FCM;
		URL url = null;
		String arrayToken[] = new String[tokens.size()];
		HttpURLConnection conn = null;
		JsonObject data=new JsonObject();
		data.addProperty("type", "broadcast");
		data.addProperty("message",message );
		int i = 0;
		for (String token : tokens) {
			arrayToken[i] = token;
			i++;
		}
		Gson gson = new GsonBuilder().create();
		String jsonArray = gson.toJson(arrayToken);
		System.out.println("JSON ARRAY===" + jsonArray);
		JsonArray array = new JsonArray();
		for (String token : arrayToken) {
			array.add(token);
		}

		try {
			url = new URL(FMCurl);

			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + authKey);
			conn.setRequestProperty("Content-Type", "application/json");

			JsonObject json = new JsonObject();
			
			  JsonArray ids=new JsonArray(); ids.add(jsonArray);
			 
			// json.addProperty("registration_ids",builder.toString());
			//JsonObject info = new JsonObject();
			//info.addProperty("title", "Notificatoin Title"); // Notification title
			//info.addProperty("body", message); // Notification body
			//json.add("notification", info);
			json.add("registration_ids",array);
		//	json.addProperty("to", "/topics/news");
			  json.add("data", data); 
			// info.addProperty("message", "hello");
			//json.add("data", info);
			System.out.println("Json=" + json.toString());
			
			 
			 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString().replaceAll("\\\\", ""));
			wr.flush();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}

		InputStream is = null;
		StringBuffer sb = null;
		try {
			is = conn.getInputStream();
			int ch;
			sb = new StringBuffer();
			while ((ch = is.read()) != -1) {
				sb.append((char) ch);
			}
			logger.info("response=================" + sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				logger.error(e);
				}
			}
		}

		return sb.toString();
 } 
 
 
 
 public static void sendPromoSms(String message,String to,String type) {
	 RestTemplate restTemplate=new RestTemplate();
	 Map<String,String> params=new HashMap<String,String>();
	
	 params.put("api_key", SMS_API_KEY);
	 params.put("method",SMS_API_METHOD);
	 params.put("message", message);
	 params.put("to", to);
	 params.put("sender", SMS_API_SENDER);
	String url=SMS_API_BASE_URL+"api_key="+SMS_API_KEY+"&method="+SMS_API_METHOD+"&message="+message+"&to="+to+"&sender="+SMS_API_SENDER;
	/* UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("api_key", SMS_API_KEY)
			 .queryParam("method", SMS_API_METHOD)
			 .queryParam("message", message)
	 	     .queryParam("to",to)
	 	   .queryParam("sender",SMS_API_SENDER);
	 restTemplate.(builder,
		         String.class);*/
	String result=restTemplate.getForObject(url,String.class);
   logger.info("Sms Result="+result);
	
	
 }
 @Async
 public static void sendJsonSms(String json) {
	 RestTemplate restTemplate=new RestTemplate();
	 Map<String,String> params=new HashMap<String,String>();
	 HttpHeaders headers = new HttpHeaders();
	 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	 MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	 map.add("api_key", SMS_API_KEY);
	 map.add("method", SMS_API_METHOD_JSON);
	 map.add("json", json);

	 HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

	 /*params.put("api_key", OTP_SMS_API_KEY);
	 params.put("method",OTP_SMS_API_METHOD);
	 
	 params.put("json", json);
	 params.put("sender", OTP_SMS_API_SENDER);*/
String url=SMS_API_BASE_URL;/*+"api_key="+OTP_SMS_API_KEY+"&method="+SMS_API_METHOD_JSON+"&json="+json+"";*/
	/* UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("api_key", SMS_API_KEY)
			 .queryParam("method", SMS_API_METHOD)H
			 .queryParam("message", message)
	 	     .queryParam("to",to)
	 	   .queryParam("sender",SMS_API_SENDER);
	 restTemplate.(builder,
		         String.class);*/
	
	
	String result=restTemplate.postForObject(url,request,String.class);
   logger.info("Sms Result="+result);
	
	
 }
 
 
}
