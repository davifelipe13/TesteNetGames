/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JChat;


/**
 *
 * @author Davi
 */
public class Participante {
    
    private String nome;
    private boolean vez;
    
    public Participante(String nome) {
        super();
        this.nome = nome;
        vez = false;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void tomarVez() {
        vez = true;
    }
    
    public void passarVez() {
        vez =false;
    }

    public boolean ehVez() {
        return this.vez;
    }
    
}
