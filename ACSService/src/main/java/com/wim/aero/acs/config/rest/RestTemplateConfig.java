package com.wim.aero.acs.config.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wim.aero.acs.aop.RestTemplateThrowErrorHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Description : rest template基本配置
 * @Author : Ellie
 * @Date : 2019/2/15
 */
@Configuration
public class RestTemplateConfig {

    private final HttpPoolProperties httpPoolProperties;
    private final ObjectMapper objectMapper;
    @Autowired
    public RestTemplateConfig(HttpPoolProperties httpPoolProperties, ObjectMapper objectMapper) {
        this.httpPoolProperties = httpPoolProperties;
        this.objectMapper = objectMapper;
    }

    /**
     * @Author: Ellie
     * @Description: 定义连接池
     * @Params: []
     * @Return: org.apache.http.impl.conn.PoolingHttpClientConnectionManager
     */
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        /** 配置同时支持http和https */
        // https
//        SSLContextBuilder builder = new SSLContextBuilder();
//        builder.loadTrustMaterial(null, (X509Certificate[] x509Certificates, String s) -> true);
//
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
        } catch (KeyManagementException e) {
            e.getStackTrace();
        } catch (KeyStoreException e) {
            e.getStackTrace();
        }

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                sslContext,
                NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", socketFactory)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(httpPoolProperties.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(httpPoolProperties.getDefaultMaxPerRoute());
        connectionManager.setValidateAfterInactivity(httpPoolProperties.getValidateAfterInactivity());
        return connectionManager;
    }

    /**
     * @Author: Ellie
     * @Description: 定义HttpClient工厂，实例化连接池，设置连接池管理器。
     * @Params: [httpClientConnectionManager]
     * @Return: org.apache.http.impl.client.HttpClientBuilder
     */
    @Bean("httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(
            @Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        return httpClientBuilder;
    }

    /**
     * @Author: Ellie
     * @Description: 注入连接池，获取httpClient
     * @Params: [httpClientBuilder]
     * @Return: org.apache.http.impl.client.CloseableHttpClient
     */
    @Bean
    public CloseableHttpClient getCloseableHttpClient(
            @Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
        return httpClientBuilder.build();
    }

    @Bean
    public RestTemplate restTemplate(CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        clientHttpRequestFactory.setConnectTimeout(httpPoolProperties.getConnectTimeout());
        clientHttpRequestFactory.setConnectionRequestTimeout(httpPoolProperties.getConnectionRequestTimeout());
        clientHttpRequestFactory.setReadTimeout(30000);

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.setErrorHandler(new RestTemplateThrowErrorHandler());

//        restTemplate.getMessageConverters().add(new SkMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    /**
     * @Author: Ellie
     * @Description: 定义requestConfig工厂，预留用于设置proxy、cookies等属性。
     * @Params: []
     * @Return: org.apache.http.client.config.RequestConfig.Builder
     */
    @Bean(name = "configBuilder")
    public RequestConfig.Builder getBuilder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder;
    }

    /**
     * @Author: Ellie
     * @Description: 构建requestConfig对象
     * @Params: [builder]
     * @Return: org.apache.http.client.config.RequestConfig
     */
    @Bean
    public RequestConfig getRequestConfig(
            @Qualifier("configBuilder") RequestConfig.Builder builder) {
        return builder.build();
    }
}
