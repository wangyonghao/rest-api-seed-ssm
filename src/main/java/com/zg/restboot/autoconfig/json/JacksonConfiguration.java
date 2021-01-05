package com.zg.restboot.autoconfig.json;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangyonghao
 * @date 12/19/20
 */
@Configuration
public class JacksonConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer(){
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

}
