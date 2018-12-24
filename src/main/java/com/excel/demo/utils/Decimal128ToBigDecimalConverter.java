package com.excel.demo.utils;

import java.math.BigDecimal;

import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;

public class Decimal128ToBigDecimalConverter implements Converter<Decimal128, BigDecimal> {

	@Override
	public BigDecimal convert(Decimal128 a_Decimal128) {
		return a_Decimal128.bigDecimalValue();
	}

}
