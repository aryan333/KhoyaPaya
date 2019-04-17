package com.saifintex.controller.rest;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.InviteeDetail;
import com.saifintex.domain.UsersContacts;
import com.saifintex.dto.InviteeDetailDto;
import com.saifintex.dto.UsersContactsDTO;
import com.saifintex.services.InviteUsersService;
import com.saifintex.services.UserService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@RestController
@RequestMapping("/rest")
public class InviteUsersController extends AbstractBase {
	
	@Autowired
	private InviteUsersService inviteUsersService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping()
	@RequestMapping(value="/private/inviteAll")
   public ResponseEntity<?> inviteAllContacts(@RequestBody InviteeDetail inviteeDetail){
		
   
   InviteeDetailDto detailDto=new InviteeDetailDto();
   BeanUtils.copyProperties(inviteeDetail, detailDto);
   List<UsersContactsDTO> listOfContactsDto=(List<UsersContactsDTO>) (List<?>)copyList(inviteeDetail.getContacts(), UsersContactsDTO.class);
	detailDto.setContactsDto(listOfContactsDto);
	
	
    List<UsersContactsDTO> dtoList=inviteUsersService.inviteAllUsers(detailDto);
    List<UsersContacts> listOfContacts=(List<UsersContacts>) (List<?>)copyList(dtoList, UsersContacts.class);
    inviteeDetail.setContacts(listOfContacts);
   return new ResponseEntity<InviteeDetail>(inviteeDetail,HttpStatus.OK);
	}
	

}
