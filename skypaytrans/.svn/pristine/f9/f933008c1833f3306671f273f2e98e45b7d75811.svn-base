package com.saifintex.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.saifintex.common.AbstractBase;
import com.saifintex.domain.ReminderNotificationParams;
import com.saifintex.services.UserService;
import com.saifintex.utils.FcmNotificationUtil;

@Controller
@RequestMapping("/admin")
public class NotificationWebController extends AbstractBase {
	
	@Value("${app.FCMAuthKey}")
	public String authKey;
	
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/sendReminder",method=RequestMethod.POST)
	public ResponseEntity<?> sendReminder(@RequestBody ReminderNotificationParams notificationParams){
		getLogger().info("======================Send Reminder=====================");
		String message=null;
		String token=userService.getToken(notificationParams.getOpponentUserId());
		getLogger().info("token="+token);
		getLogger().info("notificationParams.getOpponentUserId())="+notificationParams.getOpponentUserId());
		if(token!=null){
			getLogger().info("token="+token);
		JsonObject data=new JsonObject();
		if(notificationParams.getReminderType().equalsIgnoreCase("receivable")){
			data.addProperty("type", "pay");
			message=""+notificationParams.getLoggedInUserName()+ " notify you to pay "+notificationParams.getSkyCredit()+ "";
			
		}else{
			data.addProperty("type", "pending");
			message=""+notificationParams.getLoggedInUserName()+ " "
					+ "notify you to Accept Transaction having bill Amount "+notificationParams.getTotalBalance()+ "made on "+notificationParams.getCreatedOn()+"";
			
			
		}
		FcmNotificationUtil.sendNotification(token, message, authKey,data);
	}	
		Map<String,String> responseMap=new HashMap<String, String>();
		responseMap.put("response","success");
		
		return new ResponseEntity<Map<String,String>>(responseMap,HttpStatus.OK) ;
	}
	
	
	@RequestMapping(value="/notifyAll",method=RequestMethod.POST)
	public @ResponseBody String notifyAll(@RequestParam("message") String message,@RequestHeader("X-CSRF-Token") String token){				
		List<String> list=userService.getTokenOfAllUsers();
		FcmNotificationUtil.notifyAllUsers(list,message.trim(), authKey,"broadcast");
		return "success";
	}
	
	@RequestMapping(value="/notifySelectedUsers",method=RequestMethod.GET)
	public @ResponseBody String notifySelectedUsers(@RequestParam("message") String message, @RequestParam("tokens[]") List<String> tokens,@RequestHeader("X-CSRF-Token") String token){				
		getLogger().info("----------------In notifySelectedUsers API ---------");
		JsonObject data=new JsonObject();
		data.addProperty("type", "broadcast");
		data.addProperty("message",message );
		FcmNotificationUtil.notifyToRecipients(tokens, message, authKey, data);
	   return "success";
	}
	
	
	@RequestMapping(value="/notifyAllIndividual/{mobile}",method=RequestMethod.POST)
	public @ResponseBody String notifyAllIndiviual(@RequestParam("message") String message,@RequestHeader("X-CSRF-Token") String token,@PathVariable("mobile") String mob){				
		String fcmToken=userService.getTokenOfIndiviual(mob);
		
		if(fcmToken==null)
		{
			return "failure";
		}		
		/*System.out.println("token=="+fcmToken);*/
		JsonObject data=new JsonObject();
		data.addProperty("type", "broadcast");
		FcmNotificationUtil.sendNotification(fcmToken, message, authKey,data);
		return "success";
	}
	
	
}
