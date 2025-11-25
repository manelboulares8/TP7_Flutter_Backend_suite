package org.isetn;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassMatRepository extends JpaRepository<ClassMat, Long> {
    List<ClassMat> findByClasseCodClass(Long codClass);
   //	 Optional<ClassMat> findByMatiereCodMat(Long codMat);

   	    // Optionnel si tu veux toutes les relations
     List<ClassMat> findByMatiere_CodMat(Long codMat);
}