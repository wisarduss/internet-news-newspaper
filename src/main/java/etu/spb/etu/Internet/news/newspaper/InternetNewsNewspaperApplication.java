package etu.spb.etu.Internet.news.newspaper;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InternetNewsNewspaperApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternetNewsNewspaperApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
