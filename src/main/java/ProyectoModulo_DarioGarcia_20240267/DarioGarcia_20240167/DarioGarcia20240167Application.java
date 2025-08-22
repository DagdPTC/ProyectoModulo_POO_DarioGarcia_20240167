package ProyectoModulo_DarioGarcia_20240267.DarioGarcia_20240167;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DarioGarcia20240167Application {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		SpringApplication.run(DarioGarcia20240167Application.class, args);
	}

}
