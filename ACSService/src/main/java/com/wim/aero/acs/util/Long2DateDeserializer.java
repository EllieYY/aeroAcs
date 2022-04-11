package com.wim.aero.acs.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @title: Long2DateDeserializer
 * @author: Ellie
 * @date: 2022/04/06 15:40
 * @description:
 **/
public class Long2DateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        long timeStamp = jsonParser.getLongValue() * 1000;
        return new Date(timeStamp);
    }

}
