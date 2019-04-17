package com.saifintex.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ibm.icu.text.NumberFormat;

public class BigDecimal2CommaSeprtStringDesrializer extends JsonSerializer<BigDecimal>  {
	@Override
	public void serialize(BigDecimal arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		
		
		BigInteger bigInt=arg0.toBigInteger();
		String number=NumberFormat.getNumberInstance(new Locale("en", "IN")).format(bigInt);
		
		arg1.writeString(number);

	}
}
