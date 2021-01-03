package com.finances.mapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.finances.entity.Budget;
import com.finances.entity.BudgetCategories;
import com.finances.entity.Category;
import com.finances.entity.User;

@JsonComponent
public class BudgetMapper {

	public static class BudgetSerializer extends JsonSerializer<Budget> {

		DateTimeFormatter df = DateTimeFormatter.ISO_DATE_TIME;

		@Override
		public void serialize(Budget value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeStartObject();
			gen.writeNumberField("id", value.getId());
			gen.writeStringField("startDate", df.format(value.getStartDate()));
			gen.writeStringField("endDate", df.format(value.getEndDate()));
			gen.writeNumberField("breakperiod", value.getBreakPeriod());
			gen.writeObjectField("user", value.getUser());
			gen.writeObjectField("categories", value.getCategories());
			gen.writeObjectField("periods", value.getPeriods());
			gen.writeEndObject();

		}
	}

	public static class BudgetDeserializer extends JsonDeserializer<Budget> {

		DateTimeFormatter df = DateTimeFormatter.ISO_DATE_TIME;

		@Override
		public Budget deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			final ObjectMapper mapper = new ObjectMapper();
			final JsonNode json = p.getCodec().readTree(p);
			final Budget ret = new Budget();
			ret.setId(json.get("id").asInt());
			ret.setStartDate(LocalDateTime.parse(json.get("startDate").asText(), df));
			ret.setEndDate(LocalDateTime.parse(json.get("endDate").asText(), df));
			ret.setBreakPeriod(json.get("breakperiod").asInt());
			ret.setUser(mapper.readValue(json.get("user").traverse(), User.class));
			final Iterator<JsonNode> it = json.get("categories").elements();
			final Set<BudgetCategories> categories = new HashSet<>();
			while (it.hasNext()) {
				final JsonNode node = it.next();
				if (node.get("category") != null) {
					final BudgetCategories budCat = new BudgetCategories(
							new Category(node.get("category").get("id").asInt()),
							new BigDecimal(node.get("value").asInt()));
					categories.add(budCat);
				}
			}
			ret.setCategories(categories);
			return ret;
		}

	}

}
