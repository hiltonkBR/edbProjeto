package br.com.edb2;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Compressor.imprimirCaracteres(Compressor.contaCaracteres(Compressor.iniciaCompressor("C:\\testes\\teste1.txt","","")));
    }
}
