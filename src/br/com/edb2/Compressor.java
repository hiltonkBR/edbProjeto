package br.com.edb2;

import java.io.*;
import java.util.*;

public class Compressor {

    public Compressor (String arquivo, String arquivoFinal, String arquivoDicionario) throws IOException {

        HashMap<Character, Integer> MapaDicionario = new HashMap<> (this.contaCaracteres(arquivo));

        Heap heap = new Heap();
        heap.insert(MapaDicionario);
        Node no = heap.criaArvore();

        HashMap<Character, String> dicionario = geraDicionario(no);
        String compactado = geraComprimido(arquivo, dicionario);
        geraArquivoComprimido(geraBinario(compactado), arquivoFinal);
        geraArquivoDicionario(geraBinarioDicionario(dicionario), arquivoDicionario);
    }

    private HashMap<Character, Integer> contaCaracteres(String arquivo) throws IOException {
        HashMap<Character, Integer> contadorChar = new HashMap<>();
        FileInputStream leitor = new FileInputStream(arquivo);

        int conteudo = leitor.read();
        while (conteudo != -1) {
            Character key = (char) conteudo;
            contadorChar.computeIfPresent(key, (Character character, Integer repete) -> ++repete);
            contadorChar.putIfAbsent(key, 1);
            conteudo = leitor.read();
        }
        leitor.close();
        return contadorChar;
    }

    public static void imprimirCaracteres(Map<Character, Integer> map) {
        for (Map.Entry<Character, Integer> m : map.entrySet()) {
            System.out.println(m.getKey() + " : " + m.getValue());
        }
    }

    public static void imprimir(HashMap<Character, Integer> codeMap) {
        for (Map.Entry<Character, Integer> m : codeMap.entrySet()) {
            int a= m.getKey();
            char b = (char) a;
            System.out.println(b + " : " + m.getValue());
        }

    }

    private HashMap<Character, String> geraDicionario(Node node){
        HashMap<Character, String> mapa = new HashMap<>();
        String byteCode = new String();
        geraBinarioDicionario(mapa, node, byteCode);
        return mapa;
    }

    private void geraBinarioDicionario(HashMap<Character, String> mapa , Node no, String binario){
        if (no.getLetter() != null) {
            mapa.put(no.getLetter(), binario);
        }
        if (no.getLeft() != null) {
            String binarioAux = binario + '0';
            geraBinarioDicionario(mapa, no.getLeft(), binarioAux);
        }
        if (no.getRight() != null) {
            String binarioAux = binario + '1';
            geraBinarioDicionario(mapa, no.getRight(), binarioAux);
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
            compactado.append(dicionario.get(key));
            conteudo = buLeitor.read();
        }

        leitor.close();
        buLeitor.close();
        //compactado.append(dicionario.get((char)3));
        return compactado.toString();
    }

    private String geraBinarioDicionario(HashMap<Character, String> dicionario) {
        StringBuffer conteudo = new StringBuffer();
        Iterator interador = dicionario.entrySet().iterator();
        while(interador.hasNext()){
            Map.Entry mapElement = (Map.Entry)interador.next();
            conteudo.append(mapElement.getKey()).append(mapElement.getValue());
        }
        conteudo.append((char)1);
        return conteudo.toString();
    }

    private void geraArquivoComprimido(byte[] compactado, String arquivoFinal) throws IOException {
        FileOutputStream saida = new FileOutputStream(arquivoFinal);
        saida.write(compactado);
        saida.close();
    }

    private void geraArquivoDicionario(String dicionario, String arquivoDicionario) throws IOException {
        FileOutputStream saida = new FileOutputStream(arquivoDicionario);
        for (int i = 0; i < dicionario.length(); ++i){
            saida.write(dicionario.charAt(i));
        }
        saida.close();
    }

}