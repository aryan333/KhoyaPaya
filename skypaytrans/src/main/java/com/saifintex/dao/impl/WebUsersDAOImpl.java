package com.saifintex.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.WebUsersDAO;
import com.saifintex.web.entity.WebUsersEntity;

@Repository
public class WebUsersDAOImpl extends BaseDAOImpl<WebUsersEntity, Integer>  implements WebUsersDAO {

	/*@Autowired
	JdbcTemplate jdbcTemplate;*/

	@Override
	public WebUsersEntity getAdmin(String userLogin) {				
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(WebUsersEntity.class);
		criteria.add(Restrictions.eq("userLogin", userLogin));
		WebUsersEntity entity = (WebUsersEntity)criteria.uniqueResult();				
		return entity;
	}

	/*@Override
	public List<Message> getMessagesById(final int userId) {
		return jdbcTemplate.query("CALL `USP_GETMessagesByUserID`(?)", new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, userId);

			}
		}, new ResultSetExtractor<List<Message>>() {

			@Override
			public List<Message> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Message> listOfMessages = new ArrayList<Message>();
				while (rs.next()) {
					Message message = new Message();
					message.setMessageId(rs.getInt("MessageId"));
					message.setUserId(rs.getInt("UserId"));
					message.setSender(rs.getString("Sender"));
					message.setMessageBody(rs.getString("MessageBody"));
					message.setType(rs.getString("AmountType"));
					message.setAmount(rs.getString("Amount"));
					message.setPartyName(rs.getString("PartyName"));
					message.setBankName(rs.getString("BankName"));
					message.setCreatedTime(rs.getString("MessageTime"));
					//message.setCreatedAt(rs.getString("CreatedAt"));

					
					 * User user=new User();
					 * user.setFirstName(rs.getString("FirstName"));
					 * user.setLastName(rs.getString("LastName"));
					 * user.setPhoneNo(rs.getString("MobileNumber1"));
					 * user.setUserId(rs.getInt("UserID"));
					 * user.setLatitude(rs.getString("Latitude"));
					 * user.setLongitude(rs.getString("Longitude"));
					 * listOfUsers.add(user);
					 
					listOfMessages.add(message);
				}
				return listOfMessages;
			}
		});

	}*/

	
	
	
}
