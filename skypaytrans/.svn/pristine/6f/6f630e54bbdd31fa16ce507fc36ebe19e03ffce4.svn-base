package com.saifintex.services.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import com.saifintex.utils.DateUtils;

public class TestAbility {

	public static void main(String[] args) {
		 getLastWeekDateRange();
		 getDate();
		
		System.out.println(DateUtils.getDateBeforeCurrentDate(1));
		System.out.println(DateUtils.getDateBeforeCurrentDate(2));
		System.out.println(DateUtils.getDateBeforeCurrentDate(8));
		System.out.println(DateUtils.getDateBeforeCurrentDate(9));
		Date date = DateUtils.getCurrentDateInDateObject();
		System.out.println(date);
		print(2);		
		
	}
	
	private static void print(int i) {
		
		System.out.println("int"+i);
	}
	
	private static void print(float i) {
		System.out.println(i);
	}

	public static Map<String, Date> getLastWeekDateRange() {
		Date date = new Date();
		Map<String, Date> dateRange = new HashMap<String, Date>();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
		c.add(Calendar.DATE, -i - 7);
		Date start = c.getTime();
		c.add(Calendar.DATE, 6);
		Date end = c.getTime();
		dateRange.put("startDate", start);
		dateRange.put("endDate", end);
		System.out.println(start + " - " + end);
		return dateRange;
	}

	public static void getDate() {
		Calendar cal = Calendar.getInstance();
		int days = 2;
		cal.add(Calendar.DATE, -(days));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(cal.getTime());
		// DateUtils.getDate(date);
		System.out.println(DateUtils.getDate(date));
		return;
	}
		
}