/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rede;

import JChat.AtorChat;
import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 06624567937
 */
public class AtorRede implements OuvidorProxy {
    
    private AtorChat atorChat;
    private Proxy proxy;
    
    private boolean ehMinhaVez = false;
    
    public AtorRede(AtorChat atorChat) {
        super();
        this.atorChat = atorChat;
        proxy = Proxy.getInstance();
        proxy.addOuvinte(this);
    }
    
    public void conectar (String nome, String servidor) {
        try {
            proxy.conectar(servidor, nome);
        } catch (JahConectadoException ex) {
            JOptionPane.showMessageDialog(atorChat.getFrame(), ex.getMessage());
            ex.printStackTrace();
        } catch (NaoPossivelConectarException ex) {
            JOptionPane.showMessageDialog(atorChat.getFrame(), ex.getMessage());
            ex.printStackTrace();
        } catch (ArquivoMultiplayerException ex) {
            JOptionPane.showMessageDialog(atorChat.getFrame(), ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void iniciarPartidaRede() {
        try {
            proxy.iniciarPartida(2);
        } catch (NaoConectadoException ex) {
            JOptionPane.showMessageDialog(atorChat.getFrame(), ex.getMessage());
            ex.printStackTrace();
        }
    }   
    
    @Override
    public void iniciarNovaPartida(Integer posicao) {
        if (posicao == 1) {
            ehMinhaVez = true;
        } else if (posicao == 2) {
            ehMinhaVez = false;
        }
        
        atorChat.iniciarPartidaRede(ehMinhaVez);
    }
    
    public void enviarJogada(String mensagem) {
        Mensagem msg = new Mensagem(mensagem);
        try {
            proxy.enviaJogada(msg);
            ehMinhaVez = false;
        } catch (NaoJogandoException ex) {
            JOptionPane.showMessageDialog(atorChat.getFrame(), ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    @Override
    public void finalizarPartidaComErro(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void receberMensagem(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void receberJogada(Jogada jogada) {
        Mensagem msg = (Mensagem) jogada;
        ehMinhaVez = true;
        atorChat.receberMensagemRede(msg.getMensagem());
    }
    
    public void desconectar() {
        try {
            proxy.desconectar();
        } catch (NaoConectadoException ex) {
            JOptionPane.showMessageDialog(atorChat.getFrame(), ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    @Override
    public void tratarConexaoPerdida() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tratarPartidaNaoIniciada(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String obterNomeAdversario() {
        String nome = "";
        if (ehMinhaVez) {
            nome = proxy.obterNomeAdversario(2);
        } else {
            nome = proxy.obterNomeAdversario(1);
        }
        return nome;
    }

    public boolean ehMinhaVez() {
        return ehMinhaVez;
    }
    
    
}

