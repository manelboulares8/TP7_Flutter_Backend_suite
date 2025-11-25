package org.isetn;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data

public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codMat;
    private String intMat;
    private String description;
    
    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "matiere-classMats")
    private List<ClassMat> classMats;
    
    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "matiere-absences")
    private List<Absence> absences;

	public Long getCodMat() {
		return codMat;
	}

	public void setCodMat(Long codMat) {
		this.codMat = codMat;
	}

	public String getIntMat() {
		return intMat;
	}

	public void setIntMat(String intMat) {
		this.intMat = intMat;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ClassMat> getClassMats() {
		return classMats;
	}

	public void setClassMats(List<ClassMat> classMats) {
		this.classMats = classMats;
	}

	public List<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}

	public Matiere(Long codMat, String intMat, String description, List<ClassMat> classMats, List<Absence> absences) {
		super();
		this.codMat = codMat;
		this.intMat = intMat;
		this.description = description;
		this.classMats = classMats;
		this.absences = absences;
	}

	public Matiere() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
    
}