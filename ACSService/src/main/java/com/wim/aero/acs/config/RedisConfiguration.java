//package com.wim.aero.acs.config;
//
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//import java.time.Duration;
//import java.util.Arrays;
//
///**
// * @title: RedisConfiguration
// * @author: Ellie
// * @date: 2022/08/17 15:02
// * @description:
// **/
//@Configuration
//public class RedisConfiguration extends CachingConfigurerSupport {
//    @Resource
//    private LettuceConnectionFactory lettuceConnectionFactory;
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(lettuceConnectionFactory);
//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//        template.setConnectionFactory(lettuceConnectionFactory);
//        //key序列化方式
//        template.setKeySerializer(redisSerializer);
//        //value序列化
//        template.setValueSerializer(serializer);
//        //value hashmap序列化
//        template.setHashValueSerializer(serializer);
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    @Bean("myKeyGenerator")
//    @Override
//    public KeyGenerator keyGenerator() {
//        return (Object target, Method method, Object... objects) -> method.getName() + "(" + Arrays.toString(objects) + ")";
//    }
//
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory);
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofDays(3))//只有通过注解的方式设置缓存才生效
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
//                .disableCachingNullValues();
//        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
//    }
//
//}
