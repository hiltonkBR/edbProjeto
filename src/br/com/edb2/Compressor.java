package br.com.edb2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Compressor {

    public Compressor (String arquivo, String arquivoEDZ, String arquivoEDT) throws IOException {

        Node no = Compressor.iniciaCompressor(arquivo,",","");
        HashMap<Character, String> codeMap = geraDicionario(no);
        imprimir(codeMap);
        String teste = geraArquivoComprimido(arquivo, codeMap);
        System.out.println("\n" +teste);


    }


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
        //System.out.println(treemap);
        return tree(treemap);
    }

    public static void imprimirCaracteres(Map<Character, Integer> map) {
        for (Map.Entry<Character, Integer> m : map.entrySet()) {
            System.out.println(m.getKey() + " : " + m.getValue());
        }
    }
    public static void imprimir(HashMap<Character, String> codeMap) {
        for (Map.Entry<Character, String> m : codeMap.entrySet()) {
            int a= m.getKey();
            char b = (char) a;
            System.out.println(b + " : " + m.getValue());
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

    private HashMap<Character, String> geraDicionario(Node node){
        HashMap<Character, String> codeMap = new HashMap<>();
        String byteCode = new String();
        geraBinarioDicionario(codeMap, node, byteCode);
        return codeMap;
    }

    private void geraBinarioDicionario(HashMap<Character, String> map , Node heap, String binario){
        if (heap.getLetter() != null) {
            int tmp = heap.getLetter();

            char tmp2 = (char)tmp;
            map.put(tmp2, binario);
        }
        if (heap.getLeft() != null) {
            String byteCodAux = binario + '0';
            geraBinarioDicionario(map, heap.getLeft(), byteCodAux);
        }
        if (heap.getRight() != null) {
            String byteCodAux = binario + '1';
            geraBinarioDicionario(map, heap.getRight(), byteCodAux);
        }
    }

    private String geraArquivoComprimido(String arquivo, HashMap<Character, String> dicionario) throws IOException {
        FileReader rdr = new FileReader(arquivo);
        BufferedReader brdr = new BufferedReader(rdr);
        int contentIn = brdr.read();
        StringBuffer contenteEncriptedOut = new StringBuffer();
        while (contentIn != -1) {
            Character key = (char) contentIn;
            if(key == (char)10) {
                contenteEncriptedOut.append(dicionario.get((char) 260));
            }else{
                contenteEncriptedOut.append(dicionario.get(key));
            }
            contentIn = brdr.read();
            }
        contenteEncriptedOut.append(dicionario.get((char)261));
        rdr.close();
        brdr.close();
        return contenteEncriptedOut.toString();
    }
}




