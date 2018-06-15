/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Palavra;
import util.Arvore;
import util.No;

/**
 *
 * @author Matheus Nascimento
 */
public class main {
    
    public static void main(String[] args){
        Arvore a = new Arvore();
        Palavra p = new Palavra("A");
        Palavra p2 = new Palavra("B");
        Palavra p3 = new Palavra("C");
        Palavra p4 = new Palavra("D");
        a.inserir(p3);
        a.inserir(p);
        a.inserir(p4);
        a.inserir(p2);
       // System.out.println(a.getPalavra());
       Palavra b = new Palavra("oi");
//       test.setA(aa);
//       Palavra bb = (Palavra) test.getA();
    }
}
