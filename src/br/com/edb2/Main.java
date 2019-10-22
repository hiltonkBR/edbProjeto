package br.com.edb2;

import br.com.edb2.app.Compressor;
import br.com.edb2.app.Extractor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        Compressor co = new Compressor("C:\\testes\\teste10.txt","C:\\testes\\teste10.edz","C:\\testes\\teste10.edt");
//        Extractor ex = new Extractor("C:\\testes\\teste10.edz","C:\\testes\\teste10.edt","C:\\testes\\teste10Restaurado.txt");

        if( args[0].equals(new String("compress"))){
            Compressor co = new Compressor(args[1],args[2], args[3]);
        }else if(args[0].equals(new String("extract"))){
            Extractor ex = new Extractor(args[1],args[2], args[3]);
        }
    }
}