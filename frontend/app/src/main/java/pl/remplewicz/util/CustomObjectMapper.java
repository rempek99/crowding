package pl.remplewicz.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.ZonedDateTime;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        super();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer());
        module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());

        registerModule(module);
    }


}
