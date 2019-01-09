package com.clsaa.homework.usm.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class MongoDBConfig {

    @Bean(name = "mongoTemplate")
    public MongoTemplate getMongoTemplate(MongoDbFactory dbFactory, MappingMongoConverter converter) {
        MongoTemplate template = new MongoTemplate(dbFactory, converter);
        return template;
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory, CustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));//去掉默认mapper添加的_class
        mappingConverter.setCustomConversions(conversions);//添加自定义的转换器
        return mappingConverter;
    }

    @Bean
    public CustomConversions customConversions() {
        List list = new ArrayList();
        list.add(new TimestampConverter());
        return new CustomConversions(list);
    }

}

class TimestampConverter implements Converter<Date, Timestamp> {
    @Override
    public Timestamp convert(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }
}