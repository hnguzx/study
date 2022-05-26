package main.controller;

import main.dto.WebResp;
import main.model.Customer;
import main.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

/**
 * 不指定controller测试，如果controller中需要注入其它component，需要使用mock注入
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest
public class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCustomer() throws Exception {
        // 指定预期结果
        Mockito.when(customerService.getAllCustomer()).thenReturn(WebResp.retOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{code:SUCCESS,message:SUCCESS,data:null,ok:true}"));
    }
}
