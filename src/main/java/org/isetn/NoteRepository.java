package org.isetn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
    // Trouver les notes d'un étudiant
    List<Note> findByEtudiantId(Long etudiantId);
    
    // Trouver les notes d'une matière
    List<Note> findByMatiereCodMat(Long matiereId);
    
    // Trouver une note spécifique d'un étudiant dans une matière
    List<Note> findByEtudiantIdAndMatiereCodMat(Long etudiantId, Long matiereId);
    
    // Vérifier si une note existe déjà pour un étudiant dans une matière
    boolean existsByEtudiantIdAndMatiereCodMat(Long etudiantId, Long matiereId);
    
    //List<Note> findByEtudiantId(Long id);

}