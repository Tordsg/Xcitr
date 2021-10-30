package restServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import core.Exciter;
@SpringBootApplication
public class ExciterApplication {

	public static Exciter excite = new Exciter();

	public static void main(String[] args) {
		SpringApplication.run(ExciterApplication.class, args);
	}

}
