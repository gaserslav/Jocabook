package Jocabook;

import Jocabook.model.db;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JocabookApplication {

	public static void main(String[] args) {

		//this one generates connectins with databases (errror would close program itself)
		db.generate_db_con();


		SpringApplication.run(JocabookApplication.class, args);
	}

}
