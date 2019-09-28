package com.ust.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.ust.model.ContactVendor;
import com.ust.model.Login;

public interface iUserDao {

	public abstract void setTemplate(JdbcTemplate template);

	public abstract Login selectlogin(String username, String password);

	public abstract List<ContactVendor> getVendor();

	//To get vendor by id
	public abstract ContactVendor getVendorById(int vendorId);

	// View vendor by name

	public abstract List<ContactVendor> getVendorByName(String vendorName);

	public abstract int saveVendor(ContactVendor v2);

	public abstract int updateVendor(int vendorId, ContactVendor v2);

	public abstract int disableVendor(int vendorId);

	public abstract List<ContactVendor> getContactDetails(int vendorId);

}