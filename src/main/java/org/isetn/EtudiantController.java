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
    @PostMapping
    public Etudiant addEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        if (!etudiantRepository.existsById(id)) return ResponseEntity.notFound().build();
        etudiantRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
