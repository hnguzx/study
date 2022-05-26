package main.controller;

import lombok.extern.slf4j.Slf4j;
import main.dto.WebResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 志雄
 */
@Slf4j
@RestController
public class PropertiesFile {

    @Value("${spring.datasource.password}")
    String dbPassword;

    @Value("${customer.url}")
    String url;

    @GetMapping("/dbPassword")
    public WebResp<String> getDBPassword() {
        log.info("url:{}", url);
        return WebResp.retOk(dbPassword);
    }
}
