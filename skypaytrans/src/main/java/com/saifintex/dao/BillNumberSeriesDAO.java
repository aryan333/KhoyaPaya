package com.saifintex.dao;

import com.saifintex.entity.BillNumberSeriesEntity;

public interface BillNumberSeriesDAO extends BaseDAO<BillNumberSeriesEntity, Long>{
	public BillNumberSeriesEntity getBYUserId(int userId);

}
