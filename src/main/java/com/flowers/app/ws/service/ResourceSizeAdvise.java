package com.flowers.app.ws.service;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResourceSizeAdvise implements ResponseBodyAdvice<Page<?>> {

	
	@Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //Checks if this advice is applicable.
        //In this case it applies to any endpoint which returns a page.
        return Page.class.isAssignableFrom(returnType.getParameterType());
    }
	@Override
    public Page<?> beforeBodyWrite(Page<?> page, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse) {
		serverHttpResponse.getHeaders().add("X-Total-Count", String.valueOf(page.getTotalElements()));
		return page;
	}
	
	/*
	 * @Override public boolean supports(MethodParameter returnType, Class<? extends
	 * HttpMessageConverter<?>> converterType) { return
	 * Collection.class.isAssignableFrom(returnType.getParameterType()); }
	 */
	
	/*
	 * @Override public Collection<?> beforeBodyWrite(Collection<?> body,
	 * MethodParameter returnType, MediaType selectedContentType, Class<? extends
	 * HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
	 * ServerHttpResponse response) { response.getHeaders().add("X-Total-Count",
	 * String.valueOf(body.size())); return body; }
	 */
}