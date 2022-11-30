package net.bombonato.salesforce.mc.config;

import com.exacttarget.fuelsdk.ETClient;
import com.exacttarget.fuelsdk.ETSdkException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfiguration {

	@Autowired
	private ApplicationContext context;

	@Bean
	public ETClient getETClient() {
		ETClient client = null;

		try {
			client = context.getBean(ETClient.class);
		} catch (RuntimeException e) {
		}

		if (client == null) {
			try {
				client = new ETClient();
			} catch (ETSdkException e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}

		return client;
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = null;

		try {
			mapper = context.getBean(ObjectMapper.class);
		} catch (RuntimeException e) {
		}

		if (mapper == null) {
			mapper = new ObjectMapper();

			mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
			mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
			mapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);
		}

		return mapper;
	}

}
