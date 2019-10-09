package br.com.edb2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Compressor {
    public static String iniciaCompressor(String arquivo, String arquivoFinal, String dicionario) throws IOException {
        File arquivoOriginal = new File(arquivo);

        if (arquivoOriginal.exists() && arquivoOriginal.isFile()) {
            //Caso exista o arquivo
            Path caminho = Paths.get(arquivo);
            String stringDOarquivo = Files.readString(caminho);
            //System.out.print(stringDOarquivo);
            return stringDOarquivo;
        } else {
                //Caso não exista
            return "Erro: arquivo informado não existe!\n";
        }
    }

    public static Map<String, Integer> contaCaracteres(String texto) {
        Map<String, Integer> treemap = new TreeMap<>();
        for(char c: texto.toCharArray()) {
            Integer i = treemap.get(String.valueOf(c));
            if (i == null) {
                treemap.put(String.valueOf(c), 1);
            }else {
                treemap.put(String.valueOf(c), i+1);
            }
        }
        return treemap;
    }

    public static void imprimirCaracteres(Map<String,Integer> map) {
        for(Map.Entry<String, Integer> m: map.entrySet()) {
            System.out.println(m.getKey() + " : " + m.getValue());
        }
    }
}


