package com.zk798.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Consts;
import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 启动入口
 * @author
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.zk798.user")
@EnableConfigurationProperties
@ConfigurationProperties
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate rest = new RestTemplate();
		HttpComponentsClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory();
		fac.setHttpClient(httpClient());
		fac.setConnectTimeout(3000);
		fac.setReadTimeout(20000);
		//请求转换
		rest.setMessageConverters(converters());
		rest.setRequestFactory(fac);
		return rest;
	}
	@Bean
	public HttpClient httpClient() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setDefaultMaxPerRoute(20);
		connectionManager.setMaxTotal(300);
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();

		connectionManager.setDefaultConnectionConfig(connectionConfig);
		connectionManager.setDefaultSocketConfig(socketConfig);
		connectionManager.closeIdleConnections(30000, TimeUnit.SECONDS);
		return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
	}

	private List<HttpMessageConverter<?>> converters(){
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		StringHttpMessageConverter sc = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		converters.add(sc);
		converters.add(new FormHttpMessageConverter());
		MappingJackson2HttpMessageConverter jsonCv=new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper();
		//TODO 时间看情况定义格式
//		mapper.setDateFormat(new SimpleDateFormat("yyyyMMddHHmmss"));
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		jsonCv.setObjectMapper(mapper);
		//TOdo暂时注解
		converters.add(jsonCv);

		return converters;
	}


}
