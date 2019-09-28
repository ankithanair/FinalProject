package com.ust.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ust.model.Login;
import com.ust.model.ContactVendor;

public class UserDao implements iUserDao {

	JdbcTemplate template;

	
	@Override
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	//Ust Login

	@Override
	public Login selectlogin(String username, String password) {
		String sql = "select userId,username,password from login where username='"
				+ username + "' and password='" + password + "'";
		return template.queryForObject(sql, new Object[] {},
				new BeanPropertyRowMapper<Login>(Login.class));
	}

	// View all vendors

	@Override
	public List<ContactVendor> getVendor() {
		return template
				.query("select vendorId,vendorName,vendorAddr,vendorLoc,vendorService,vendorPincode,isActive from vendor where isActive=0",
						new RowMapper<ContactVendor>() {
							public ContactVendor mapRow(ResultSet rs, int row)
									throws SQLException {
								ContactVendor v2 = new ContactVendor();

								v2.setVendorId(rs.getInt(1));
								v2.setVendorName(rs.getString(2));
								v2.setVendorAddr(rs.getString(3));
								v2.setVendorLoc(rs.getString(4));
								v2.setVendorService(rs.getString(5));
								v2.setVendorPincode(rs.getInt(6));
								v2.setIsActive(rs.getInt(7));
								return v2;

							}
						});
	}

	//To get vendor by id
	
	@Override
	public ContactVendor getVendorById(int vendorId){
		String sql="select vc.vendorId,vc.vendorName,vc.vendorAddr,vc.vendorLoc,vc.vendorService,vc.vendorPincode,cd.contactId,cd.contactName,cd.department,cd.email,cd.phone from vendor vc join contact cd on vc.vendorId=cd.vendorId where vc.isActive=0 and vc.vendorId='"+ vendorId + "'";
		return template.queryForObject(sql, new Object[]{},
				new BeanPropertyRowMapper<ContactVendor>(ContactVendor.class));
		
	}
	// View vendor by name

	@Override
	public List<ContactVendor> getVendorByName(String vendorName) {
		return template
				.query("select vendorId,vendorName,vendorAddr,vendorLoc,vendorService,vendorPincode,isActive from vendor"
						+ " where vendorName='" + vendorName + "'",
						new RowMapper<ContactVendor>() {
							public ContactVendor mapRow(ResultSet rs, int row)
									throws SQLException {
								ContactVendor v2 = new ContactVendor();

								v2.setVendorId(rs.getInt(1));
								v2.setVendorName(rs.getString(2));
								v2.setVendorAddr(rs.getString(3));
								v2.setVendorLoc(rs.getString(4));
								v2.setVendorService(rs.getString(5));
								v2.setVendorPincode(rs.getInt(6));
								v2.setIsActive(rs.getInt(7));
								return v2;

							}
						});
	}

	// Add vendor and contact details

	@Override
	public int saveVendor(ContactVendor v2) {

		String sql1 = "insert into vendor(vendorName,vendorAddr,vendorLoc,vendorService,vendorPincode,isActive) values "
				+ "('"
				+ v2.getVendorName()
				+ "','"
				+ v2.getVendorAddr()
				+ "','"
				+ v2.getVendorLoc()
				+ "','"
				+ v2.getVendorService()
				+ "'," + v2.getVendorPincode() + "," + 0 + ")";

		template.update(sql1);

		Integer maxId = getSequence();
		String sql2 = "insert into contact(vendorId,contactName,department,email,phone) values ("
				+ maxId
				+ ",'"
				+ v2.getContactName()
				+ "','"
				+ v2.getDepartment()
				+ "','"
				+ v2.getEmail()
				+ "','"
				+ v2.getPhone() + "')";
		return template.update(sql2);

	}

	// To get vendor by id

	private Integer getSequence() {
		Integer seq;
		String sql = "select MAX(vendorId)from vendor";
		seq = template.queryForObject(sql, new Object[] {}, Integer.class);
		return seq;
	}
	

	// Update existing vendor details

	@Override
	public int updateVendor(int vendorId, ContactVendor v2) {

		String sql = "update vendor set vendorName='" + v2.getVendorName()
				+ "' ,vendorAddr='" + v2.getVendorAddr() + "' ,vendorLoc='"
				+ v2.getVendorLoc() + "',vendorService='"
				+ v2.getVendorService() + "',vendorPincode="
				+ v2.getVendorPincode() + ",isActive=" + v2.getIsActive() + " "
				+ "where vendorId =" + vendorId;
		template.update(sql);

		Integer maxId = getSequence();

		String sql1 = "update contact set vendorId=" + vendorId
				+ ",contactName='" + v2.getContactName() + "',department='"
				+ v2.getDepartment() + "',email='" + v2.getEmail()
				+ "',phone='" + v2.getPhone() + "'where contactId = "
				+ v2.getContactId();

		return template.update(sql1);
	}

	// Disable vendor

	@Override
	public int disableVendor(int vendorId) {

		String sql = "update vendor set isActive='1' where vendorId="
				+ vendorId + "";

		return template.update(sql);
	}

	//View all  contact details by vendor id

	@Override
	public List<ContactVendor> getContactDetails(int vendorId) {
		return template
				.query("select contactId,contactName,department,email,phone from contact where vendorId="
						+ vendorId + "", new RowMapper<ContactVendor>() {
					public ContactVendor mapRow(ResultSet rs, int row)
							throws SQLException {
						ContactVendor v2 = new ContactVendor();

						v2.setContactId(rs.getInt(1));
						v2.setContactName(rs.getString(2));
						v2.setVendorId(rs.getInt(3));
						v2.setDepartment(rs.getString(4));
						v2.setEmail(rs.getString(5));
						v2.setPhone(rs.getString(6));
						return v2;
					}
				});
	}

}