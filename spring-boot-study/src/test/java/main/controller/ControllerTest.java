package main.controller;

import lombok.extern.slf4j.Slf4j;
import main.util.WaterMarkUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 测试指定controller，controller中没有调用其它component
 */
@Slf4j
//@ExtendWith(SpringExtension.class)
@WebMvcTest(DemoController.class)
public class ControllerTest {
    @Autowired
    private DemoController demo;

    @Test
    void testController() {
        log.info(demo.demo().getData());
        Assertions.assertThat(demo.demo()).isNotNull();
    }

}
