package com.matt.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
	protected Logger log = Logger.getLogger(BaseService.class);
}
