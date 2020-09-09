package cn.aaron911.micro.ai.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/ai")
public class AiController {

	@RequestMapping(value = "/textclassify", method = RequestMethod.POST)
	public Map textClassify(@RequestBody Map<String, String> content) {
		return null;
	}
}