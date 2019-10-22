package br.com.edb2.utils;

public class Char {
    private Integer character;
    private Integer quantitie;

    public Char(Integer character, Integer quantitie) {
        this.character = character;
        this.quantitie = quantitie;
    }

    public Char(Integer quantitie) {
        this.character = null;
        this.quantitie = quantitie;
    }

    public Integer getCharacter() {
        return character;
    }

    public Integer getQuantitie() {
        return quantitie;
    }

}
