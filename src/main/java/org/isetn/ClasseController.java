package org.isetn;

import org.isetn.Classe;
import org.isetn.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes")
public class ClasseController {

    @Autowired
    private ClasseRepository classeRepository;

    // GET /classes - lister toutes les classes
    @GetMapping
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    // GET /classes/{id} - récupérer une classe par id
    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable Long id) {
        Optional<Classe> classe = classeRepository.findById(id);
        return classe.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /classes - ajouter une classe
    @PostMapping
    public Classe addClasse(@RequestBody Classe classe) {
        return classeRepository.save(classe);
    }

    // PUT /classes/{id} - modifier une classe
    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable Long id, @RequestBody Classe classeDetails) {
        Optional<Classe> optionalClasse = classeRepository.findById(id);
        if (!optionalClasse.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Classe classe = optionalClasse.get();
        classe.setNomClass(classeDetails.getNomClass());
        classe.setNbreEtud(classeDetails.getNbreEtud());
        Classe updatedClasse = classeRepository.save(classe);
        return ResponseEntity.ok(updatedClasse);
    }

    // DELETE /classes/{id} - supprimer une classe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        if (!classeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        classeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
