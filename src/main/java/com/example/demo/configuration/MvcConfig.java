package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/css/**","/images/**").addResourceLocations("classpath:/static/**");

        Path ProductUploadDir = Paths.get("./Product_Image");
        String ImageUploadPath = ProductUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/Product_Image/**").addResourceLocations("file:/" + ImageUploadPath + "/");
    }


}
