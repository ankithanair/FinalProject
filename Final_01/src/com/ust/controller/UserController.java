package com.ust.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import com.ust.dao.iUserDao;
import com.ust.model.ContactVendor;
import com.ust.model.Login;

@RestController
public class UserController {

	@Autowired
	iUserDao dao;

	//ust login

	@RequestMapping(value = "/api/login/{username}/{userpassword}", method = RequestMethod.GET)
	@ResponseBody
	public Login selectlogin(@PathVariable("username") String username,
			@PathVariable("userpassword") String password) {
		return dao.selectlogin(username, password);
	}

	// View all vendors

	@RequestMapping(value = "/api/vendor/{vendorName}", method = RequestMethod.GET)
	@ResponseBody
	public List getvendor(Model m, @PathVariable("vendorName") String vendorName) {
		List list;
		if (vendorName.equals("null")) {
			list = dao.getVendor();
		} else {
			list = dao.getVendorByName(vendorName);
		}

		return list;
	}

	// Add new vendor

	@ResponseBody
	@RequestMapping(value = "/api/insertvendor", method = RequestMethod.POST)
	public void insertVendor(@RequestBody ContactVendor v2)
			throws ParseException {
		dao.saveVendor(v2);
	}

	// Update existing vendor

	@ResponseBody
	@RequestMapping(value = "/api/updatevendor", method = RequestMethod.PUT)
	public void updateVendor(@RequestBody ContactVendor v2)
			throws ParseException {
		int vendorId = v2.getVendorId();
		dao.updateVendor(vendorId, v2);
	}

	// Disable vendor

	@RequestMapping(value = "/api/disablevendor/{vendorId}", method = RequestMethod.PUT)
	@ResponseBody
	public void disableVendor(@PathVariable("vendorId") int vendorId) {
		dao.disableVendor(vendorId);
	}

	// view vendor list by vendor id

	@RequestMapping(value = "/api/vendors/{vendorId}", method = RequestMethod.GET)
	@ResponseBody
	public ContactVendor getvendors(Model m,
			@PathVariable("vendorId") int vendorId) {
		return dao.getVendorById(vendorId);

	}

	// view contact details by vendor id
	@RequestMapping(value = "/api/contactDetails/{vendorId}", method = RequestMethod.GET)
	@ResponseBody
	public List getContactDetails(Model m,
			@PathVariable("vendorId") int vendorId) {
		List list;
		list = dao.getContactDetails(vendorId);
		return list;

	}

}
