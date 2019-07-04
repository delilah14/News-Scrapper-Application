package com.iyoa.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlHeading3;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.iyoa.news.Item;
import com.iyoa.news.NewsApplication;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	
	@GetMapping("/home")
	public String showHomePage() {
		return "News";
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
	
	public static void getWebsite()  {
		String blogUrl = "https://www.bbc.com/news";
		try {
		
		Document doc = Jsoup.connect(blogUrl)
				.userAgent("chrome")
				.timeout(5000)
				.referrer("http://google.com")
				.get();
		Elements divs = doc.getElementsByClass("nw-c-top-stories-primary__story gel-layout gs-u-pb gs-u-pb0@m");
		//the header
		System.out.println(divs.select("h3").text());
		//the p tag
		System.out.println(divs.select("p").text());
		
		//The link
		System.out.println(divs.select("a").text());

		//the imageurl
		System.out.println(divs.select("img").attr("src"));

		}
		catch(IOException x) {
			
		}
	}
	

}
