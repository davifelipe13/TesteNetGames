/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JChat;

import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author Davi
 */
public class Sala {
    
    private ArrayList<String> mensagens;
    private Participante participanteCriador;
    private Participante participanteConvidado;
    
    public Sala() {
        mensagens = new ArrayList<String>();
    }
    
    public void criarParticipante (String nome) {
        if (participanteCriador == null) {
            participanteCriador = new Participante(nome);
            participanteCriador.tomarVez();
        } else if (participanteConvidado == null) {
            participanteConvidado = new Participante(nome);
            participanteConvidado.passarVez();
        }
    }
    
    public boolean trataMensagem(String mensagem) {
        if (participanteCriador.ehVez()) {
            mensagens.add("[" + participanteCriador.getNome() + "]: " + mensagem + "/n");
            participanteCriador.passarVez();
            participanteConvidado.tomarVez();
            return true;
        } else if (participanteConvidado.ehVez()) {
            mensagens.add("[" + participanteConvidado.getNome() + "]: " + mensagem + "\n");
            participanteConvidado.passarVez();
            participanteCriador.tomarVez();
            return true;
        }
        return false;
    }
    
    public String informaUltimaMensagem() {
        return mensagens.get(mensagens.size() -1);
    }


       
}
