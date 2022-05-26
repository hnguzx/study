package main.config;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 志雄
 */
@Configuration
public class DruidConfig {
    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;
    }

    @Bean
    public WallConfig wallConfig(){
        WallConfig wallConfig = new WallConfig();
        wallConfig.setDir("META-INF/druid/wall/mysql");
        //允许一次执行多条语句
        wallConfig.setMultiStatementAllow(true);
        //是否允许非以上基本语句的其他语句
        wallConfig.setNoneBaseStatementAllow(true);
        //是否进行严格的语法检测
        wallConfig.setStrictSyntaxCheck(false);
        //是否允许进行查询
        wallConfig.setSelectAllow(true);
        return wallConfig;
    }
}
