package org.isetn;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data

public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "codMat")
    @JsonBackReference(value = "matiere-absences")
    private Matiere matiere;
    
    @ManyToOne
    @JoinColumn(name = "nce")
    @JsonBackReference(value = "etudiant-absences")
    private Etudiant etudiant;
    
    private LocalDate dateA;
    
    private Integer nha; // Nombre d'heures d'absences

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public LocalDate getDateA() {
		return dateA;
	}

	public void setDateA(LocalDate dateA) {
		this.dateA = dateA;
	}

	public Integer getNha() {
		return nha;
	}

	public void setNha(Integer nha) {
		this.nha = nha;
	}

	public Absence(Long id, Matiere matiere, Etudiant etudiant, LocalDate dateA, Integer nha) {
		super();
		this.id = id;
		this.matiere = matiere;
		this.etudiant = etudiant;
		this.dateA = dateA;
		this.nha = nha;
	}

	public Absence() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
    
}