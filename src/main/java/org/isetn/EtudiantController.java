package org.isetn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private ClasseRepository classeRepository;
    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

   /* @GetMapping("/classe/{codClass}")
    public List<Etudiant> getEtudiantsByClasse(@PathVariable Long codClass) {
        return etudiantRepository.findByClasseCodClass(codClass);
        
    }*/
 
    @GetMapping("/classe/{codClass}")
    public List<Etudiant> getEtudiantsByClasse(@PathVariable Long codClass) {
        List<Etudiant> etudiants = etudiantRepository.findByClasseCodClass(codClass);
        // si nécessaire, forcer le fetch de formation et classe pour éviter LazyInitializationException
        etudiants.forEach(e -> {
            if (e.getFormation() != null) 
            	e.getFormation().getNom();
        });
        return etudiants;
    }
   /* @PostMapping
    public Etudiant addEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }*/
    
    @PostMapping
    public Etudiant addEtudiant(@RequestBody EtudiantDTO dto) {
        // convertir codClass reçu en Long (assurez-vous que le front envoie un Long)
        Long codClass = dto.getCodClass();

        Classe classe = classeRepository.findById(codClass)
                .orElseThrow(() -> new RuntimeException("Classe non trouvée"));

        Etudiant etudiant = new Etudiant();
        etudiant.setNom(dto.getNom());
        etudiant.setPrenom(dto.getPrenom());
        etudiant.setDateNais(dto.getDateNais());
        etudiant.setClasse(classe);

        return etudiantRepository.save(etudiant);
    }

    @PutMapping("/{id}")
    public Etudiant updateEtudiant(@PathVariable Long id, @RequestBody EtudiantDTO dto) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        Classe classe = classeRepository.findById(dto.getCodClass())
                .orElseThrow(() -> new RuntimeException("Classe non trouvée"));

        etudiant.setNom(dto.getNom());
        etudiant.setPrenom(dto.getPrenom());
        etudiant.setDateNais(dto.getDateNais());
        etudiant.setClasse(classe);

        return etudiantRepository.save(etudiant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        if (!etudiantRepository.existsById(id)) return ResponseEntity.notFound().build();
        etudiantRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(
    	    value = "/{id}",
    	    consumes = "application/json",
    	    produces = "application/json"
    	)
    public Etudiant updateStudent(@PathVariable Long id,
                                  @RequestBody Etudiant studentDetails) {

        Etudiant student = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Update basic fields
        student.setNom(studentDetails.getNom());
        student.setPrenom(studentDetails.getPrenom());
        student.setDateNais(studentDetails.getDateNais());

        // Update class
        if (studentDetails.getClasse() != null) {
            Classe classe = classeRepository.findById(studentDetails.getClasse().getCodClass())
                    .orElseThrow(() -> new RuntimeException("Classe not found"));

            student.setClasse(classe);
        }

        return etudiantRepository.save(student);
    }


}
