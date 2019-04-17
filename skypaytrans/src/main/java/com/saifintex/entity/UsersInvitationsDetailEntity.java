package com.saifintex.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UsersInvitationsDetail")
public class UsersInvitationsDetailEntity extends BaseEntity {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
@Column(name="UsersInvDetailId")
private long id;
@Column(name="User1ID")
private int user1Id;

@Column(name="User2ID")
private int user2Id;
@Column(name="SMSInvitesCount")
private int smsInviteCount;
@Column(name="fcmInviteCount")
private int fcmInviteCount;
@Column(name="InvitationSentDate")
private Date invitationSentDate;

public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public int getUser1Id() {
	return user1Id;
}
public void setUser1Id(int user1Id) {
	this.user1Id = user1Id;
}
public int getUser2Id() {
	return user2Id;
}
public void setUser2Id(int user2Id) {
	this.user2Id = user2Id;
}
public int getSmsInviteCount() {
	return smsInviteCount;
}
public void setSmsInviteCount(int smsInviteCount) {
	this.smsInviteCount = smsInviteCount;
}
public int getFcmInviteCount() {
	return fcmInviteCount;
}
public void setFcmInviteCount(int fcmInviteCount) {
	this.fcmInviteCount = fcmInviteCount;
}
public Date getInvitationSentDate() {
	return invitationSentDate;
}
public void setInvitationSentDate(Date invitationSentDate) {
	this.invitationSentDate = invitationSentDate;
}


}
