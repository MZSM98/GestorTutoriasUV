package com.gtuv.modelo.pojo;

public class Tutor extends Usuario {
    
    private int noTutorados;

    public Tutor() {
        super(); 
    }

    public int getNoTutorados() {
        return noTutorados;
    }

    public void setNoTutorados(int noTutorados) {
        this.noTutorados = noTutorados;
    }
}