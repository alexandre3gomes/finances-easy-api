package net.finance.mapper;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import net.finance.entity.User;

@JsonComponent
public class UserSerializer extends JsonSerializer<User> {

	@Override
	public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("name", value.getName());
		gen.writeStringField("username", value.getUsername());
		gen.writeStringField("token", value.getToken());
		gen.writeEndObject();
	}

}
