package org.isetn;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/moyenne")
@RequiredArgsConstructor
public class MoyenneController {

    private final NoteRepository noteRepository;

    @GetMapping("/student/{id}")
    public double moyenneEtudiant(@PathVariable long id) {

        List<Note> notes = noteRepository.findByEtudiantId(id);

        if (notes.isEmpty()) return 0;

        double sum = notes.stream()
                .mapToDouble(n -> n.getValeurNote() != null ? n.getValeurNote() : 0.0)
                .sum();

        return sum / notes.size();
    }
    
    public MoyenneController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

}
