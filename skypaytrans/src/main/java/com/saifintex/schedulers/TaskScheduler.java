package com.saifintex.schedulers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.saifintex.utils.FcmNotificationUtil;
//@Component
public class TaskScheduler {
	
	@Value("${app.FCMAuthKey}")
	public String authKey;
	
	@Value("${app.update.secure.app.message}")
	private String updateSecureAppMessage;
	
	
	@Scheduled(cron = "0 18 6 * * ?")
	public void notifyAllUsers() {
		FcmNotificationUtil.notifyAllUsers(new ArrayList<>(), updateSecureAppMessage, authKey,"update");
		System.out.println("Hello scheduler");
	}
	

}
