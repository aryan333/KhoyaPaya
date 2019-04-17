package com.saifintex.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.Message;
import com.saifintex.dto.MessageDto;
import com.saifintex.services.MessageService;
import com.saifintex.utils.DateUtils;
//import com.saifintex.validators.MessageValidator;

@RestController
@RequestMapping("/rest")
public class MessageController extends AbstractBase{

	@Autowired 
	MessageService messageService;
	
	@Autowired
	MessageSource messageSource;
	/*
	@Autowired
	MessageValidator messageValidator;*/
	
	@RequestMapping(value = "/private/messagestore", method = RequestMethod.POST)
	public/* @ResponseBody*/ ResponseEntity<?> handleMessage(@Validated @RequestBody List<Message> messageList,BindingResult bindingResult ){	
		getLogger().info("////////////////////////////////////////////////////");
		getLogger().info("message store api----------------------");
		getLogger().info("time ===  "+new Date(System.currentTimeMillis()));
		getLogger().info("message list size : "+messageList.size());
		getLogger().info("Balance="+messageList.get(0).getBalance());
		Map<String,String> responseMap = new HashMap<String,String>();
		/*messageValidator.validate(messageList, bindingResult);*/
		if(bindingResult.hasErrors()){
			
			responseMap.put("response" ,"invalid arguments");
			return new ResponseEntity<Map<String,String>>(responseMap,HttpStatus.BAD_REQUEST);
		}
		List<Map<String,String>> responseList=new ArrayList<Map<String,String>>();
		List<MessageDto> dtoList=new ArrayList<MessageDto>();
		Date date=DateUtils.getCurrentDateTime();
		for(Message message:messageList){
			
			message.setCreatedBy(message.getUserId());
			message.setModifiedBy(message.getUserId());
			message.setCreatedOn(date);
			MessageDto dto=new MessageDto();
			BeanUtils.copyProperties(message, dto);
			dtoList.add(dto);
		}
		/*if(bindingResult.hasErrors()){
			FieldError error=bindingResult.getFieldError();
			String message=messageSource.getMessage(error, null);
			responseMap.put("response" , error.getField()+" "+message);
			return responseMap;
		}*/
		boolean result = messageService.insertMessageData(dtoList);
		if(result){
			responseMap.put("response", "success");
			responseList.add(responseMap);
		}
		else{
			responseMap.put("response", "failure");
			responseList.add(responseMap);
		}

		return new ResponseEntity<List<Map<String,String>>>(responseList,HttpStatus.OK);
		
	}
	
}

