/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import util.Arvore;

/**
 *
 * @author Matheus Nascimento
 */
public class ControllerBusca {

    ControllerFile controlRead;
    Arvore buscaRapida;

    public ControllerBusca() {
        controlRead = new ControllerFile();
        buscaRapida = new Arvore();
    }

    public Arvore getBuscaRapida() {
        return buscaRapida;
    }
    
    public boolean addPalavra(String palavra) throws IOException {
        return controlRead.readFiles(palavra, buscaRapida);

    }

    public void setBuscaRapida(Arvore buscaRapida) {
        this.buscaRapida = buscaRapida;
    }

    public ControllerFile getControlRead() {
        return controlRead;
    }

    public void setControlRead(ControllerFile controlRead) {
        this.controlRead = controlRead;
    }



}
