package com.example.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;

import java.io.Serializable;

//Http Session Configure, for getting Session logic, please refer to WebInterceptor
@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 300,redisNamespace = "test2") // it will override session configure in application.properties
public class RedisConfig implements BeanClassLoaderAware {

    private ClassLoader loader;

    //this bean is used to make the values saved in Redis seems meaningful
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));//if it does not register modules, it will throw following exception
        //java.lang.ClassCastException: class java.util.LinkedHashMap cannot be cast to
        // class org.springframework.security.web.csrf.CsrfToken
        // (java.util.LinkedHashMap is in module java.base of loader 'bootstrap';
        // org.springframework.security.web.csrf.CsrfToken is in unnamed module of loader 'app')
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    //it will throw JsonParseException if this bean is missing
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<String, Serializable>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer(loader));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer(loader));
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;

    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }

}