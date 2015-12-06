package com.example.shdemo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name = "lekarstwo.unsold", query = "Select c from Lekarstwo c where c.sold = false")
})
public class Lekarstwo {

	private Long id;
	private String make;
	private String model;
	private Boolean sold = false;

	//private List<Osoba> osoba = new List<Osoba>();
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}


}
