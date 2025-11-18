package org.isetn;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data

public class ClassMat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "codMat")
    @JsonBackReference(value = "matiere-classMats")
    private Matiere matiere;
    
    @ManyToOne
    @JoinColumn(name = "codClass")
    @JsonBackReference(value = "classe-classMats")
    private Classe classe;
    
    private Integer coef;
    private Integer chsm; // Charge horaire semestrielle
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
	public Classe getClasse() {
		return classe;
	}
	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	public Integer getCoef() {
		return coef;
	}
	public void setCoef(Integer coef) {
		this.coef = coef;
	}
	public Integer getChsm() {
		return chsm;
	}
	public void setChsm(Integer chsm) {
		this.chsm = chsm;
	}
	public ClassMat(Long id, Matiere matiere, Classe classe, Integer coef, Integer chsm) {
		super();
		this.id = id;
		this.matiere = matiere;
		this.classe = classe;
		this.coef = coef;
		this.chsm = chsm;
	}
	public ClassMat() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
}