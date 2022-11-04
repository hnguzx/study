package main.dto.dao;

import main.model.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 志雄
 */
@Mapper
public interface CustomerDao {

    /**
     * 新增用户
     * @param customer
     * @return
     */
    public Integer addCustomer(Customer customer);

    /**
     * 更新用户信息
     * @param customer
     * @return
     */
    public Integer updateCustomer(Customer customer);

    /**
     * 删除用户
     * @param id
     * @return
     */
    public Integer deleteCustomer(@Param("customerId") Integer id);

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    public List<Customer> getCustomerByUsername(@Param("username") String username);

    /**
     * 获取所有用户信息
     * @return
     */
    public List<Customer> getAllCustomer();
}
