package main.service;

import main.dto.WebResp;
import main.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 志雄
 */
public interface CustomerService {

    /**
     * 新增用户
     * @param customer
     * @return
     */
    public WebResp<String> add(Customer customer);

    /**
     * 获取所有用户信息
     * @return
     */
    public WebResp<List<Customer>> getAllCustomer();
}
