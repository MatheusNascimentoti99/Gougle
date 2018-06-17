/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import util.Arvore;

/**
 *
 * @author Matheus Nascimento
 */
public class ControllerSave {

    public void saveFiles(LinkedList files) throws Exception {
        save(files, "resources\\Files.date");
    }

    public void saveTree(Arvore arvore) throws Exception {
        save(arvore, "resources\\Tree.date");
    }

    public LinkedList readFiles() {
        LinkedList temp = (LinkedList) readDate("resources\\Files.date");
        return temp;
    }

    public Arvore readTree() {
        Arvore temp;
        temp = (Arvore) readDate("resources\\Tree.date");
        if (temp == null) {
            return new Arvore();
        }
        return temp;
    }

    private Object readDate(String local) {
        Object dado = new Object();
        try {

            try (
                    FileInputStream arquivoLeitura = new FileInputStream(local); ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura)) {

                dado = objLeitura.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
        }
        return dado;
    }

    protected void save(Object dados, String local) throws Exception {
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
