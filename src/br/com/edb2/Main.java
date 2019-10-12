package br.com.edb2;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
       Node no = Compressor.tree(Compressor.contaCaracteres(Compressor.iniciaCompressor("/home/raul/Área de Trabalho/Proj EDB2/testes/teste1.txt","","")));
    }
}
