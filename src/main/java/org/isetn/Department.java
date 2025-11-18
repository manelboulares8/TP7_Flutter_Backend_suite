package org.isetn;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codDept;
    private String nomDept;
    
    @OneToMany (mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Classe> classes;

	public Long getCodDept() {
		return codDept;
	}

	public void setCodDept(Long codDept) {
		this.codDept = codDept;
	}

	public String getNomDept() {
		return nomDept;
	}

	public void setNomDept(String nomDept) {
		this.nomDept = nomDept;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(Long codDept, String nomDept, List<Classe> classes) {
		super();
		this.codDept = codDept;
		this.nomDept = nomDept;
		this.classes = classes;
	}
    
	 public Department(Long codDept, String nomDept) {
	        this.codDept = codDept;
	        this.nomDept = nomDept;
	    }
    
    
    
    
}