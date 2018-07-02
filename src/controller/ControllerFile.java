/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * A classe <b>ControllerFile</b> faz o gerenciamento de leitura e gravação de arquivos.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 */
public class ControllerFile {

    /**
     *Ler arquivo binário.
     * @param local Parâmetro utilizado para indicar o diretorio e o nome do arquivo binario a ser lido.
     * @return Retorna o objeto que está salvo no arquivo binario indicado.
     * @throws FileNotFoundException Exceção caso a arquivo ou local não exista.
     */

    public static Object readDate(String local) throws FileNotFoundException {
        Object dado = null;
        try {

            try (
                    FileInputStream arquivoLeitura = new FileInputStream(local); ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura)) {
                dado = objLeitura.readObject();
                if(dado == null)
                   throw new FileNotFoundException();
            }

        } catch (IOException | ClassNotFoundException e) {
            
        }
        return dado;
    }

    /**
     *Salva um arquivo binário em disco.
     * @param dados O dado a ser gravado em disco.
     * @param local Local onde o arquivo será salvo.
     * @throws Exception Exceção ao gravar o arquivo.
     */
    public static void save(Object dados, String local) throws Exception {
        try {
            FileOutputStream arquivoGrav;
            arquivoGrav = new FileOutputStream(local);
            try (ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav)) {
                objGravar.writeObject(dados);

                objGravar.flush();
            } catch (Exception e) {
            }

            arquivoGrav.flush();
        } catch (IOException e) {
        }
    }


}
