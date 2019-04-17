package com.saifintex.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.saifintex.dto.UserDTO;

import com.saifintex.utils.FcmNotificationUtil;

@Component
public class NotificationThread implements Runnable {

	private List<UserDTO> list;
	
	private String authKey;
	private String name;
	private String phone;
	@Value("${app.user.temp.onboard.msg}")
	private String onBoardMessage;
	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public void setList(List<UserDTO> list) {
		this.list = list;
	}

	


	@Override
	public void run() {
		JsonObject data=new JsonObject();
		data.addProperty("type", "users");
		data.addProperty("phoneNumber", phone);
		/*for(UserDTO userDTO:list){
			String token=userDTO.getFcmToken();
			if(token!=null){
				
				String message="A temp user "+ phone +" is now on board with name "+ name+"";
				FcmNotificationUtil.sendNotification(token, message, authKey, data);
			}
		}*/
		List<String> tokens=new ArrayList<String>();
		for(UserDTO dto:list) {
			tokens.add(dto.getFcmToken());
		}
		
		
		FcmNotificationUtil.notifyToRecipients(tokens, onBoardMessage, authKey, data);
	}

}
