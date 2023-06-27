package com.example.fakeapiserver.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Configuration
public class CustomErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Object messages = errorAttributes.get("message");
        errorAttributes.clear();
        errorAttributes.put("success", Boolean.FALSE);
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("errors", messages);
        return errorAttributes;
    }
}