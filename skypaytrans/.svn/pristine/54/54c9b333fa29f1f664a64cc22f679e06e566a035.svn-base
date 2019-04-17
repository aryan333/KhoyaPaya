package com.saifintex.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saifintex.common.AbstractBase;
import com.saifintex.dao.UserContactsDAO;
import com.saifintex.domain.SMSDetailToInvite;
import com.saifintex.domain.SMSSenderDetail;
import com.saifintex.dto.InviteeDetailDto;
import com.saifintex.dto.UsersContactsDTO;
import com.saifintex.entity.UsersContactsEntity;
import com.saifintex.services.InviteUsersService;
import com.saifintex.utils.DateUtils;
import com.saifintex.utils.FcmNotificationUtil;
import com.saifintex.utils.InviteAllThread;
import com.saifintex.utils.SmsPromoThread;
import com.saifintex.utils.ThreadUtils;
@Service
@Transactional
public class InviteUsersServiceImpl extends AbstractBase implements InviteUsersService{
	
	@Autowired
	private UserContactsDAO userContactsDAO;
	private static final String TRANS_SMS_API_INVITE_SENDER="SAIINV";
	 private static final String SMS_API_SENDER="BULKSMS";
	@Value("${app.sms.message.to.non.app.person.part1}")
	private String messageToNonAppPersonPart1;

	@Value("${app.sms.message.to.non.app.person.part2}")
	private String messageToNonAppPersonPart2;
	
	@Value("${app.invite.url}")
	private String appInviteUrl;
	
	@Value("${app.invite.all}")
	private boolean isInviteAll;
	@Value("${app.invite.message.head}")
	private String inviteAllMessageHead;
	@Value("${app.invite.message.url}")
	private String inviteUrl;
	@Value("${app.invite.message.footer}")
	private String inviteAllMessageFooter;
	
	
	
	@Override
	public List<UsersContactsDTO> inviteAllUsers(InviteeDetailDto inviteeDetailDto) {
		List<String> contactsNumbers=new ArrayList<String>();
		for(UsersContactsDTO dto:inviteeDetailDto.getContactsDto()) {
			contactsNumbers.add(dto.getContactNumber());
		}
		List<UsersContactsDTO> responseDtoList=null;
	
		contactsNumbers=userContactsDAO.getExistingContatcs(contactsNumbers);
		List<UsersContactsDTO> listOfContacts=getContactsToInvite(inviteeDetailDto.getContactsDto(),contactsNumbers);
		String json=null;
		if(isInviteAll) {
			responseDtoList=inviteeDetailDto.getContactsDto();
			setRequiredFieldsToPersist(responseDtoList);
			
		}else {
			responseDtoList=listOfContacts;
			setRequiredFieldsToPersist(responseDtoList);
		}
		
		
		if(!responseDtoList.isEmpty()) {
			
			json=convertToJson(SMS_API_SENDER, responseDtoList,
					inviteeDetailDto.getUserName(),
					inviteeDetailDto.getReferralCode());
		/*SMSSenderDetail smsSenderDetail=new SMSSenderDetail();
		smsSenderDetail.setSender(TRANS_SMS_API_INVITE_SENDER);
		List<SMSDetailToInvite> smsDetailToInvites=new ArrayList<SMSDetailToInvite>();
		for( UsersContactsDTO dto:listOfContacts) {
			SMSDetailToInvite smsDetailToInvite=new SMSDetailToInvite();
			smsDetailToInvite.setMessage(createMessage(inviteeDetailDto.getUserName(),inviteeDetailDto.getReferralCode()));
			smsDetailToInvite.setTo(dto.getContactNumber());
			smsDetailToInvites.add(smsDetailToInvite);
		}
		smsSenderDetail.setSms(smsDetailToInvites);
		Gson gson=new Gson();
		String json=gson.toJson(smsSenderDetail);
		System.out.println("Json="+json);*/
		if(!listOfContacts.isEmpty()) {
			setRequiredFieldsToPersist(listOfContacts);
		List<UsersContactsEntity> listOfContactEntity=(List<UsersContactsEntity>)(List<?>)copyList(listOfContacts, UsersContactsEntity.class);
		
		userContactsDAO.bulkInsert(listOfContactEntity);
		}
		 InviteAllThread thread=new InviteAllThread(json);
		 ThreadUtils.start(thread);
		
		}
		 
		return responseDtoList;
	}
	
	private List<UsersContactsDTO> getContactsToInvite(List<UsersContactsDTO> listOfContacts,List<String> contactsNumbers){
		List<UsersContactsDTO> dtoList=new ArrayList<UsersContactsDTO>();
		for(UsersContactsDTO dto:listOfContacts) {
			if(!contactsNumbers.contains(dto.getContactNumber())) {
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	
	private String createMessage(String loggedInUserName,String refferalCode) {
		return inviteAllMessageHead + " "+ appInviteUrl + refferalCode;
	}
	
	private void setRequiredFieldsToPersist(List<UsersContactsDTO> list){
		
		/*List<UsersContactsEntity> listOfUsersContactEntities=new ArrayList<UsersContactsEntity>();*/
		for(UsersContactsDTO dto:list) {
			//UsersContactsEntity usersContactsEntity=new UsersContactsEntity();
			//BeanUtils.copyProperties(dto, usersContactsEntity);
			dto.setCreatedBy(dto.getLoggedInUserId());
			dto.setInvited(true);
			dto.setCreatedOn(DateUtils.getCurrentDateTime());
			dto.setSmsResponse("Awaited-DLR");
			
			/*listOfUsersContactEntities.add(usersContactsEntity);*/
		
		}
		//return listOfUsersContactEntities;
	}
	
	private String convertToJson(String sender,List<UsersContactsDTO> contactsDTO,String userName,String refferalCode) {
		
		SMSSenderDetail smsSenderDetail=new SMSSenderDetail();
		smsSenderDetail.setSender(SMS_API_SENDER);
		List<SMSDetailToInvite> smsDetailToInvites=new ArrayList<SMSDetailToInvite>();
		for( UsersContactsDTO dto:contactsDTO) {
			SMSDetailToInvite smsDetailToInvite=new SMSDetailToInvite();
			smsDetailToInvite.setMessage(createMessage(userName,refferalCode));
			smsDetailToInvite.setTo(dto.getContactNumber());
			smsDetailToInvites.add(smsDetailToInvite);
		}
		smsSenderDetail.setSms(smsDetailToInvites);
		Gson gson=new Gson();
		String json=gson.toJson(smsSenderDetail);
		System.out.println("Json="+json);
		return json;
	}

}
