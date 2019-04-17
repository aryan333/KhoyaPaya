package com.saifintex.services;

import java.util.List;

import com.saifintex.dto.MessageDto;

public interface MessageService {

	public boolean insertMessageData(List<MessageDto> messageList);
	
}
