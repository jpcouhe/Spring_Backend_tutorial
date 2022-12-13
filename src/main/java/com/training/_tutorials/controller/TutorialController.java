package com.training._tutorials.controller;


import ch.qos.logback.core.encoder.EchoEncoder;
import com.training._tutorials.controller.model.TutorialDto;
import com.training._tutorials.entity.Tutorial;
import com.training._tutorials.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api")




public class TutorialController {


    @Autowired
    private TutorialRepository tutorialRepository;



    @GetMapping("/populate")
    public ResponseEntity<?> populateDatabase(){
        List<Tutorial> tutos = new ArrayList<>();

        tutos.add(new Tutorial("Tuto #1", "Description #1", true));
        tutos.add(new Tutorial("Tuto #2", "Description #2", false));
        tutos.add(new Tutorial("Tuto #3", "Description #3", true));
        tutorialRepository.saveAll(tutos);
        return ResponseEntity.noContent().build();
    };


    // /api/tutorials?title= Titre du tuto recherché
    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(value = "title", required = false) String title){


        List<Tutorial> tutos = new ArrayList<>();
            if(title == null) {
                tutorialRepository.findAll().forEach(tutorial -> tutos.add(tutorial));
            }else{
                tutorialRepository.findByTitleContaining(title).forEach(t -> tutos.add(t));
            }

            if(tutos.isEmpty()){
                //Http 204 : sans contenu
             /*   return ResponseEntity.status(HttpStatus.NO_CONTENT).build();*/
                return ResponseEntity.noContent().build();
            } else{
                //http 200 avec les tutos
                return ResponseEntity.ok().body(tutos);
            }
    }


    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialByid(@PathVariable("id") long id){
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if(tutorialData.isPresent()){
            return ResponseEntity.ok().body(tutorialData.get());
           /* return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);*/
        }else{
            return ResponseEntity.notFound().build();
         /*   return new ResponseEntity<>(HttpStatus.NOT_FOUND);*/
        }
    };


    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished(){
        try{
            List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

            if(tutorials.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(tutorials);

        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping("tutorials")
    //dto objet à part entiere
    public ResponseEntity<Tutorial> createTutorial(@RequestBody TutorialDto dto) {
        try {
            //Convention _?
            Tutorial newTutorial = new Tutorial(dto.getTitle(), dto.getDescription(), dto.isPublished());
            Tutorial _tutorial = tutorialRepository.save(newTutorial);
            //A reprendre?
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);

        }catch(Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if(tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle("Tuto #4 modifié");
            _tutorial.setDescription("description #4 modifié");
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try{
            tutorialRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials(){
        try{
            tutorialRepository.deleteAll();
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
