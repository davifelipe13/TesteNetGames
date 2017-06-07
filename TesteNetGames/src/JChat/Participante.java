/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JChat;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Davi
 */
public class Participante {
    
    private JTextArea incoming;
    private JTextField outgoing;
    private Sala sala;
    private JFrame frame;
    
    public void go() {
        
        frame = new JFrame("JChat");
        
        String nome1 = JOptionPane.showInputDialog(frame, "Escolha o nome do participante 1");
        String nome2 = JOptionPane.showInputDialog(frame, "Escolha o nome do participante 2");
        
        sala = new Sala();
        sala.criarParticipante(nome1);
        sala.criarParticipante(nome2);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel buttonPanel = new JPanel();
        
        incoming = new JTextArea(15,50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
    }
    
    public void novaMensagem (String mensagem) {
        boolean trato = sala.trataMensagem(mensagem);
        if (trato) {
            incoming.append(Sala.informaUltimaMensagem());
        } else {
            JOptionPane.showMessageDialog(frame, "Ocorreu um erro na ordenação de mensagens");
        }
    }
    
    public static void main (String[] args) {
        new AtorChat().go();
    }
    
}
