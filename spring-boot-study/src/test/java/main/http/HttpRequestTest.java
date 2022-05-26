package main.http;

import main.dto.WebResp;
import main.enums.CommonRespCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * http测试
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * 调用网络服务
     */
    @Test
    public void testDemo() {
        ResponseEntity<WebResp> result = restTemplate.getForEntity("http://localhost:" + port + "/demo", WebResp.class);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody().getCode()).isEqualTo(CommonRespCode.SUCCESS);
    }

    @Autowired
    private MockMvc mockMvc;

    /**
     * 调用本地服务
     * @throws Exception
     */
    @Test
    public void testDemo2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/demo"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().json("{code:SUCCESS,message:SUCCESS,ok:true}", false));

    }
}
