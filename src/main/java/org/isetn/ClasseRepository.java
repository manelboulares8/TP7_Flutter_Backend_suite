package org.isetn;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;*/

@RepositoryRestResource
public interface ClasseRepository extends JpaRepository<Classe, Long> {
	
	List<Classe> findByDepartmentCodDept(Long deptId);
	//@Query("select p from Etudiant e where e.nom like :x")
	//public List<Etudiant> Chercher(@Param("x")String nom);
	//ou d'ecrire 


    Optional<Classe> findByCodClass(String codClass); // renvoie Classe, pas Etudiant
}
