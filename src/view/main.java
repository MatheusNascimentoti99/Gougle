/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Random;
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
        Random r = new Random();
        for(int i = 0; i < 1000; i++){
            a.inserir(new Palavra("A" +r.nextInt(100)));
        }
        System.out.println(((Palavra)a.getRaiz().getDate()).getPalavra());
       // System.out.println(a.getPalavra());
       Palavra b = new Palavra("oi");
//       test.setA(aa);
//       Palavra bb = (Palavra) test.getA();
    }
}
