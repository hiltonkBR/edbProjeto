package br.com.edb2.app;

import br.com.edb2.arvore.Heap;
import br.com.edb2.arvore.Node;

import java.io.*;
import java.util.*;

import static br.com.edb2.utils.AuxBinario.ABinario;

public class Compressor {
    boolean interruptor = true;

    public Compressor (String arquivo, String arquivoFinal, String arquivoDicionario) throws IOException {

        HashMap<Integer, Integer> MapaDicionario = new HashMap (this.contaCaracteres(arquivo));

        Heap heap = new Heap();
        Node no = new Node();
        Node n1 = criaArvore(MapaDicionario, heap, no);

        HashMap<Character, String> dicionario = geraDicionario(n1, arquivoDicionario);
        geraArquivoComprimido(arquivo, arquivoFinal, dicionario);

    }

    public Map<Integer, Integer> contaCaracteres(String arquivo) throws IOException {
        Map<Integer, Integer> contadorChar = new HashMap<>();
        FileInputStream leitor = new FileInputStream(arquivo);

        BufferedInputStream buLeitor = new BufferedInputStream(leitor);

        byte conteudo[] = buLeitor.readAllBytes();
        String lido = new String(conteudo, "UTF8");
        for (int i = 0; i < lido.length(); i++) {
            if (contadorChar.containsKey((int) lido.charAt(i))) {
                int key = (contadorChar.get((int) lido.charAt(i))) + 1;
                contadorChar.put((int) lido.charAt(i), key);
            } else {
                contadorChar.put((int) lido.charAt(i), 1);
            }
        }
        contadorChar.put(300, 1);
        return contadorChar;
    }

    public Node criaArvore(Map<Integer, Integer> mapaCaracteres, Heap heap, Node node)  {
        for (Integer key : mapaCaracteres.keySet()) {
            Node no = new Node(key, mapaCaracteres.get(key));
            heap.insert(no);
        }
        do{
            if (heap.getSize() == 1) {
                interruptor = false;
                break;
            }
            Node left = heap.peek();
            heap.remove();
            Node right = heap.peek();
            heap.remove();
            node = new Node(left.getValue().getQuantitie() + right.getValue().getQuantitie(), left, right);
            heap.insert(node);
        } while (heap.getSize() > 1);
        return node;
    }

    public HashMap<Character, String> geraDicionario(Node node, String dicionario) throws IOException {
        HashMap<Character, String> mapaBinario = new HashMap<>();
        String bit[] = ABinario(node, interruptor);

        FileWriter arquivoDicionario = new FileWriter(dicionario);
        for (int i = 0; i < bit.length; ) {
            arquivoDicionario.write((char) Integer.parseInt(bit[i]) + String.valueOf((char) -1) + bit[i + 1] + String.valueOf((char) -1));
            mapaBinario.put((char) Integer.parseInt(bit[i]), bit[i + 1]);
            i += 2;
        }
        arquivoDicionario.close();
        FileInputStream tabela = new FileInputStream(dicionario);
        tabela.close();
     return mapaBinario;
    }

    public void geraArquivoComprimido(String arquivo, String arquivoFinal, Map<Character, String> mapaBinario) throws IOException {
        FileOutputStream arqFinal = new FileOutputStream(arquivoFinal);
        FileInputStream arqOriginal = new FileInputStream(arquivo);

        BufferedInputStream buLeitor = new BufferedInputStream(arqOriginal);

        int cont = 0;
        BitSet bitSet = new BitSet();
        byte conteudo[] = buLeitor.readAllBytes();
        String lido = new String(conteudo, "UTF8");
        for (int i = 0; i < lido.length(); i++) {
            if (mapaBinario.containsKey(lido.charAt(i))) {
                for (int j = 0; j < mapaBinario.get(lido.charAt(i)).length(); j++) {
                    if (mapaBinario.get(lido.charAt(i)).charAt(j) == '1') {
                        bitSet.set(cont);
                    } else {
                        bitSet.set(cont, false);
                    }
                    cont += 1;
                }
            }
        }

        for (int j = 0; j < mapaBinario.get((char) 300).length(); j++) {
            if (mapaBinario.get((char) 300).charAt(j) == '1') {
                bitSet.set(cont);
            } else {
                bitSet.set(cont, false);
            }
            cont += 1;
        }

        if (cont % 8 == 0) {
            arqFinal.write(bitSet.toByteArray());
            arqFinal.close();
            arqOriginal.close();
        } else {
            int octeto = (int) ((1 - (((float) cont / 8) - (cont / 8))) * 8);
            for (int i = 0; i < octeto; i++) {
                bitSet.set(cont);
                cont += 1;
            }
            arqFinal.write(bitSet.toByteArray());
            arqFinal.close();
            arqOriginal.close();
        }
    }
}