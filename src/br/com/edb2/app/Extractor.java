package br.com.edb2.app;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Extractor {
    private Map<String, Character> key = new HashMap<>();
    private Map<Character, String> cod = new HashMap<>();

    public Extractor (String arquivoCompactado, String dicionario, String descompactado) throws IOException {
        leDicionario(dicionario);
        descompactador(arquivoCompactado,descompactado);
    }

    public void leDicionario(String dicionario) throws IOException {
        FileInputStream leitor = new FileInputStream(dicionario);
        BufferedInputStream buLeitor = new BufferedInputStream(leitor);

        byte conteudo[] = buLeitor.readAllBytes();

        String lido = new String(conteudo, "UTF8");
        String []cods = lido.split(String.valueOf((char)-1));

        for (int i = 0; i < cods.length; ) {
            key.put(cods[i+1], cods[i].charAt(0));
            cod.put(cods[i].charAt(0), cods[i+1]);
            i+=2;
        }
        leitor.close();
    }

    public void descompactador(String arquivoCompactado, String descompactado) throws IOException {
        FileInputStream leitor = new FileInputStream(arquivoCompactado);
        byte[] bytes = leitor.readAllBytes();
        StringBuilder conteudo = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            conteudo.append(new StringBuilder(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(" ", "0")).reverse());
        }
        String comparator = "";
        FileWriter arquivoDescompactado = new FileWriter(descompactado);
        for (int j = 0; j < conteudo.length(); j++) {
            comparator += conteudo.charAt(j);
            if (cod.containsValue(comparator)) {
                if (comparator.equals(cod.get((char) 300))) {
                    arquivoDescompactado.close();
                } else {
                    arquivoDescompactado.write(key.get(comparator));
                    comparator = "";
                }
            }
        }
    }
}
