package br.com.edb2;

import java.io.*;
import java.util.HashMap;

public class Extractor {
    private HashMap<String,Character> dicionario;
    private String bytes;
    private String restaurado;

    public Extractor (String arquivoCompactado, String dicionario, String descompactado) throws IOException {
        leDicionario(dicionario);
        leCompactado(organizadorByte(arquivoCompactado));
        descompactador(getBytes(), getDicionario());
        restaurador(descompactado);
    }

    public String getBytes() {
        return bytes;
    }

    public HashMap<String, Character> getDicionario() {
        return dicionario;
    }

    public String getRestaurado() {
        return restaurado;
    }

    private void leDicionario (String dicionario) throws IOException {
        FileReader leitor = new FileReader(dicionario);
        BufferedReader buLeitor = new BufferedReader(leitor);
        HashMap<String, Character> mapa = new HashMap<>();
        StringBuffer tabela = new StringBuffer();
        char caractere;
        char auxLeitor = (char) buLeitor.read();

        while( auxLeitor != (char) 1 ){
            caractere = auxLeitor;
            auxLeitor = (char) buLeitor.read();
            while((auxLeitor == '1') || (auxLeitor == '0')){
                tabela.append(auxLeitor);
                auxLeitor = (char) buLeitor.read();
            }
            mapa.put(tabela.toString(), caractere);
            tabela = new StringBuffer();
        }
        this.dicionario = mapa;
    }

    private byte[] organizadorByte (String arquivoCompactado){
        File arquivo = new File(arquivoCompactado);
        FileInputStream arquivoStream;
        byte[] bytesArquivo = new byte[(int) arquivo.length()];
        try{
            arquivoStream = new FileInputStream(arquivo);
            arquivoStream.read(bytesArquivo);
            arquivoStream.close();
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bytesArquivo;
    }

    private void leCompactado (byte[] bytesArquivo) {
        StringBuilder string = new StringBuilder();
        for (byte b : bytesArquivo) {
            string.append(new StringBuilder(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0")).reverse());
        }
        this.bytes = string.toString();
    }

    private void descompactador(String bytes, HashMap<String,Character> dicionario) {
        StringBuilder leitor = new StringBuilder();
        StringBuilder descompatado = new StringBuilder();
        for(int i=0; i<bytes.length(); ++i) {
            leitor.append(bytes.charAt(i));
            if(dicionario.containsKey(leitor.toString()) && dicionario.get(leitor.toString()) != (char)3) {
                descompatado.append(dicionario.get(leitor.toString()));
                leitor.setLength(0);
            }
        }
        this.restaurado = descompatado.toString();
    }

    private void restaurador(String descompactado) throws IOException {
        FileOutputStream arquivoDescompactado = new FileOutputStream(descompactado);
        for (int i = 0; i < getRestaurado().length(); ++i){
            arquivoDescompactado.write(getRestaurado().charAt(i));
        }
        arquivoDescompactado.close();
    }

}
