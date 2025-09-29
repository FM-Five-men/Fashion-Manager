package fashionmanager.lee.develop.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "fashionmanager.lee.develop", annotationClass = Mapper.class)
public class AppConfiguration1 {
}