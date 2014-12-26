package br.com.sdtd.helper;

import java.util.Objects;

public class Country {
    private final String code;
    
    public Country(String code) {
        this.code = code;
    }
    
    public String getCountryCode() {
        return this.code;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Country)) {
            return false;
        }
        
        return ((Country)o).getCountryCode().equals(this.code);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.code);
        return hash;
    }
}