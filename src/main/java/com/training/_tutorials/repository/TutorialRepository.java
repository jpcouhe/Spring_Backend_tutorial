package com.training._tutorials.repository;

import com.training._tutorials.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


//Model et Type de la clé primaire pour extend
//équivalent de la DAO
//Etends différentes méthodes
//Pattern DAO et repository (orienté répertoire)
//Pas besoin de connection, pas de try and catch
@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {


    @Query("SELECT t FROM Tutorial t WHERE t.published=:published")
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);


}
