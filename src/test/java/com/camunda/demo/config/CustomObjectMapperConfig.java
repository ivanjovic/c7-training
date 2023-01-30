package com.camunda.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat;
import org.camunda.spin.spi.DataFormatConfigurator;

@Slf4j
public class CustomObjectMapperConfig implements DataFormatConfigurator<JacksonJsonDataFormat> {

    @Override
    public Class<JacksonJsonDataFormat> getDataFormatClass() {
        return JacksonJsonDataFormat.class;
    }

    @Override
    public void configure(JacksonJsonDataFormat dataFormat) {
        log.info(" ### .... re-configuring objectmapper....");
        ObjectMapper objectMapper = dataFormat.getObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
}
