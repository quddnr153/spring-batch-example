package bw.batch.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Byungwook Lee on 2018-03-17
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RestController
public class HelloController {
	@RequestMapping("/")
	public String hello() {
		return "hello";
	}
}
