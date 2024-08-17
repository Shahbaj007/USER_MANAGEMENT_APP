package in.rahulit.service;

import java.util.List;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.rahulit.bindings.Quote;


@Service
public class DashboardServiceImpl implements DashboardService {

	private String url = "https://type.fit/api/quotes";

	private Quote[] quotes = null;

	@Override
	public String getQuote() {

		// By using REST Template....

		if(quotes == null) {
			RestTemplate rt = new RestTemplate();
			ResponseEntity<String> forEntity = rt.getForEntity(url, String.class);
			String body = forEntity.getBody();
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				quotes = mapper.readValue(body, Quote[].class);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		Random r = new Random();
		int index = r.nextInt(quotes.length - 1);
		return quotes[index].getText();
				
	}

}
