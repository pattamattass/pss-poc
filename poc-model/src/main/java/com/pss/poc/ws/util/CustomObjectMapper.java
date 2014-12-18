package com.pss.poc.ws.util;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class CustomObjectMapper extends ObjectMapper {
	public CustomObjectMapper() {
		super();
		super.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);
		super.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
		super.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
		super.setAnnotationIntrospector(pair);
	}

}