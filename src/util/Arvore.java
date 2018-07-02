/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*

    Arvore  baseada na Arvore implementada por Rodrigo Vilar disponivel no GitHub: https://gist.github.com/rodrigovilar
 */
package util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import model.Pagina;

import model.Palavra;

/**
 * A classe <b>Arvore</b> é a classe para armazenar a estrutura de dado Árvore AVL.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 */
public class Arvore implements Serializable {
    
    protected No raiz;
    
    /**
     * Método que insere um objeto na Árvore AVL, passando como parâmetro para o método secundário, o nó com o
     * objeto a ser inserido e a raiz da árvore.
     * @param date Objeto Comparable
     */
    public void inserir(Comparable date) {
        No n = new No(date);
        inserirAVL(this.raiz, n);
    }

    /**
     *Método que retorna a raiz da árvore AVL.
     * @return No raiz da árvore.
     */
    public No getRaiz() {
        return raiz;
    }

    /**
     * Método que designa um novo No raiz á árvore.
     * @param raiz Nó a ser a raiz.
     */
    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }

    /**
     * Método secundário de inserção da árvore, onde é recebido como parâmetro 
     * o nó da árvore e o nó com o objeto a ser inserido na árvore.
     * @param aComparar Nó a ser comparado
     * @param aInserir Nó a ser inserido na árvore.
     */
         private void fileEqual(No aComparar, No aInserir) {
        boolean equal = false;
        Palavra compara = (Palavra) aComparar.getData();
        Palavra nova = (Palavra) aInserir.getData();
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
     
         public void inserirAVL(No aComparar, No aInserir) {

        if (aComparar == null) {
            this.raiz = aInserir;

        } else {

            if (aInserir.getData().compareTo(aComparar.getData()) < 0) {

                if (aComparar.getLeft() == null) {
                    aComparar.setLeft(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

                } else {
                    inserirAVL(aComparar.getLeft(), aInserir);
                }

            } else if (aInserir.getData().compareTo(aComparar.getData()) > 0) {

                if (aComparar.getRight() == null) {
                    aComparar.setRight(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

                } else {
                    inserirAVL(aComparar.getRight(), aInserir);
                }

            } else {
                if (aInserir.getData().compareTo(aComparar.getData()) == 0) {
                    fileEqual(aComparar, aInserir);
                       
                    
                }
            }
        }

    }
    /**
     *Método que verifica o balanceamento de um nó da árvore.
     * @param atual Nó atual 
     */
    public void verificarBalanceamento(No atual) {
        setBalanceamento(atual);
        int balanceamento = atual.getBalanceamento();

        if (balanceamento == -2) {

            if (altura(atual.getLeft().getRight()) < altura(atual.getLeft().getLeft())) {
                atual = rotacaoDireita(atual);

            } else {
                atual = duplaRotacaoEsquerdaDireita(atual);
            }

        } else if (balanceamento == 2) {

            if (altura(atual.getRight().getLeft()) < altura(atual.getRight().getRight())) {
                atual = rotacaoEsquerda(atual);

            } else {
                atual = duplaRotacaoDireitaEsquerda(atual);
            }
        }

        if (atual.getPai() != null) {
            verificarBalanceamento(atual.getPai());
        } else {
            this.raiz = atual;
        }
    }
    //Código retirado da aula 09 de Estrutura de Dados do Professora João Rocha.

    public Queue preOrder() {
        Queue fila = new LinkedList();
        Stack stack = new Stack();
        stack.push(raiz);
        while (!stack.isEmpty()) {
            No n = (No) stack.pop();
            fila.add(n.getData());
            if (n.getRight() != null) {
                stack.push(n.getRight());
            }
            if (n.getLeft() != null) {
                stack.push(n.getLeft());
            }
        }
        return fila;
    }

    /**
     * Método que remove uma objeto Palavra da árvore.
     * @param palavra Palavra a ser removida.
     */
 public void remover(Comparable palavra) {
        remover(this.raiz, palavra);
    }

    private void remover(No atual, Comparable palavra) {
        if (raiz == null) {
        } else {
            if (atual.getData().compareTo(palavra) > 0) {
                remover(atual.getLeft(), palavra);

            } else if (atual.getData().compareTo(palavra) < 0) {
                remover(atual.getRight(), palavra);

            } else if (atual.getData().equals(palavra)) {
                removerNoEncontrado(atual);
            }
        }
    }

    private void removerNoEncontrado(No aRemover) {
        No aux;
        //Verifica se o n� a ser removido � uma folha.
        if (aRemover.getLeft()== null || aRemover.getRight()== null) {
            if (aRemover.getPai() == null) {
                this.raiz = null;
                return;
            }
            aux = aRemover;
        } else {
            //Caso contrario ele ir� procurar o proximo n� que poder� substituir aquela sub-�rvore.
            aux = sucessor(aRemover);
            aRemover.setData(aux.getData());
        }
        No aux2;
        if (aux.getLeft()!= null) {
            aux2 = aux.getLeft();
        } else {
            aux2 = aux.getRight();
        }

        if (aux2 != null) {
            aux2.setPai(aux.getPai());
        }

        if (aux.getPai() == null) {
            this.raiz = aux2;
        } else {
            if (aux == aux.getPai().getLeft()) {
                aux.getPai().setLeft(aux2);
            } else {
                aux.getPai().setRight(aux2);
            }
            verificarBalanceamento(aux.getPai());
        }
    }
    private No rotacaoEsquerda(No inicial) {

        No right = inicial.getRight();
        right.setPai(inicial.getPai());

        inicial.setRight(right.getLeft());

        if (inicial.getRight() != null) {
            inicial.getRight().setPai(inicial);
        }

        right.setLeft(inicial);
        inicial.setPai(right);

        if (right.getPai() != null) {

            if (right.getPai().getRight() == inicial) {
                right.getPai().setRight(right);

            } else if (right.getPai().getLeft() == inicial) {
                right.getPai().setLeft(right);
            }
        }

        setBalanceamento(inicial);
        setBalanceamento(right);

        return right;
    }

    private No rotacaoDireita(No inicial) {

        No left = inicial.getLeft();
        left.setPai(inicial.getPai());

        inicial.setLeft(left.getRight());

        if (inicial.getLeft() != null) {
            inicial.getLeft().setPai(inicial);
        }

        left.setRight(inicial);
        inicial.setPai(left);

        if (left.getPai() != null) {

            if (left.getPai().getRight() == inicial) {
                left.getPai().setRight(left);

            } else if (left.getPai().getLeft() == inicial) {
                left.getPai().setLeft(left);
            }
        }

        setBalanceamento(inicial);
        setBalanceamento(left);

        return left;
    }

    private No duplaRotacaoEsquerdaDireita(No inicial) {
        inicial.setLeft(rotacaoEsquerda(inicial.getLeft()));
        return rotacaoDireita(inicial);
    }

    private No duplaRotacaoDireitaEsquerda(No inicial) {
        inicial.setRight(rotacaoDireita(inicial.getRight()));
        return rotacaoEsquerda(inicial);
    }

    public No sucessor(No q) {
        if (q.getRight()!= null) {
            No r = q.getRight();
            while (r.getLeft()!= null) {
                r = r.getLeft();
            }
            return r;
        } else {
            No p = q.getPai();
            while (p != null && q == p.getRight()) {
                q = p;
                p = q.getPai();
            }
            return p;
        }
    }

    private int altura(No atual) {
        if (atual == null) {
            return -1;
        }

        if (atual.getLeft() == null && atual.getRight() == null) {
            return 0;

        } else if (atual.getLeft() == null) {
            return 1 + altura(atual.getRight());

        } else if (atual.getRight() == null) {
            return 1 + altura(atual.getLeft());

        } else {
            return 1 + Math.max(altura(atual.getLeft()), altura(atual.getRight()));
        }
    }

    private void setBalanceamento(No no) {
        no.setBalanceamento(altura(no.getRight()) - altura(no.getLeft()));
    }

    /**
     * Método que verifica se a árvore está vazia, analisando se a raiz é null.
     * @return boolean, true ou false.
     */
    public boolean isEmpty() {
        return raiz == null;
    }

}
