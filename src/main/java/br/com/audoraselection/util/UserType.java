package br.com.audoraselection.util;

import lombok.Getter;

@Getter
public enum UserType {
	
	 CLIENTE("C"),
	 ADMINISTRADOR("A");
	
    private String type;
    
    private UserType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
