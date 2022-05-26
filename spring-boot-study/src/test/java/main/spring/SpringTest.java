package main.spring;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

/**
 * 普通的spring boot测试
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringTest {

    @Resource
    private StringEncryptor stringEncryptor;

    /**
     * 配置文件关键信息加密
     */
    @Test
    public void EncodePassword() {
        String dbpassword = stringEncryptor.encrypt("123456");
        String redispassword = stringEncryptor.encrypt("UAT_vbP190624");
        String dbUsername = stringEncryptor.encrypt("admin");
        System.out.println("dbusername:" + dbUsername);
        System.out.println("dbpasswor:" + dbpassword);
        System.out.println("redispassword:" + redispassword);
    }
}
