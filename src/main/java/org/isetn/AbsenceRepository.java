package org.isetn;
//AbsenceRepository.java

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
 List<Absence> findByEtudiantId(Long etudiantId);
 List<Absence> findByEtudiantIdAndMatiereCodMat(Long etudiantId, Long matiereId);
 
 @Modifying
 @Query("DELETE FROM Absence a WHERE a.etudiant.id = :etudiantId AND a.matiere.codMat = :matiereId AND a.dateA = :dateA")
 void deleteByEtudiantIdAndMatiereCodMatAndDateA(@Param("etudiantId") Long etudiantId, 
                                                @Param("matiereId") Long matiereId, 
                                                @Param("dateA") LocalDate dateA);
}