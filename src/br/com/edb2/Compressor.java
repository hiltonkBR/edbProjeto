package br.com.edb2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Compressor {
    public static Node iniciaCompressor(String arquivo, String arquivoFinal, String dicionario) throws IOException {
        FileReader arquivoOriginal = null;
        try{
            arquivoOriginal = new FileReader(arquivo);

                BufferedReader stringDOarquivo = new BufferedReader(arquivoOriginal);
                return contaCaracteres(stringDOarquivo);

    }catch (IOException e){
        return null;
        }
    }

    public static Node contaCaracteres(BufferedReader leitor) throws IOException {
        Map<Character, Integer> treemap = new TreeMap<>();
        String texto = leitor.readLine();
        while(texto != null){
            for (char c : texto.toCharArray()) {
                Integer i = treemap.get(c);
                if (i == null) {
                    treemap.put(c, 1);
                } else {
                    treemap.put(c, i + 1);
                }
            }
            texto = leitor.readLine();
            if(texto != null){
                Integer i = treemap.get((char)260); // \n
                if (i == null) {
                    treemap.put((char)260, 1);
                } else {
                    treemap.put((char)260, i + 1);
                }
            }
        }
        treemap.put((char)261, 1); // EOF
        System.out.println(treemap);
        return tree(treemap);
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




