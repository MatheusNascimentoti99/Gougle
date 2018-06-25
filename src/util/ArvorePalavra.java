/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Iterator;
import model.Pagina;
import model.Palavra;

/**
 *
 * @author Matheus Nascimento
 */
public class ArvorePalavra extends Arvore{
    
     private void fileEqual(No aComparar, No aInserir) {
        boolean equal = false;
        Palavra compara = (Palavra) aComparar.getDate();
        Palavra nova = (Palavra) aInserir.getDate();
        Iterator it = compara.getPaginas().iterator();
        while (it.hasNext()) {
            Pagina pagina = (Pagina) it.next();
            if (pagina.getNome().equals(((Pagina) nova.getPaginas().getFirst()).getNome())) {
                pagina.repetir();
                equal = true;
            }

        }
        if(equal == false){
            compara.getPaginas().add(nova.getPaginas().getFirst());
        }
    }
     @Override
         public void inserirAVL(No aComparar, No aInserir) {

        if (aComparar == null) {
            this.raiz = aInserir;

        } else {

            if (aInserir.getDate().compareTo(aComparar.getDate()) < 0) {

                if (aComparar.getLeft() == null) {
                    aComparar.setLeft(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

                } else {
                    inserirAVL(aComparar.getLeft(), aInserir);
                }

            } else if (aInserir.getDate().compareTo(aComparar.getDate()) > 0) {

                if (aComparar.getRight() == null) {
                    aComparar.setRight(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

                } else {
                    inserirAVL(aComparar.getRight(), aInserir);
                }

            } else {
                if (aInserir.getDate().compareTo(aComparar.getDate()) == 0) {
                    fileEqual(aComparar, aInserir);
                       
                    
                }
            }
        }

    }
}
