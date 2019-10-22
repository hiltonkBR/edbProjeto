package br.com.edb2.utils;

import br.com.edb2.arvore.Node;

public class AuxBinario {
    private static String tableCode = "";

    public static String[] ABinario(Node no, boolean chave) {
        if( chave == true){
            tableCode = ABinario(no, "");
            return tableCode.split("=");
        }else{
            tableCode += "300"+"=0";
            return tableCode.split("=");
        }
    }

    private static String ABinario(Node no, String codigo) {
        if (no.getLeft() == no.getRight()) {
            tableCode+= no.getValue().getCharacter() + "=" + codigo + "=" ;
            return tableCode;
        } else {
            ABinario(no.getRight(), codigo + Integer.toString(no.getRight().getBit()));
            ABinario(no.getLeft(), codigo + Integer.toString(no.getLeft().getBit()));
        }
        return tableCode;
    }
}
