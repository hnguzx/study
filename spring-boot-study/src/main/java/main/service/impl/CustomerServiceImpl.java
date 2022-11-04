package main.service.impl;

import main.dto.dao.CustomerDao;
import main.dto.WebResp;
import main.model.Customer;
import main.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 志雄
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    CustomerDao customerDao;

    @Override
    public WebResp<String> add(Customer customer) {
        return null;
    }

    @Override
    public WebResp<List<Customer>> getAllCustomer() {
        List<Customer> allCustomer = customerDao.getAllCustomer();
        return WebResp.retOk(allCustomer);
    }
}
