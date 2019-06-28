package com.iyoa.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
public class NewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsApplication.class, args);
	}
	
	@Controller
	public class Home {
		
		@RequestMapping(value = "/News")
	    public ModelAndView getDateAndTime() {
	        return new ModelAndView("News");

		}
		
		
	}

}
