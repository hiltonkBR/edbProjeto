package br.com.edb2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    public static Map<Character, Integer> contaCaracteres(String texto) {
        Map<Character, Integer> treemap = new TreeMap<>();
        for (char c : texto.toCharArray()) {
            Integer i = treemap.get(c);
            if (i == null) {
                treemap.put(c, 1);
            } else {
                treemap.put(c, i + 1);
            }
        }
        return treemap;
    }

    public static void imprimirCaracteres(Map<Character, Integer> map) {
        for (Map.Entry<Character, Integer> m : map.entrySet()) {
            System.out.println(m.getKey() + " : " + m.getValue());
        }
    }
    public static Node tree(Map<Character, Integer> map){

        Heap heap = new Heap();
        Node tree = new Node();

        for (Map.Entry<Character, Integer> m : map.entrySet()) {
            heap.insert(new Node((int)m.getKey() , m.getValue()));
        }

        if(heap.getSize() < 1){

        }else{
            while(heap.getSize() > 1){
                Node left = heap.peekRemove();
                Node right= heap.peekRemove();
                tree = new Node(left.getCount()+right.getCount(), left, right);
                heap.insert(tree);
            }
        }
        return tree;


    }

}




