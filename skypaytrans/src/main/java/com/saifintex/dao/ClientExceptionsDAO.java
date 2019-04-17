package com.saifintex.dao;

import com.saifintex.dto.BugReportStats;
import com.saifintex.entity.ClientExceptionsEntity;

public interface ClientExceptionsDAO extends BaseDAO<ClientExceptionsEntity, Integer> {
	public BugReportStats getBugStats();
}
