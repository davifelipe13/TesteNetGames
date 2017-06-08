/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JChat;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import rede.AtorRede;

/**
 *
 * @author Davi
 */
public class AtorChat {

    private JTextArea incoming;
    private JTextField outgoing;
    private Sala sala;
    private String nome = "";
    
    private AtorRede atorRede;
    private JFrame frame;

    public AtorChat() {
        super();
        atorRede = new AtorRede(this);
    }

    public void go() {
        
        frame = new JFrame("JChat");
        
        nome = JOptionPane.showInputDialog(frame, "Escolha o nome do participante 1");
        
        atorRede.conectar(nome, "localhost");
        

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel buttonPanel = new JPanel();
        
        incoming = new JTextArea(15,50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        outgoing = new JTextField(40);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        
        buttonPanel.add(outgoing);
        buttonPanel.add(sendButton);
        
        JPanel conexaoPanel = new JPanel();
        
        JButton iniciarConversa = new JButton("Iniciar Conversa");
        JButton desconectar = new JButton("Desconectar");
        
        ConexaoListener conexaoListener = new ConexaoListener();
        
        iniciarConversa.addActionListener(conexaoListener);
        desconectar.addActionListener(conexaoListener);
        
        conexaoPanel.add(iniciarConversa);
        conexaoPanel.add(desconectar);
        
        
        mainPanel.add(qScroller);
        mainPanel.add(buttonPanel);
        mainPanel.add(conexaoPanel);
        frame.add(mainPanel);
        
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }
    
    public void iniciarConversa() {
        atorRede.iniciarPartidaRede();
    }

    public void iniciarPartidaRede(boolean comecoJogando) {
        String nomeOutroJogador = atorRede.obterNomeAdversario();
        sala = new Sala();
        
        if (comecoJogando) {
            sala.criarParticipante(this.nome);
            sala.criarParticipante(nomeOutroJogador);
            JOptionPane.showMessageDialog(this.getFrame(), "É sua vez, comece falando..");
        } else {
            sala.criarParticipante(nomeOutroJogador);
            sala.criarParticipante(this.nome);
            JOptionPane.showMessageDialog(this.getFrame(), "Espero o outro falar...");
        }
        
    }

    public void receberMensagemRede(String mensagem) {
        sala.trataMensagem(mensagem);
        incoming.append(sala.informaUltimaMensagem());
    }
    
    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            boolean tratou = AtorChat.this.novaMensagem(outgoing.getText());
            
            if (tratou) {
                outgoing.setText("");
                outgoing.requestFocus(); 
            } else {
                JOptionPane.showMessageDialog(AtorChat.this.getFrame(), "Não é a sua vez, aguarde!");
            }
        }
    }
    
    public class ConexaoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton botao = (JButton) e.getSource();
                if (botao.getText().equals("Iniciar Conversa")) {
                    AtorChat.this.atorRede.iniciarPartidaRede();
                } else if (botao.getText().equals("Desconectar")) {
                    AtorChat.this.atorRede.desconectar();
                    System.exit(0);
                }
            }
        }
        
    }
    
    public boolean novaMensagem (String mensagem) {
        if (atorRede.ehMinhaVez()) {
            boolean tratou = sala.trataMensagem(mensagem);
            if (tratou) {
                incoming.append(sala.informaUltimaMensagem());
                atorRede.enviarJogada(sala.informaUltimaMensagem());
            } else {
                JOptionPane.showMessageDialog(frame, "Ocorreu um erro na ordenação de mensagens");
            }
            return tratou;
        } else {
            return false;
        }
    }
    
    public static void main (String[] args) {
        new AtorChat().go();
    }
    
}
