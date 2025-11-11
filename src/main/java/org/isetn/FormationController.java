package org.isetn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/formations")
public class FormationController {

    @Autowired
    private FormationRepository formationRepository;

    @GetMapping
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @PostMapping
    public Formation addFormation(@RequestBody Formation formation) {
        return formationRepository.save(formation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formationDetails) {
        Optional<Formation> optionalFormation = formationRepository.findById(id);
        if (!optionalFormation.isPresent()) return ResponseEntity.notFound().build();
        Formation formation = optionalFormation.get();
        formation.setNom(formationDetails.getNom());
        formation.setDuree(formationDetails.getDuree());
        return ResponseEntity.ok(formationRepository.save(formation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        if (!formationRepository.existsById(id)) return ResponseEntity.notFound().build();
        formationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
