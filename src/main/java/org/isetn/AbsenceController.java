// AbsenceController.java
package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/absences")
@CrossOrigin("*")
public class AbsenceController {
    
    @Autowired
    private AbsenceRepository absenceRepository;
    
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Autowired
    private MatiereRepository matiereRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private ClasseRepository classeRepository;
    
    @Autowired
    private ClassMatRepository classMatRepository;
    
    // Get all departments
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    // Get classes by department
    @GetMapping("/departments/{deptId}/classes")
    public List<Classe> getClassesByDepartment(@PathVariable Long deptId) {
        return classeRepository.findByDepartmentCodDept(deptId);
    }
    
    // Get students by class
    @GetMapping("/classes/{classId}/students")
    public List<Etudiant> getStudentsByClass(@PathVariable Long classId) {
        return etudiantRepository.findByClasseCodClass(classId);
    }
    
    // Get matieres by class
    @GetMapping("/classes/{classId}/matieres")
    public List<Matiere> getMatieresByClass(@PathVariable Long classId) {
        return matiereRepository.findByClassMatsClasseCodClass(classId);
    }
    
    // Get absences by student
    @GetMapping("/students/{studentId}/absences")
    public List<Absence> getAbsencesByStudent(@PathVariable Long studentId) {
        return absenceRepository.findByEtudiantId(studentId);
    }
    

    // Add absence - Version avec Map pour éviter les problèmes JSON
    @PostMapping
    public ResponseEntity<Absence> addAbsence(@RequestBody Map<String, Object> requestData) {
        try {
            System.out.println("📥 Données brutes reçues: " + requestData);
            
            // Extraire les données du Map
            Map<String, Object> etudiantMap = (Map<String, Object>) requestData.get("etudiant");
            Map<String, Object> matiereMap = (Map<String, Object>) requestData.get("matiere");
            
            Long etudiantId = ((Number) etudiantMap.get("id")).longValue();
            Long matiereId = ((Number) matiereMap.get("codMat")).longValue();
            String dateStr = (String) requestData.get("dateA");
            Integer nha = (Integer) requestData.get("nha");
            
            System.out.println("🔍 Extraction:");
            System.out.println("  - Étudiant ID: " + etudiantId);
            System.out.println("  - Matière ID: " + matiereId);
            System.out.println("  - Date: " + dateStr);
            System.out.println("  - Heures: " + nha);

            // Vérifier et associer l'étudiant
            Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
            if (!etudiantOpt.isPresent()) {
                System.out.println("❌ Étudiant non trouvé: " + etudiantId);
                return ResponseEntity.badRequest().build();
            }

            // Vérifier et associer la matière
            Optional<Matiere> matiereOpt = matiereRepository.findById(matiereId);
            if (!matiereOpt.isPresent()) {
                System.out.println("❌ Matière non trouvée: " + matiereId);
                return ResponseEntity.badRequest().build();
            }

            // Créer l'absence
            Absence absence = new Absence();
            absence.setEtudiant(etudiantOpt.get());
            absence.setMatiere(matiereOpt.get());
            absence.setDateA(LocalDate.parse(dateStr));
            absence.setNha(nha);

            Absence savedAbsence = absenceRepository.save(absence);
            System.out.println("💾 Absence sauvegardée avec ID: " + savedAbsence.getId());
            return ResponseEntity.ok(savedAbsence);
            
        } catch (Exception e) {
            System.out.println("❌ Erreur sauvegarde absence: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    // Update absence
    @PutMapping("/{id}")
    public Absence updateAbsence(@PathVariable Long id, @RequestBody Absence absence) {
        absence.setId(id);
        return absenceRepository.save(absence);
    }
    
    // Delete absence
    @DeleteMapping("/{id}")
    public void deleteAbsence(@PathVariable Long id) {
        absenceRepository.deleteById(id);
    }
    
    // Delete absence by student, matiere and date
    @DeleteMapping("/students/{studentId}/matieres/{matiereId}/date/{date}")
    public void deleteAbsence(@PathVariable Long studentId, 
                             @PathVariable Long matiereId, 
                             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        absenceRepository.deleteByEtudiantIdAndMatiereCodMatAndDateA(studentId, matiereId, date);
    }
}