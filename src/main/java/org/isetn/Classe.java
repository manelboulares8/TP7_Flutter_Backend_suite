package org.isetn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classe {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codClass;

    private String nomClass;
    private int nbreEtud;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "classe-etudiants") // 👈 empêche la boucle
    private List<Etudiant> etudiants;
	
	public Classe(Long codClass, String nomClass, int nbreEtud, List<Etudiant> etudiants) {
		super();
		this.codClass = codClass;
		this.nomClass = nomClass;
		this.nbreEtud = nbreEtud;
		this.etudiants = etudiants;
	}
	public Classe() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getCodClass() {
		return codClass;
	}
	public void setCodClass(Long codClass) {
		this.codClass = codClass;
	}
	public String getNomClass() {
		return nomClass;
	}
	public void setNomClass(String nomClass) {
		this.nomClass = nomClass;
	}
	public int getNbreEtud() {
		return nbreEtud;
	}
	public void setNbreEtud(int nbreEtud) {
		this.nbreEtud = nbreEtud;
	}
	public Collection<Etudiant> getEtudiants() {
		return etudiants;
	}
	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	} 
	
	
	
	
}
