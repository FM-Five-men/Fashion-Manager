package fashionmanager.song.develop.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// 이미지 때문에 config 하나 만듦
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${C:/uploadFiles/**}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:///C:/uploadFiles/"); // 상위 폴더
    }
}