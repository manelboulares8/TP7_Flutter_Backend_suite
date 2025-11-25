package org.isetn;

import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtudiantDTO {
    private String nce;
    private String nom;
    private String prenom;
    private Date dateNais;
    private String lieuNais;
    private Long codClass;
	public String getNce() {
		return nce;
	}
	public void setNce(String nce) {
		this.nce = nce;
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
	public String getLieuNais() {
		return lieuNais;
	}
	public void setLieuNais(String lieuNais) {
		this.lieuNais = lieuNais;
	}
	public Long getCodClass() {
		return codClass;
	}
	public void setCodClass(Long codClass) {
		this.codClass = codClass;
	}
	public EtudiantDTO(String nce, String nom, String prenom, Date dateNais, String lieuNais, Long codClass) {
		super();
		this.nce = nce;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNais = dateNais;
		this.lieuNais = lieuNais;
		this.codClass = codClass;
	}
	public EtudiantDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
    
}
