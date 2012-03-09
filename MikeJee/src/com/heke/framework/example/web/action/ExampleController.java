package com.heke.framework.example.web.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heke.framework.example.query.ExampleQuery;
import com.heke.framework.example.service.ExampleService;

@Controller
@RequestMapping(value = "/example")
public class ExampleController {

	@Resource
	private ExampleService exampleService;
	
	@RequestMapping(value = "/examples")
	public String examples() {
		return "frame/example/examples";
	}
	
	@RequestMapping(value = "/queryExamples")
	@ResponseBody
	public ExampleQuery queryDepts(ExampleQuery query) {
		
		ExampleQuery exampleQuery = (ExampleQuery)exampleService.findPagedExample(query);
		
		return exampleQuery;
	}
}
