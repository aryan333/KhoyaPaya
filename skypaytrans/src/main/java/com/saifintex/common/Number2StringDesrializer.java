package com.saifintex.common;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class Number2StringDesrializer extends JsonSerializer<BigDecimal>  {
	@Override
	public void serialize(BigDecimal arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		arg1.writeString(arg0.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

	}
}
