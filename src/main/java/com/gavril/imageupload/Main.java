package com.gavril.imageupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

		// GET list of files in directory
		String[] pathnames;
		File f = new File("src/main/resources/images/");
		pathnames = f.list();

		for (String pathname : pathnames) {
			System.out.println(pathname);
		}
	}
}
