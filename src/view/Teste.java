/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Palavra;
import util.Arvore;

/**
 *
 * @author Matheus Nascimento
 */

public class Teste{ 
    Arvore avl = new Arvore();
    public void criar(){
        
        avl.inserir(new Palavra("C"));
        avl.inserir(new Palavra("b"));      
        avl.inserir(new Palavra("a"));
        
    }

  
    

    public Arvore getAvl() {
        return avl;
    }

    public void setAvl(Arvore avl) {
        this.avl = avl;
    }
    
    
}
