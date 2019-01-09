package com.clsaa.homework.usm.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author joyren
 */
@Configuration
public class MongoDBConfig {

    @Bean(name = "mongoTemplate")
    public MongoTemplate getMongoTemplate(MongoDbFactory dbFactory, MappingMongoConverter converter) {
        return new MongoTemplate(dbFactory, converter);
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        mappingConverter.setCustomConversions(customConversions());
        return mappingConverter;
    }

    @Bean
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new TimestampConverter());
        return new MongoCustomConversions(converters);
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
}
