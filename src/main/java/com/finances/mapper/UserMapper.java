package com.finances.mapper;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.finances.entity.User;

@JsonComponent
public class UserMapper {

	public static class UserSerializer extends JsonSerializer<User> {

		@Override
		public void serialize(User value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeStartObject();
			gen.writeNumberField("id", value.getId());
			gen.writeStringField("name", value.getName());
			gen.writeStringField("username", value.getUsername());
			gen.writeStringField("token", value.getToken());
			gen.writeEndObject();
		}
	}

//	public static class UserDeserializer extends JsonDeserializer<User> {
//
//		@Override
//		public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//			System.out.println(p.getValueAsBoolean());
//			return new User();
//		}
//
//	}

}
