package fashionmanager.song.develop.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "fashionmanager.song.develop", annotationClass = Mapper.class)
public class SongAppConfig {

}
