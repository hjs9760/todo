package com.js.ms.todo.global.config.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonFormattingConfig {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(jsonMapperJava8DateTimeModule());
		return objectMapper;
	}

	private Module jsonMapperJava8DateTimeModule() {
		SimpleModule module = new SimpleModule();

		module.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
				return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}
		});

		module.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
			@Override
			public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
				return LocalDate.parse(p.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}
		});

		module.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			@Override
			public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeString(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(value));
			}
		});

		module.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
			@Override
			public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeString(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(value));
			}
		});


		return module;
	}
}