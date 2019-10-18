package br.com.edb2;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //Compressor co = new Compressor("C:\\testes\\teste3.txt","C:\\testes\\teste3.edz","C:\\testes\\teste3.edt");
        Extractor ex = new Extractor("C:\\testes\\teste3.edz","C:\\testes\\teste3.edt","C:\\testes\\teste3Restaurado.txt");

    }
}