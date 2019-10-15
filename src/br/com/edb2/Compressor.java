package br.com.edb2;

import java.io.*;
import java.util.*;

public class Compressor {

    public Compressor (String arquivo, String arquivoFinal, String dicionario) throws IOException {

        Node no = Compressor.iniciaCompressor(arquivo,",","");
        HashMap<Character, String> MapaDicionario = geraDicionario(no);
        imprimir(MapaDicionario);
        String compactado = geraComprimido(arquivo, MapaDicionario);
        geraArquivoComprimido(geraBinario(compactado), arquivoFinal);
        geraArquivoDicionario(geraBinarioDicionario(MapaDicionario), dicionario);
        System.out.println("\n" +compactado);

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
            String binarioAux = binario + '0';
            geraBinarioDicionario(map, heap.getLeft(), binarioAux);
        }
        if (heap.getRight() != null) {
            String binarioAux = binario + '1';
            geraBinarioDicionario(map, heap.getRight(), binarioAux);
        }
    }

    private byte[] geraBinario(String compactado) {
        BitSet bit = new BitSet();
        for (int i = 0; i < compactado.length(); ++i){
            if(compactado.charAt(i) == '1'){
                bit.set(i);
            }
        }
        return bit.toByteArray();
    }

    private String geraComprimido(String arquivo, HashMap<Character, String> dicionario) throws IOException {
        FileReader leitor = new FileReader(arquivo);
        BufferedReader buLeitor = new BufferedReader(leitor);
        int conteudo = buLeitor.read();
        StringBuffer compactado = new StringBuffer();
        while (conteudo != -1) {
            Character key = (char) conteudo;
            if(key == (char)10) {
                compactado.append(dicionario.get((char) 260));
            }else{
                compactado.append(dicionario.get(key));
            }
            conteudo = buLeitor.read();
            }
        compactado.append(dicionario.get((char)261));
        leitor.close();
        buLeitor.close();
        return compactado.toString();
    }

    private String geraBinarioDicionario(HashMap<Character, String> dicionario) {
        StringBuffer conteudo = new StringBuffer();
        Iterator interador = dicionario.entrySet().iterator();
        while(interador.hasNext()){
            Map.Entry mapElement = (Map.Entry)interador.next();
            conteudo.append(mapElement.getKey()).append(mapElement.getValue());
            if (interador.hasNext())
                conteudo.append('\n');
        }
        return conteudo.toString();
    }

    private void geraArquivoComprimido(byte[] compactado, String arquivoFinal) throws IOException {
        FileOutputStream saida = new FileOutputStream(arquivoFinal);
        saida.write(compactado);
        saida.close();
    }

    private void geraArquivoDicionario(String tableCode, String fileEDT) throws IOException {
        FileOutputStream fileOutEDT = new FileOutputStream(fileEDT);
        for (int i = 0; i < tableCode.length(); ++i){
            fileOutEDT.write(tableCode.charAt(i));
        }
        fileOutEDT.close();
    }

}




