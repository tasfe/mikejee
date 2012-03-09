/**
 * 
 */
package com.heke.framework.common.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * @author gonglei
 * 
 */
public class SpringBindingInitializer implements WebBindingInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.bind.support.WebBindingInitializer#initBinder
	 * (org.springframework.web.bind.WebDataBinder,
	 * org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
//		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, false));
	}

}
