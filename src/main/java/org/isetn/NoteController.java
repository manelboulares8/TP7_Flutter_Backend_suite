package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin("*")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    // GET /api/notes - lister toutes les notes
    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // GET /api/notes/{id} - récupérer une note par id
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /api/notes/etudiant/{etudiantId} - notes par étudiant
    @GetMapping("/etudiant/{etudiantId}")
    public List<Note> getNotesByEtudiant(@PathVariable Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId);
    }

    // GET /api/notes/matiere/{matiereId} - notes par matière
    @GetMapping("/matiere/{matiereId}")
    public List<Note> getNotesByMatiere(@PathVariable Long matiereId) {
        return noteRepository.findByMatiereCodMat(matiereId);
    }

    // POST /api/notes - ajouter une note
    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Map<String, Object> requestData) {
        try {
            System.out.println("📥 Données note reçues: " + requestData);
            
            Map<String, Object> etudiantMap = (Map<String, Object>) requestData.get("etudiant");
            Map<String, Object> matiereMap = (Map<String, Object>) requestData.get("matiere");
            
            Long etudiantId = ((Number) etudiantMap.get("id")).longValue();
            Long matiereId = ((Number) matiereMap.get("codMat")).longValue();
            Double valeurNote = ((Number) requestData.get("valeurNote")).doubleValue();

            // Vérifier l'étudiant
            Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
            if (!etudiantOpt.isPresent()) {
                System.out.println("❌ Étudiant non trouvé: " + etudiantId);
                return ResponseEntity.badRequest().build();
            }

            // Vérifier la matière
            Optional<Matiere> matiereOpt = matiereRepository.findById(matiereId);
            if (!matiereOpt.isPresent()) {
                System.out.println("❌ Matière non trouvée: " + matiereId);
                return ResponseEntity.badRequest().build();
            }

            // Vérifier si une note existe déjà
            if (noteRepository.existsByEtudiantIdAndMatiereCodMat(etudiantId, matiereId)) {
                System.out.println("⚠️  Note déjà existante pour cet étudiant dans cette matière");
                return ResponseEntity.badRequest().build();
            }

            // Créer la note
            Note note = new Note();
            note.setEtudiant(etudiantOpt.get());
            note.setMatiere(matiereOpt.get());
            note.setValeurNote(valeurNote);

            Note savedNote = noteRepository.save(note);
            System.out.println("💾 Note sauvegardée avec ID: " + savedNote.getId());
            return ResponseEntity.ok(savedNote);
            
        } catch (Exception e) {
            System.out.println("❌ Erreur sauvegarde note: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/notes/{id} - modifier une note
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Map<String, Object> requestData) {
        try {
            Optional<Note> optionalNote = noteRepository.findById(id);
            if (!optionalNote.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Note note = optionalNote.get();
            Double valeurNote = ((Number) requestData.get("valeurNote")).doubleValue();
            note.setValeurNote(valeurNote);

            Note updatedNote = noteRepository.save(note);
            return ResponseEntity.ok(updatedNote);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/notes/{id} - supprimer une note
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        try {
            if (!noteRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            noteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/notes/etudiant/{etudiantId}/matiere/{matiereId} - note spécifique
    @GetMapping("/etudiant/{etudiantId}/matiere/{matiereId}")
    public List<Note> getNoteByEtudiantAndMatiere(@PathVariable Long etudiantId, @PathVariable Long matiereId) {
        return noteRepository.findByEtudiantIdAndMatiereCodMat(etudiantId, matiereId);
    }
}