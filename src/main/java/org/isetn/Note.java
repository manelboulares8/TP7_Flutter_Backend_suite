package org.isetn;

import javax.persistence.*;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "nce")
    private Etudiant etudiant;
    
    @ManyToOne
    @JoinColumn(name = "codMat")
    private Matiere matiere;
    
    private Double valeurNote;

    // Constructeurs
    public Note() {}

    public Note(Long id, Etudiant etudiant, Matiere matiere, Double valeurNote) {
        this.id = id;
        this.etudiant = etudiant;
        this.matiere = matiere;
        this.valeurNote = valeurNote;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    
    public Matiere getMatiere() { return matiere; }
    public void setMatiere(Matiere matiere) { this.matiere = matiere; }
    
    public Double getValeurNote() { return valeurNote; }
    public void setValeurNote(Double valeurNote) { this.valeurNote = valeurNote; }
}