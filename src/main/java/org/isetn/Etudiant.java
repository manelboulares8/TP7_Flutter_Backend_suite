package org.isetn;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Etudiant {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nom;
	    private String prenom;
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date dateNais;

	    @ManyToOne(fetch = FetchType.EAGER) // par défaut c’est LAZY
	    @JoinColumn(name = "formation_id", nullable = true)
	    @JsonBackReference(value = "formation-etudiants")
	    private Formation formation;

	    @ManyToOne
	    @JoinColumn(name = "classe_id")
	    @JsonBackReference(value = "classe-etudiants") // 👈 empêche rebouclage
	    private Classe classe;
	
	    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
	    private List<Note> notes;
	
	public Etudiant(Long id, String nom, String prenom, Date dateNais, Formation formation, Classe classe) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNais = dateNais;
		this.formation = formation;
		this.classe = classe;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Date getDateNais() {
		return dateNais;
	}
	public void setDateNais(Date dateNais) {
		this.dateNais = dateNais;
	}
	public Formation getFormation() {
		return formation;
	}
	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	public Classe getClasse() {
		return classe;
	}
	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	public Etudiant() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
