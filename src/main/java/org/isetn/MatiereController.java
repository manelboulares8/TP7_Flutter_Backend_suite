package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/matieres")
@CrossOrigin("*")
public class MatiereController {

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private ClassMatRepository classMatRepository;
    
    

    @Autowired
    private ClasseRepository classeRepository;

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
   /* @PostMapping
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
*/
    // PUT /matieres/{id} - modifier une matière
  /*  @PutMapping("/{id}")
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
    }*/

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
    
    
    
    @PostMapping
    public ResponseEntity<Object> addMatiere(@RequestBody Map<String, Object> requestData) {
        try {
            String intMat = (String) requestData.get("intMat");
            String description = (String) requestData.get("description");
            Number classeIdNumber = (Number) requestData.get("classeId"); // ID de la classe

            if (classeIdNumber == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "⚠️ Veuillez préciser la classe pour la matière"));
            }

            Long classeId = classeIdNumber.longValue();
            Optional<Classe> classeOpt = classeRepository.findById(classeId);
            if (!classeOpt.isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "⚠️ Classe non trouvée"));
            }

            Matiere matiere = new Matiere();
            matiere.setIntMat(intMat);
            matiere.setDescription(description);

            Matiere savedMatiere = matiereRepository.save(matiere);

            // Créer la relation avec la classe
            ClassMat classMat = new ClassMat();
            classMat.setClasse(classeOpt.get());
            classMat.setMatiere(savedMatiere);
            classMatRepository.save(classMat);

            return ResponseEntity.ok(savedMatiere);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Erreur lors de l'ajout de la matière"));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMatiere(@PathVariable Long id, @RequestBody Map<String, Object> requestData) {
        try {
            Optional<Matiere> optionalMatiere = matiereRepository.findById(id);
            if (!optionalMatiere.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Matiere matiere = optionalMatiere.get();

            String intMat = (String) requestData.get("intMat");
            String description = (String) requestData.get("description");
            Number classeIdNumber = (Number) requestData.get("classeId");

            matiere.setIntMat(intMat);
            matiere.setDescription(description);

            Matiere updatedMatiere = matiereRepository.save(matiere);

            if (classeIdNumber != null) {
                Long classeId = classeIdNumber.longValue();
                Optional<Classe> classeOpt = classeRepository.findById(classeId);
                if (classeOpt.isPresent()) {
                    // Récupérer la liste des ClassMat pour cette matière
                    List<ClassMat> classMats = classMatRepository.findByMatiere_CodMat(id);
                    ClassMat classMat;

                    if (classMats.isEmpty()) {
                        classMat = new ClassMat();
                    } else {
                        classMat = classMats.get(0); // prend le premier ClassMat existant
                    }

                    classMat.setClasse(classeOpt.get());
                    classMat.setMatiere(updatedMatiere);
                    classMatRepository.save(classMat);
                }
            }

            return ResponseEntity.ok(updatedMatiere);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Erreur lors de la modification de la matière"));
        }
    }
}