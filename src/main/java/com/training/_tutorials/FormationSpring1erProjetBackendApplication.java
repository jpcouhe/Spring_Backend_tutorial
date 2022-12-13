package com.training._tutorials;

import com.training._tutorials.entity.Tutorial;
import com.training._tutorials.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FormationSpring1erProjetBackendApplication implements CommandLineRunner {
    @Autowired
    private TutorialRepository tutorialRepository;

    public static void main(String[] args) {
        SpringApplication.run(FormationSpring1erProjetBackendApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
            List<Tutorial> tutos = new ArrayList<>();

            tutos.add(new Tutorial("Tuto #1", "Description #1", true));
            tutos.add(new Tutorial("Tuto #2", "Description #2", false));
            tutos.add(new Tutorial("Tuto #3", "Description #3", true));
            tutorialRepository.saveAll(tutos);
    }
}