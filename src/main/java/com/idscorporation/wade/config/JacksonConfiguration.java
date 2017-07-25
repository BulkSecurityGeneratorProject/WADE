package com.idscorporation.wade.config;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

import com.idscorporation.wade.domain.uitl.aixm.AIXMModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    /**
     * Support for Hibernate types in Jackson.
     */
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    /**
     * Jackson Afterburner module to speed up serialization/deserialization.
     */
    @Bean
    public AfterburnerModule afterburnerModule() {
        return new AfterburnerModule();
    }

    @Bean
    public JtsModule jacksonJTSModule() {
        JtsModule jtsModule = new JtsModule();
        return jtsModule;
    }

    @Bean
    public AIXMModule jacksonAIXMModule() {
        AIXMModule aixmModule = new AIXMModule();
        return aixmModule;
    }
}
