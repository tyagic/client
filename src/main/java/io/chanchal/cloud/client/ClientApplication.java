package io.chanchal.cloud.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Autowired
	EurekaClient eurekaClient;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@RequestMapping("/")
	public String callService()
	{
		RestTemplate restTemplate = restTemplateBuilder.build();
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("service", false);

		String homePageUrl = instanceInfo.getHomePageUrl();

		ResponseEntity<String> responseEntity = restTemplate.exchange(homePageUrl, HttpMethod.GET, null, String.class);

		return "Response from callService "+responseEntity.getBody();
	}
}
