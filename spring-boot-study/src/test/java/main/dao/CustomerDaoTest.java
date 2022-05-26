package main.dao;

import lombok.extern.slf4j.Slf4j;
import main.model.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Rollback
@SpringBootTest
public class CustomerDaoTest {

    @Resource
    private CustomerDao customerDao;


    @Test
    @Transactional
    public void testAddCustomer(){
        Customer customer = new Customer();
        customer.setUsername("guzx");
        customer.setPassword("123456");
        Integer result = customerDao.addCustomer(customer);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void testFindCustomer(){
        List<Customer> allCustomer = customerDao.getAllCustomer();
        Assertions.assertThat(allCustomer.size()).isGreaterThan(0);
    }
}
