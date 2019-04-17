package com.saifintex.dao.impl;

import org.springframework.stereotype.Repository;

import com.saifintex.dao.ItemCategoryDAO;
import com.saifintex.entity.ItemCategoryEntity;
@Repository("itemCategoryDao")
public class ItemCategoryDAOImpl extends BaseDAOImpl<ItemCategoryEntity, Integer> implements ItemCategoryDAO {

}
