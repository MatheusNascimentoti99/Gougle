/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerFile;
import java.io.IOException;
import java.util.Random;
import model.Palavra;
import util.Arvore;
import util.No;

/**
 *
 * @author Matheus Nascimento
 */
public class main {
    
    public static void main(String[] args) throws IOException{
        ControllerFile c = new ControllerFile();
        
        
        Arvore a = c.readFiles();
        System.out.println(":)");
    }
}
