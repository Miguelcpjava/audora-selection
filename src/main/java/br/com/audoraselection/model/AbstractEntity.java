package br.com.audoraselection.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8403896832103955058L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractEntity) {
			if (obj.getClass() == this.getClass()) {
				if (getId() != null && ((AbstractEntity) obj).getId().equals(getId())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + "id=" + id + '}';
	}
}
