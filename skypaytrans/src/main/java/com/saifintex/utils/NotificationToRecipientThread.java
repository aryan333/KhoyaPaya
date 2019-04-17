package com.saifintex.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.saifintex.common.AbstractBase;
import com.saifintex.exception.FCMException;

public class NotificationToRecipientThread extends AbstractBase implements Runnable {
	private String token=null;
	private String authKey=null;
	private String message;
	private JsonObject data;
	private static final String API_URL_FCM="https://fcm.googleapis.com/fcm/send";

	public NotificationToRecipientThread(String token,String authKey,String message,JsonObject data) {
		this.token=token;
		this.authKey=authKey;
		this.message=message;
		this.data=data;
	}


@Override
	public void run() {
	sendNotification();
		
	}
private String sendNotification() {
	String FMCurl = API_URL_FCM;
	URL url = null;

	HttpURLConnection conn = null;

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
		json.addProperty("to", token);
		JsonObject info = new JsonObject();
		// info.addProperty("title", "Notification Title"); // Notification title
		// info.addProperty("body", message); // Notification body
		// json.add("notification", info);
		if (data != null) {
			data.addProperty("message", message);
			json.add("data", data);
		}
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(json.toString());
		wr.flush();

	} catch (IOException e1) {
		// TODO Auto-generated catch block
		getLogger().error(e1);
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
		getLogger().info("response=================" + sb.toString());
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				getLogger().error(e);
			}
		}
	}
	if(sb!=null) {
		return sb.toString();
	}else {
		throw new FCMException();
	}
}
}
