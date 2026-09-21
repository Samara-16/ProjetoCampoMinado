/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;
/**
 *
 * @author SamaraTavares
 */

public class Jogo extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Jogo.class.getName());
    
    // É O LOCAL ONDE CRIAMOS AS NOSSAS VARIAVEIS
    // btnCampos é o nome da variavel - (você escolhe)
    // matriz com - 10 linhas e 10 colunas
    JButton [][] btnCampos = new JButton[10][10];
    
    //MATRIZ PQRQ GUARDAR AS BOMBAS - true p/bomba, false p/numero
    boolean [][] bombas = new boolean [10][10];
    
    //MATRIZ PARA GUARDAR OS CAMPOS QUE FOREM ABERTOS
    boolean [][] abertos = new boolean[10][10];
    
    int quantidadeBombas = 20;
    boolean jogoEncerrado= false;
    int quantidadeCasasAbertas;
    int segundosPassados =0;
    Timer cronometro;
    
    
    // CONSTRUTOR DA CLASSE/TELA - SEM ELE A TELA NÃO FUNCIONA
    public Jogo() {
        initComponents();
        //definir tamanho para painel
        painelCampo.setPreferredSize(new Dimension(900,700));
        CriarTabuleiro();
    }

    // CRIAR AS NOSSAS FUNÇÕES/METODOS
    
    
    public void CriarTabuleiro(){
        // definir que o painel será dividido em 10 linhas e 10 colunas
        //com altura 2px e largura2px
        painelCampo.setLayout(new GridLayout(10,10,2,2));
        
        for(int coluna=0; coluna<=9;coluna++){
            for(int linha=0;linha<=9;linha++){
                // váriavel botão para guardar os dados provisorios
                JButton botao = new JButton();
                botao.setFont(new Font("Kristen ITC 15 Bold",Font.BOLD,15));
                botao.setBackground(new Color(197,213,235));
                botao.setForeground(Color.WHITE);// COR DE TEXTO
                
                // remover marcas do botão que vem por padrão
                botao.setFocusPainted(false);
                botao.setEnabled(false);
                
                final int linhaSelecionada= linha;
                final int colunaSelecionada = coluna;
                
                //adicionar o evento de clique para abrir as casas
                botao.addActionListener((ActionEvent Evento)->{
                       abrirBotao(linhaSelecionada,colunaSelecionada); 
                
                            });
                
                
                // adicionar o botao dentro da matriz
                btnCampos[linha][coluna]=botao;
                // adicionar ele dentro do painel
                painelCampo.add(botao);
                
            }// fim da 2 for
        }// fim da 1 for  
        
    }//fim do metodo CriarTabubeiro
    
    public void AdicionarBombas(){
        //Criar uma variavel Random para gerar valores aleatorios
        Random sorteador = new Random();
        int bombasAdicionadas = 0;
        
        while( bombasAdicionadas < quantidadeBombas){
            // sortear o n d linha e coluna que vai ficar a bomba
            int linha = sorteador.nextInt(10);
            int coluna = sorteador.nextInt(10);
            //verifica se não existe bomba adicionada no local
            if(!bombas[linha][coluna]){
                //adicionar a bomba na matirz
                bombas[linha][coluna]= true;
                bombasAdicionadas++;
            }
            
        }
        
        
    }// fim do AdicionarBombas
    
    public void IniciarJogo(){
        //chamar o metodo adicionarBombas
        AdicionarBombas();
        IniciarConometro();
        //depois precisamos iniciar os botoes do jogo
        for(int colunas=0;colunas<=9;colunas++){
            for(int linhas=0;linhas<=9;linhas++){
                JButton botao = btnCampos[linhas][colunas];
                //deixar os botoes visiveis e clicaveis
                botao.setEnabled(true);
            }//fim do 2 for
        }//fim do 1 for
        btniniciar.setText("REINICIAR");
    }// fim do IniciarJogo
    
    public void abrirBotao(int linha, int coluna){
        //verifica se o jogo foi finalizado
        if(jogoEncerrado) return;
        
        //verificar se o botao ja foi aberto
        if(abertos[linha][coluna]) return;
        
        /*se o jogo ainda estiver rodando e o botão ainda não tiver
        sido aberto - então vamos abrir o botão*/
        abertos[linha][coluna]=true;
        quantidadeCasasAbertas++;
        
        // acessar o que tem dentro do botão
        JButton botao = btnCampos[linha][coluna];
        //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
        if(bombas[linha][coluna]){
            //variavel que recebe nossa imagem
            ImageIcon imgBomba = new ImageIcon(
                    getClass(). getResource("/assets/bomb.png"));
            //colocar a imagem no botao
            botao.setIcon(imgBomba);
            FinalizarJogo(false);
            return;
        }else{
            ImageIcon imgBandeira = new ImageIcon(
            getClass(). getResource("/assets/flag.png"));
            botao.setIcon(imgBandeira);
            return;
        }
        
    }  
    
    public void FinalizarJogo(boolean venceu){
        //vamos informar que o jogo acabou
        MostrarBombas();
        jogoEncerrado=true;
        cronometro.stop();
        
        //verificar se a pessoa venceu ou não
        if(venceu){
            JOptionPane.showMessageDialog(this,"Parabéns você venceu!");
            LimparJogo();
        }else{
            JOptionPane.showMessageDialog(this,"Ops, você perdeu o jogo!");
            LimparJogo();
        }
    }//fim da finalizão
    
    public void VerificarVitoria(){
        // armazenar a quantidade de casas com bandeiras
        int casasSemBomba= 100 - quantidadeBombas;
        // se a pessoa abrir todas as bandeiras e não abriu nenhuma bomba
        // então ela venceu o jogo, e o finalizarJogo imprime a mensagem
        if(quantidadeCasasAbertas == casasSemBomba){
            FinalizarJogo(true);
            
        }
    }
    
    public void IniciarConometro(){
        // zerar o cronometro caso tenha sido um jogo anterior
        if(cronometro !=null){
            cronometro.stop();
        }
        // reseta o cronometro
        segundosPassados = 0;
        tfTempo.setText("00:00");
        
        // converter o tempo em minutos e segundos
        // o cronometro conta de 1 em 1 segundos, e vai convertendo
        cronometro = new Timer(1000, Everton->{
            segundosPassados++;
            int minutos = segundosPassados/60;
            int horas = minutos/60;
            int segundo = segundosPassados%60;
            //mostrar o tempo dentro da variavel
            tfTempo.setText(
            String.format("%02d:%02d:%02d",horas,minutos,segundo));
            
        });
        cronometro.start();        
    }

    public void LimparJogo(){
      quantidadeCasasAbertas=0;

        for(int coluna=0; coluna<=9;coluna++){
            for(int linha=0;linha<=9;linha++){
                bombas[linha][coluna]=false;
                abertos[linha][coluna]=false;
                jogoEncerrado=false;
                // limpaza dos botões
                JButton botao= btnCampos[linha][coluna];
                botao.setIcon(null);
                        
            }// fim do 2 for
        }// fim do 1 for
        AdicionarBombas();
        IniciarConometro();
    }// fim do LimparJogo
    
    public void MostrarBombas(){
       for(int coluna=0;coluna<=9;coluna++){
           for(int linha=0;linha<=9;linha++){
               JButton botao = btnCampos[linha][coluna];
               //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
               if(bombas[linha][coluna]){
                   //variavel que recebe nossa imagem
                  ImageIcon imgBomba = new ImageIcon(
                          getClass(). getResource("/assets/bomb.png"));
                  //colocar a imagem no botao
                  botao.setIcon(imgBomba);
                  
               }// fim do if
           }// fim do 2 for
       }// fim do 1 for
    }// fim do mostrarBombas
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        btniniciar = new javax.swing.JButton();
        tfTempo = new javax.swing.JTextField();
        painelCampo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titulo.setBackground(new java.awt.Color(255, 255, 255));
        titulo.setFont(new java.awt.Font("Old English Text MT", 0, 55)); // NOI18N
        titulo.setForeground(new java.awt.Color(19, 51, 140));
        titulo.setText("Campo Minado");

        btniniciar.setBackground(new java.awt.Color(30, 82, 146));
        btniniciar.setFont(new java.awt.Font("Kristen ITC", 1, 15)); // NOI18N
        btniniciar.setForeground(new java.awt.Color(255, 255, 255));
        btniniciar.setText("PLAY");
        btniniciar.addActionListener(this::btniniciarActionPerformed);

        tfTempo.setEditable(false);
        tfTempo.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfTempo.setText("00:00");
        tfTempo.addActionListener(this::tfTempoActionPerformed);

        painelCampo.setBackground(new java.awt.Color(32, 94, 172));

        javax.swing.GroupLayout painelCampoLayout = new javax.swing.GroupLayout(painelCampo);
        painelCampo.setLayout(painelCampoLayout);
        painelCampoLayout.setHorizontalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        painelCampoLayout.setVerticalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btniniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(painelCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btniniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(painelCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfTempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTempoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTempoActionPerformed

    private void btniniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniniciarActionPerformed
        // TODO add your handling code here:
        IniciarJogo();
    }//GEN-LAST:event_btniniciarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Jogo().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btniniciar;
    private javax.swing.JPanel painelCampo;
    private javax.swing.JTextField tfTempo;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
