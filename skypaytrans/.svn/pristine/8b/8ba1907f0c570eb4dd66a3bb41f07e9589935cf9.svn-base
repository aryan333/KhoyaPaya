package com.saifintex.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.ReminderNotificationParams;
import com.saifintex.dto.NotificationDTO;
import com.saifintex.services.NotificationService;
import com.saifintex.services.UsersInvitationsDetailService;

@RestController
@RequestMapping("/rest")
public class NotificationController extends AbstractBase {
	
	
	@Autowired
	private UsersInvitationsDetailService usersInvitationsDetailService;
	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(value="/private/sendReminder",method=RequestMethod.POST)
	public ResponseEntity<?> sendReminder(@RequestBody ReminderNotificationParams notificationParams){
		getLogger().info("======================Send Reminder=====================");
		String message=null;
	/*	String token=userService.getToken(notificationParams.getOpponentUserId());
		getLogger().info("token="+token);
		getLogger().info("notificationParams.getOpponentUserId())="+notificationParams.getOpponentUserId());
		if(token!=null){
			getLogger().info("token="+token);
		JsonObject data=new JsonObject();
		if(notificationParams.getReminderType().equalsIgnoreCase("receivable")){
			data.addProperty("type", "pay");
			data.addProperty("phoneNumber",notificationParams.getRecipientMobileNumber());
			message=receivableNotification +" "+notificationParams.getSkyCredit()+" by "+ notificationParams.getLoggedInUserName()+"";
			
		}else{
			data.addProperty("type", "pending");
			data.addProperty("phoneNumber",notificationParams.getRecipientMobileNumber());
			message=notificationParams.getLoggedInUserName()+ " "
					+" "+pendingNotifiacation+" "+notificationParams.getTotalBalance()+ " made on "+notificationParams.getPaymentDate()+"";
			
			
		}
		FcmNotificationUtil.sendNotification(token, message, authKey,data);
	}	
	*/	
		usersInvitationsDetailService.sendFcmReminder(notificationParams);
		Map<String,String> responseMap=new HashMap<String, String>();
		responseMap.put("response","success");
		
		return new ResponseEntity<Map<String,String>>(responseMap,HttpStatus.OK);
	}
	
	/**
	 * @Ajay An Api to send invitation to non app person
	 * @param ReminderNotificationParams
	 * @return Success/Failure
	 */
	@RequestMapping(value="/private/sendInvitation",method=RequestMethod.POST)
	private ResponseEntity<?> sendInvitation(@RequestBody ReminderNotificationParams notificationParams){
		getLogger().info("In SMS Invitation");
		Map<String,String> responseMap=new HashMap<String,String>();
		if(usersInvitationsDetailService.sendInvitation(notificationParams)) {
			responseMap.put("response", "success");
			return new ResponseEntity<Map<String,String>>(responseMap,HttpStatus.OK); 
		}else {
			responseMap.put("response", "failure");
			return new ResponseEntity<Map<String,String>>(responseMap,HttpStatus.BAD_REQUEST); 
		
		}
	}
	
	@RequestMapping(value="/public/getUserNotification/{userId}",method=RequestMethod.GET)
	
	public ResponseEntity<?> getUserNotification(@PathVariable("userId") int userId){
		getLogger().info("In getUserNotification==UserId="+userId);
		Map<String,String> responseMap=new HashMap<String,String>();
		List<NotificationDTO> dtoList=notificationService.getUserNotification(userId);
			responseMap.put("response", "failure");
			return new ResponseEntity<List<NotificationDTO>>(dtoList,HttpStatus.OK); 
		
		
	}
}
