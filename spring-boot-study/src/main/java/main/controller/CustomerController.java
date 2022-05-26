package main.controller;

import main.dto.WebResp;
import main.model.Customer;
import main.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 志雄
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @PostMapping("/add")
    public WebResp<String> addCustomer(@RequestBody Customer customer) {
        WebResp<String> add = customerService.add(customer);
        return add;
    }

    @GetMapping("/all")
    public WebResp<List<Customer>> getAllCustomer(){
        WebResp<List<Customer>> allCustomer = customerService.getAllCustomer();
        return allCustomer;
    }
}
