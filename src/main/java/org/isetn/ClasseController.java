package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes")
@CrossOrigin("*")
public class ClasseController {

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // GET /classes - lister toutes les classes avec leurs départements
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

    // POST /classes - ajouter une classe avec département

    // POST /classes - ajouter une classe avec département
    
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Classe> addClasse(@RequestBody Classe classe) {
        try {
            System.out.println("📥 Données reçues: " + classe.getNomClass() + ", " + classe.getNbreEtud());
            
            // Vérifier si un département est associé
            if (classe.getDepartment() != null && classe.getDepartment().getCodDept() != null) {
                System.out.println("🔍 Recherche département ID: " + classe.getDepartment().getCodDept());
                
                Optional<Department> department = departmentRepository.findById(classe.getDepartment().getCodDept());
                if (department.isPresent()) {
                    classe.setDepartment(department.get());
                    System.out.println("✅ Département trouvé: " + department.get().getNomDept());
                } else {
                    System.out.println("❌ Département non trouvé");
                    return ResponseEntity.badRequest().build();
                }
            } else {
                System.out.println("⚠️  Aucun département associé");
            }

            Classe savedClasse = classeRepository.save(classe);
            System.out.println("💾 Classe sauvegardée: " + savedClasse.getCodClass());
            return ResponseEntity.ok(savedClasse);
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /classes/{id} - modifier une classe
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Classe> updateClasse(@PathVariable Long id, @RequestBody Classe classeDetails) {
        try {
            Optional<Classe> optionalClasse = classeRepository.findById(id);
            if (!optionalClasse.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Classe classe = optionalClasse.get();
            classe.setNomClass(classeDetails.getNomClass());
            classe.setNbreEtud(classeDetails.getNbreEtud());

            // Mettre à jour le département si fourni
            if (classeDetails.getDepartment() != null && classeDetails.getDepartment().getCodDept() != null) {
                Optional<Department> department = departmentRepository.findById(classeDetails.getDepartment().getCodDept());
                if (department.isPresent()) {
                    classe.setDepartment(department.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }

            Classe updatedClasse = classeRepository.save(classe);
            return ResponseEntity.ok(updatedClasse);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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

    // GET /classes/department/{departmentId} - classes par département
    @GetMapping("/department/{departmentId}")
    public List<Classe> getClassesByDepartment(@PathVariable Long departmentId) {
        return classeRepository.findByDepartmentCodDept(departmentId);
    }
}