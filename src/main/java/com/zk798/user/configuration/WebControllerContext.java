package com.zk798.user.configuration;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置</><br/>
 * Date: 2016/7/14<br/>
 *
 * @author ZRS
 * @since JDK7
 */
@Configuration
@Slf4j
public class WebControllerContext extends WebMvcConfigurationSupport {

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		MappingJackson2HttpMessageConverter cvt = new MappingJackson2HttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		cvt.setSupportedMediaTypes(supportedMediaTypes );
		ObjectMapper mapper = new ObjectMapper();
//		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.setSerializationInclusion(Include.NON_NULL);
		cvt.setObjectMapper(mapper);
		converters.add(cvt);
		log.info("----------------------------------------------------------------------ok");
	}


}
