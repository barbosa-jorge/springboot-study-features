package com.study.springbootfeatures.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    Contact contact = new Contact(
            "Jorge Luis de Oliveira Barbosa",
            "https://www.linkedin.com/in/jorge-barbosa-295a3334/",
            "jorgeluis6@gmail.com"
    );

    List<VendorExtension> vendorExtensions = new ArrayList<>();

    ApiInfo apiInfo = new ApiInfo(
            "Spring Boot features",
            "This project is being used to learn Spring Boot features",
            "1.0",
            "",
            contact,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            vendorExtensions);

    @Bean
    public Docket apiDocket() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .protocols(new HashSet<>(Arrays.asList("HTTP","HTTPs")))
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("package com.study.springbootfeatures"))
                .paths(PathSelectors.any())
                .build();

        return docket;

    }

}
