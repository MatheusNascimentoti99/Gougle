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

    ControllerBuscaFile controlRead;
    Arvore buscaRapida;

    public ControllerBusca() {
        controlRead = new ControllerBuscaFile();
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

    public ControllerBuscaFile getControlRead() {
        return controlRead;
    }

    public void setControlRead(ControllerBuscaFile controlRead) {
        this.controlRead = controlRead;
    }



}
