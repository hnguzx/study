package main.dao;

import lombok.extern.slf4j.Slf4j;
import main.dto.dao.CustomerDao;
import main.model.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Rollback
@SpringBootTest
public class CustomerDaoTest {

    @Resource
    private CustomerDao customerDao;


    @Test
    @Transactional
    public void testAddCustomer() {
        Customer customer = new Customer();
        customer.setUserName("guzx");
        customer.setPassWord("123456");
        Integer result = customerDao.addCustomer(customer);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void testFindCustomer() {
        List<Customer> allCustomer = customerDao.getAllCustomer();
        Assertions.assertThat(allCustomer.size()).isGreaterThan(0);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testReflect() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> aClass = Class.forName("main.dto.dao.CustomerDao");
        Object bean = applicationContext.getBean(aClass);
        Method deleteCustomer = aClass.getDeclaredMethod("deleteCustomer", Integer.class);
        Object invoke = deleteCustomer.invoke(bean, 1);
        log.info("invoke result:{}", invoke.toString());
    }

    @Test
    void batchInsert() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Customer customer = new Customer(1, "guzx", "123");
        Field[] fields = Customer.class.getDeclaredFields();

        Customer.class.getDeclaredMethods();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String name = fields[i].getName();
            Object o = fields[i].get(customer);
            log.info("name:{},value:{}", name, o);

            PropertyDescriptor pd = new PropertyDescriptor(fields[i].getName(), Customer.class);
            //获得get方法
            Method getMethod = pd.getReadMethod();
            //执行get方法返回一个Object
            Object obj = getMethod.invoke(customer);
            System.out.println(obj.toString());
        }

    }

    @Test
    void testSet() {
        //Set<Customer> customers = new HashSet<Customer>();
        //customers.add(new Customer(1, "1", "1"));
        //customers.add(new Customer(2, "2", "2"));
        //customers.add(new Customer(1, "1", "1"));
        //log.info("set:{}", customers);
        //log.info("list:{}", new ArrayList<>(customers).size());

        String customer = "guzx";
        String[] split = customer.split("/");
        log.info("split:{}", split.length);
    }

    @Test
    void testListDistinct() {
        Customer customer1 = new Customer(1, "1", "1");
        Customer customer2 = new Customer(2, "2", "2");
        Customer customer3 = new Customer(3, "3", "3");
        Customer customer4 = new Customer(1, "4", "4");

        List<Customer> list1 = new ArrayList<>();
        List<Customer> list2 = new ArrayList<>();

        list1.add(customer1);
        list1.add(customer2);
        list1.add(customer3);

        list2.add(customer4);

        list1.removeAll(list2);
        log.info("list:{}", list1);

        boolean con = CollectionUtils.containsAny(list2, list1);
        log.info("con:{}", con);

        list1.addAll(list2);
        Map<Integer, Customer> collect = list1.stream().collect(Collectors.toMap(Customer::getId, item -> item, (o1, o2) -> null));
        List<Customer> customerList = collect.values().stream().collect(Collectors.toList());

        log.info("customerList:{}", customerList);

        customerList.parallelStream().forEach(item -> item.setPassWord("password"));


    }

}
