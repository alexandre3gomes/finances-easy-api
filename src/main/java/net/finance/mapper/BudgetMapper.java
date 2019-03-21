package net.finance.mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import net.finance.entity.Budget;

public class BudgetMapper extends JsonSerializer<Budget> {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

	@Override
	public void serialize(Budget value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("startDate", df.format(value.getStartDate()));
		gen.writeStringField("endDate", df.format(value.getEndDate()));
		gen.writeObjectField("user", value.getUser());
		gen.writeObjectField("categories", value.getCategories());
		gen.writeEndObject();

	}

}
