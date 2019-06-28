package com.iyoa.news;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.*;

import lombok.extern.slf4j.Slf4j;
@SpringBootApplication
@Slf4j
public class NewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsApplication.class, args);
		getContents();
	}
	
	public static void getContents() {
	
		WebClient web = new WebClient();
		web.getOptions().setJavaScriptEnabled(false);
		web.getOptions().setCssEnabled(false);
		web.getOptions().setUseInsecureSSL(true);
		
		try {
			String baseUrl = "https://www.bbc.com/news";
			URL url = new URL(baseUrl);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy ip", 8080));
			URLConnection connection = url.openConnection(proxy);
			
			
			
			HtmlPage page = web.getPage(baseUrl);
		
			List<HtmlElement> items = page.getByXPath("//*[@id=\"u11584256418190209\"]/div/div/div/div[6]");
			if(!items.isEmpty()) {
				for(HtmlElement htmlItem:items) {
					HtmlHeading3 htmlHeading = ((HtmlHeading3) htmlItem.getFirstByXPath("div[@class='gs-c-promo']/div/div/div/a/h3"));
					Item item = new Item();
					item.setTitle(htmlHeading.asText());
					ObjectMapper mapper = new ObjectMapper();
					String jsonString = mapper.writeValueAsString(item);
					log.info(jsonString);
				
				}
			}
			//log.info(page.asXml());
		}
		catch(IOException ex) {
			log.info(ex.getMessage());

		}
	}
	
	@Controller
	public class Home {
		
		@RequestMapping(value = "/News")
	    public ModelAndView getDateAndTime() {
	        return new ModelAndView("News");

		}
		
		
	}

}
