package com.saifintex.services.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.MessageDAO;
import com.saifintex.dto.MessageDto;
import com.saifintex.entity.MessageEntity;
import com.saifintex.services.MessageService;
@Service("messageService")
@Transactional
public class MessageServiceImpl extends AbstractBase implements MessageService{
	@Autowired
	MessageDAO messageDao;
	public boolean insertMessageData(List<MessageDto> messageList){
		removeOTPMessages(messageList);
		//setTime(messageList);
		//changeNumberFormat(messageList);
		//getPartyNameFromMessageBody(messageList);
		List<MessageEntity> messageEntityList=new ArrayList<MessageEntity>();
		for(MessageDto dto:messageList){
			//System.out.println("dto createdAt="+dto.getCreatedAt());
			MessageEntity entity=new MessageEntity();
			BeanUtils.copyProperties(dto, entity);
			//System.out.println("entity createdAt="+entity.getCreatedAt());
			messageEntityList.add(entity);
		}
		int result = messageDao.insertMessageDetail(messageEntityList);
		/*if(result > 0){
			return true;
		}*/
		return true;		
	}

	private void setTime(List<MessageDto> messageList){
		for(MessageDto message:messageList){
			//message.setCreatedAt(DateUtils.getCurrentDateTime());
		}
	}
	
	private void changeNumberFormat(List<MessageDto> messageList){
		DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
		format.setParseBigDecimal(true);
		BigDecimal amount = null , balance = null ;
		for(MessageDto message:messageList){
		    try {		
		    	getLogger().info("Amount="+message.getAmount()+"Balance="+message.getBalance());
				amount = (BigDecimal) format.parse(getNumericAmount(message.getAmount()));
				if(!message.getBalance().contains("Not Found")){
				balance = (BigDecimal) format.parse(getNumericAmount(message.getBalance()));
				
			
				}else{
					balance=new BigDecimal(0.00);
				}
				message.setAmount(amount+"");
				message.setBalance(balance+"");
			} catch (ParseException e) {				
				getLogger().error(e);
			}
		
		}		
		
	}
	private void removeOTPMessages(List<MessageDto> messageList){
		for(int i=0;i<messageList.size();i++){
			MessageDto message=messageList.get(i);
			if(message.getMessageBody().contains("otp") || message.getMessageBody().contains("OTP")){
				messageList.remove(i);
			}
		}
		getLogger().info("messages list after otp removal === "+messageList.size());
	}
	private void getPartyNameFromMessageBody(List<MessageDto> messageList){
		for(int i=0; i< messageList.size();i++){
		String partyName=null;
		MessageDto message=messageList.get(i);
		String messageBody=message.getMessageBody();
		//String balance="Rs.5.00 has been transferred to jtendra sukla(XXXXXXXXX3000) TID <4992316223> Your current balance is 5.50 Thank you for using State Bank Buddy";
		if(messageBody.contains("transferred to") && messageBody.contains("TID")){
			
	   if(messageList.get(i).getSender().contains("SBIWAL")){
				
		int firstIndex=messageBody.lastIndexOf("transferred to");
		getLogger().info(firstIndex);
		getLogger().info("message Body="+messageBody);
		String substring=messageBody.substring(firstIndex+14,messageBody.indexOf("TID")).trim();
		getLogger().info(substring);
		partyName=substring;
	   }
		}else if(messageBody.contains("from")  && messageBody.contains("TID")){
			if(messageList.get(i).getSender().contains("SBIWAL")){
			int firstIndex=messageBody.lastIndexOf("from");
			getLogger().info(firstIndex);
			String substring=messageBody.substring(firstIndex+4,messageBody.indexOf(").")).trim()+")";
			partyName=substring;
			getLogger().info(substring);
			}
		}else{
			getLogger().info("other sms");
			partyName="other sms";
		}
		messageList.get(i).setPartyName(partyName);
	}
	}
	
	
	private String getNumericAmount(String amount){
		String numericAmount = amount;
		if(amount.contains("Rs.") ||amount.contains("rs.") ){
			int index = amount.indexOf('.');
		    numericAmount=amount.substring(index+1).trim();			
		}else if(amount.contains("Rs") || amount.contains("rs")){
			int index = amount.lastIndexOf('s');
		    numericAmount=amount.substring(index+1).trim();		
		}else if(amount.startsWith("INR.")){
			int index = amount.indexOf('.');
		    numericAmount=amount.substring(index+1).trim();	
		}else if(amount.startsWith("INR")){
			int index = amount.indexOf('R');
		    numericAmount=amount.substring(index+1).trim();	
		}
		getLogger().info("numberAmount======="+numericAmount);
		return numericAmount;
	}
	
	/*private void getPartyName(List<Message> messageList){
		for(Message message:messageList){
			String body = message.getMessageBody();
			
		}
	}*/
}
