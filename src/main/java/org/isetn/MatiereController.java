package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matieres")
@CrossOrigin("*")
public class MatiereController {

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private ClassMatRepository classMatRepository;

    // GET /matieres - lister toutes les matières
    @GetMapping
    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    // GET /matieres/{id} - récupérer une matière par id
    @GetMapping("/{id}")
    public ResponseEntity<Matiere> getMatiereById(@PathVariable Long id) {
        Optional<Matiere> matiere = matiereRepository.findById(id);
        return matiere.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /matieres - ajouter une matière
    @PostMapping
    public ResponseEntity<Matiere> addMatiere(@RequestBody Matiere matiere) {
        try {
            System.out.println("📥 Ajout matière: " + matiere.getIntMat());
            Matiere savedMatiere = matiereRepository.save(matiere);
            return ResponseEntity.ok(savedMatiere);
        } catch (Exception e) {
            System.out.println("❌ Erreur ajout matière: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /matieres/{id} - modifier une matière
    @PutMapping("/{id}")
    public ResponseEntity<Matiere> updateMatiere(@PathVariable Long id, @RequestBody Matiere matiereDetails) {
        try {
            Optional<Matiere> optionalMatiere = matiereRepository.findById(id);
            if (!optionalMatiere.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Matiere matiere = optionalMatiere.get();
            matiere.setIntMat(matiereDetails.getIntMat());
            matiere.setDescription(matiereDetails.getDescription());

            Matiere updatedMatiere = matiereRepository.save(matiere);
            return ResponseEntity.ok(updatedMatiere);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /matieres/{id} - supprimer une matière
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        try {
            if (!matiereRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            matiereRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /matieres/classe/{classId} - matières par classe
    @GetMapping("/classe/{classId}")
    public List<Matiere> getMatieresByClass(@PathVariable Long classId) {
        return matiereRepository.findByClassMatsClasseCodClass(classId);
    }
}