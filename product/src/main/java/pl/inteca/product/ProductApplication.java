package pl.inteca.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) throws InterruptedException {
		// Wait 10 seconds for start database
		Thread.sleep(10_000);
		SpringApplication.run(ProductApplication.class, args);
	}

}
