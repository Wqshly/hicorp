package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.Customer;
import com.hicorp.segment.service.CustomerService;
import org.springframework.stereotype.Service;

@Service("customerImpl")
public class CustomerImpl extends BasicInterfaceImpl<Customer> implements CustomerService {
}
