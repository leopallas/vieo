package com.test.performance.mvc.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.performance.mvc.domain.AuthenticationKey;
import com.test.performance.mvc.domain.ServiceDevice;
import com.test.performance.mvc.domain.TestMongoDB;
import com.test.performance.mvc.domain.TestMongoDB1;
import com.test.performance.mvc.domain.TestMongoDB2;

@Controller
@RequestMapping(value = "test")
public class MVCController {
	
	@Autowired
	protected MongoTemplate mongoTemplate;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String get() {
		String hello = "testGet";
		return hello;
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	@ResponseBody
	public String post() {
		String hello = "testPost";
		return hello;
	}	
	
	private final static String _insert = "_insert";
	private final static String _update = "_update";
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public String insert(@RequestParam String key ) {		
		String desc = String.format("insert key '%s' into testMongoDB collection.", key);
		Date sysDate = new Date(System.currentTimeMillis());
		TestMongoDB obj = new TestMongoDB(key, key+_insert, true, desc, sysDate, sysDate);
		this.mongoTemplate.insert(obj);
		return desc;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestParam String key) {		
		String desc = String.format("update key '%s' in testMongoDB collection.", key);
		Date sysDate = new Date(System.currentTimeMillis());		
		Update update = Update.update("active", false);
		update.set("objID", key+_update);
		update.set("desc", desc);
		update.set("updateTime", sysDate);
		
		this.mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(key)), update, TestMongoDB.class);		
		return desc;
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public String query(@RequestParam String key) {
		TestMongoDB obj = this.mongoTemplate.findById(key, TestMongoDB.class);
		if (obj == null) {
			System.out.println("In testMongoDB collcetion can not find specify record !" + key);
			return null;
		}
		return String.format("query key '%s' in testMongoDB collection.", key);
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public String remove(@RequestParam String key ) {		
		this.mongoTemplate.remove(new Query(Criteria.where("_id").is(key)), TestMongoDB.class);		
		return String.format("remove key '%s' in testMongoDB collection.", key);
	}

	@RequestMapping(value = "/queryupdate", method = RequestMethod.POST)
	@ResponseBody
	public String queryAndUpdate(@RequestParam String key) {
		query(key);		
		update(key);
		return String.format("query and update key '%s' in testMongoDB collection.", key);
	}

	@RequestMapping(value = "/insert1", method = RequestMethod.POST)
	@ResponseBody
	public String insertTestMongoDB1(@RequestParam String key) {		
		String desc = String.format("insert key '%s' into testMongoDB1 collection.", key);
		Date sysDate = new Date(System.currentTimeMillis());
		TestMongoDB1 obj = new TestMongoDB1(key, key+_insert, true, desc, sysDate, sysDate);
		this.mongoTemplate.insert(obj);
		return desc;
	}

	@RequestMapping(value = "/insert2", method = RequestMethod.POST)
	@ResponseBody
	public String insertTestMongoDB2(@RequestParam String key) {		
		String desc = String.format("insert key '%s' into testMongoDB2 collection.", key);
		Date sysDate = new Date(System.currentTimeMillis());
		TestMongoDB2 obj = new TestMongoDB2(key, key+_insert, true, desc, sysDate, sysDate);
		this.mongoTemplate.insert(obj);
		return desc;
	}
	
	@RequestMapping(value = "/insertDevice", method = RequestMethod.POST)
	@ResponseBody
	public String insertServiceDevice(@RequestParam String key) {
		ServiceDevice device = fillServiceDevice(key);
		this.mongoTemplate.insert(device);
		return String.format("insert key '%s' in ServiceDevice collection.", key);
	}		
	
	@RequestMapping(value = "/alive", method = RequestMethod.POST)
	@ResponseBody
	public String alive(@RequestParam(value = "key") String key, 
			@RequestParam(value = "query") String query,
			@RequestParam(value = "query1") String query1,
			@RequestParam(value = "query2") String query2,
			@RequestParam(value = "updatebyid") String updateByID
			) {
		
		if(Boolean.parseBoolean(query)) {
			TestMongoDB found = this.mongoTemplate.findById(key, TestMongoDB.class);
			if(found == null) {
				System.out.println("In testMongoDB collcetion can not find specify record !" + key);
				return null;
			}
		}
		
		if(Boolean.parseBoolean(query1)) {
			TestMongoDB1 found1 = this.mongoTemplate.findById(key, TestMongoDB1.class);
			if(found1 == null) {
				System.out.println("In testMongoDB1 collcetion can not find specify record !" + key);
				return null;
			}
		}
		
		if(Boolean.parseBoolean(query2)) {
			TestMongoDB2 found2 = this.mongoTemplate.findById(key, TestMongoDB2.class);
			if(found2 == null) {
				System.out.println("In testMongoDB2 collcetion can not find specify record !" + key);
				return null;
			}
		}
		
		ServiceDevice device = null;
		boolean byID = Boolean.parseBoolean(updateByID);
		if(byID) {			
			device = this.mongoTemplate.findById(key, ServiceDevice.class);				
			device.setLastActiveTime(new Date());
			device.setDynamicDNS("updateByID");
			updateDevice(device, byID);
		} else {
			device = this.mongoTemplate.findOne(new Query(Criteria.where("deviceID").is(key)), ServiceDevice.class);
			device.setLastActiveTime(new Date());
			device.setDynamicDNS("updateByDeviceID");
			updateDevice(device, byID);
		}
		
		return "Successfully";
	}

	private ServiceDevice fillServiceDevice(String key) {
		AuthenticationKey authenticationKey = new AuthenticationKey("qHPgRmJq2GUjb0gFqegXP95SIwY=", "6murg80pRrU=");
		ServiceDevice device = new ServiceDevice();
		device.setId(key);
		device.setAuthenticationKey(authenticationKey);
		device.setCanP2P(false);
		device.setCanWOL(false);
		device.setDeviceID(key);
		device.setDynamicDNS(null);
		device.setExtraInfo("eb21bf3c-ea0e-4cb0-a8d3-64a53df15b55");
		device.setLastActiveTime(new Date());
		device.setMacAddress("54:04:A6:B6:11:14 ");
		device.setManufacture("System manufacturer");
		device.setName("LEO-PC");
		device.setOsVersion("[Windows 7][6.1]");
		device.setPrivateIP("192.168.1.102 ");
		device.setPrivatePort(33256);
		String[] array={"com.smedio.egh.support.media.folder",  "com.smedio.egh.support.media.photo",   "com.smedio.egh.support.media.tag.photo.date", "com.smedio.egh.support.media.tag.photo.folder"};
		device.setProvideServices(array);
		device.setPublicIP("192.168.1.102");
		device.setPublicPort(0);
		device.setSerialID("System Serial NumberBFEBFBFF000206A7");
		device.setStatus(0);		
		device.setUserID("829abbf1-f7ea-432b-a8ce-770dc22cb5d4");
		device.setVersion("1.0");				
		return device;
	}
	
    private void updateDevice(ServiceDevice serviceDevice, boolean updateById) {
        Update update = Update.update("extraInfo", serviceDevice.getExtraInfo());
        update.set("lastActiveTime", serviceDevice.getLastActiveTime());
        update.set("name", serviceDevice.getName());
        update.set("osVersion", serviceDevice.getOsVersion());
        update.set("privateIP", serviceDevice.getPrivateIP());
        update.set("privatePort", serviceDevice.getPrivatePort());
        update.set("provideServices", serviceDevice.getProvideServices());
        update.set("publicIP", serviceDevice.getPublicIP());
        update.set("publicPort", serviceDevice.getPublicPort());
        update.set("status", serviceDevice.getStatus());
        update.set("version", serviceDevice.getVersion());
        update.set("canP2P", serviceDevice.isCanP2P());
        update.set("canWOL", serviceDevice.isCanWOL());
        update.set("dynamicDNS", serviceDevice.getDynamicDNS());
        update.set("macAddress", serviceDevice.getMacAddress());
        update.set("authenticationKey", serviceDevice.getAuthenticationKey());
        if(updateById) {
        	this.mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(serviceDevice.getId())), update, ServiceDevice.class);
        } else {
        	this.mongoTemplate.updateFirst(new Query(Criteria.where("deviceID").is(serviceDevice.getDeviceID())), update, ServiceDevice.class);
        }
    }
}
