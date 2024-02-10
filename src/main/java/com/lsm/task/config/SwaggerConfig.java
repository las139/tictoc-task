package com.lsm.task.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("선생님 공고 추천 서버 API 명세서")
                            .description("선생님 공고 추천 서버 API 명세서입니다.")
                            .version("v1.0"));
    }
}
