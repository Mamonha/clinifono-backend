//package com.app.clinifono.configuration.web;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//public class WebConfig  {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Permite CORS para todas as rotas
//                        .allowedOrigins("*") // Permite apenas o frontend
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
//                        .allowedHeaders("*") // Permite todos os headers
//                        .allowCredentials(true); // Permite cookies e autenticações
//            }
//        };
//    }
//}
