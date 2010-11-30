/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jFMesas.java
 *
 * Created on 21/11/2010, 15:16:19
 */

package restaurantepoo.forms;

import java.awt.Color;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import restaurantepoo.dao.MesaDao;
import restaurantepoo.dao.MesaProdutoDao;
import restaurantepoo.logica.Mesa;
import restaurantepoo.logica.Produto;

/**
 *
 * @author Antonio
 */
public class jFMesas extends javax.swing.JFrame {

    /** Creates new form jFMesas */
    public jFMesas() throws SQLException, ParseException {
        initComponents();
        populaTabelaMesas("");
        criaMesas(10);
    }
    
     DefaultTableModel tmMesa = new DefaultTableModel(
            new Object [][]{
            },
            new String[]{"mesa", "horaabertura", "valortotal"}
     );
     
     DefaultTableModel tmProduto = new DefaultTableModel(
            new Object [][]{
            },
            new String[]{"produto", "nome", "preco", "quantidade", "total item"}
     );

    public ArrayList<Mesa> mesas = new ArrayList<Mesa>();
    Mesa mesa;
    ListSelectionModel lsmMesa;
    ListSelectionModel lsmProdutos;

    public void populaTabelaMesas(String busca) throws SQLException, ParseException{

        ArrayList<Mesa> mesaBanco = new ArrayList<Mesa>();
        tmMesa.setRowCount(0);
        
        MesaDao dao = new MesaDao();
        mesaBanco = dao.getLista("%"+busca+"%");

        for (Mesa m1 : mesaBanco) {
            insereTabelaMesas(m1);
        }
    }
     
     public void populaTabelaProdutos(){
        tmProduto.setRowCount(0);
        System.out.println("Numero da mesa escolhido:" + mesa.getMesa());
        for (Produto p1 : mesa.produtos) {
            insereTabelaProdutos(p1);
        }
    }
    public void populaTabelaProdutosBanco() throws SQLException {
        tmProduto.setRowCount(0);

        MesaProdutoDao dao = new MesaProdutoDao();
        Mesa m1 = new Mesa();
        Double somaTotal = 0.0;

        dao.getListaProdutosMesa(numeroMesa.getText(), m1);

        for (int i = 0; i < m1.produtos.size(); i++) {
            
            double preco = m1.produtos.get(i).getPreco();
            int qtd = Integer.parseInt(m1.quantidade.get(i));
            Double soma = qtd*preco;
            somaTotal += soma;

            tmProduto.addRow(new String[]{
                        String.valueOf(m1.produtos.get(i).getProduto()),
                        m1.produtos.get(i).getNome(),
                        String.valueOf(preco),
                        String.valueOf(qtd),
                        String.valueOf(soma)
                    });
        }
        salvaValorTotal(somaTotal);
    }

    private void insereTabelaMesas(Mesa m1){

        tmMesa.addRow(new String[]{
            String.valueOf(m1.getMesa()),
            !m1.isStatus()?"Livre":String.valueOf(m1.getHoraAbertura()),
            String.valueOf(m1.getValorTotal())
        });
    }

    private void insereTabelaProdutos(Produto p1){

        tmProduto.addRow(new String[]{
            String.valueOf(p1.getProduto()),
            p1.getNome(),
            String.valueOf(p1.getPreco())
        });
    }

    private void criaMesas(int n) throws SQLException{
        for (int i=0; i<=n; i++){
            Mesa m = new Mesa();
            m.setMesa(i);
            mesas.add(m);
            //criaMesasBanco(m);    Linha só é utilizada para criar as mesas no banco de dados.
        }
    }

    private void criaMesasBanco(Mesa m1) throws SQLException{
        MesaDao dao = new MesaDao();
        dao.criaMesa(m1);
    }

    public void salvaValorTotal(Double total){
        Mesa m1 = new Mesa();
        m1.setValorTotal(total);
        m1.setMesa(Integer.parseInt(numeroMesa.getText()));

        try {
            MesaDao dao = new MesaDao();
            dao.alteraValorTotal(m1);
        } catch (SQLException ex) {
            Logger.getLogger(jFMesas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void calculaValorTotal(){
        double soma =0;

        int linha;
        linha = Integer.parseInt(numeroMesa.getText());
        mesa = mesas.get(linha);

        for (Produto p1 : mesa.produtos) {
            soma += p1.getPreco();
        }
        
        mesa.setValorTotal(soma);
        try {
            MesaDao dao = new MesaDao();
            dao.alteraValorTotal(mesa);
        } catch (SQLException ex) {
            Logger.getLogger(jFMesas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private int escolherLinha(){

        int linha = tabelaMesas.getSelectedRow();
        System.out.println("linha:"+linha);

        String temp;

        temp = String.valueOf(tabelaMesas.getValueAt(linha, 0));
        numeroMesa.setText(temp);
        return linha;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sair = new javax.swing.JButton();
        cadProduto = new javax.swing.JButton();
        numeroMesa = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        produtos = new javax.swing.JTable();
        cadFuncionario = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        adicionaProduto = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaMesas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel1.setText("MESA:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        sair.setText("Sair");
        sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairActionPerformed(evt);
            }
        });
        jPanel1.add(sair, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, 80, 30));

        cadProduto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cadProduto.setText("Cadastrar Produto");
        cadProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadProdutoActionPerformed(evt);
            }
        });
        jPanel1.add(cadProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 150, 30));

        numeroMesa.setEditable(false);
        jPanel1.add(numeroMesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 60, -1));

        produtos.setModel(tmProduto);
        jScrollPane2.setViewportView(produtos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 60, 460, 210));

        cadFuncionario.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cadFuncionario.setText("Cadastrar Funcionário");
        jPanel1.add(cadFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 150, 30));

        jButton1.setText("Fechar Mesa");
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, 130, 30));

        jButton2.setText("Dividir Conta");
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 130, 30));

        adicionaProduto.setText("Adicionar Produto");
        adicionaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionaProdutoActionPerformed(evt);
            }
        });
        jPanel1.add(adicionaProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 150, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 370));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tabelaMesas.setModel(tmMesa);
        tabelaMesas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMesasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaMesas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 340, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairActionPerformed
        dispose();
    }//GEN-LAST:event_sairActionPerformed

    private void cadProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadProdutoActionPerformed
        try {
            new jFCadProduto().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(jFMesas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_cadProdutoActionPerformed

    private void tabelaMesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMesasMouseClicked
        //habilitaDados();
        int linha;
        linha = escolherLinha();
        mesa = mesas.get(linha);
        //calculaValorTotal();
        //populaTabelaProdutos();  // numero da mesa é igual ao numero da mesa +1
        try {
            populaTabelaProdutosBanco();

        } catch (SQLException ex) {
            Logger.getLogger(jFMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelaMesasMouseClicked

    private void adicionaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionaProdutoActionPerformed
        System.out.println("Entrando no Forme de adicionar Produtos...");

        try {
            new jFAdicionaProduto(this,numeroMesa.getText()).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(jFMesas.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Voltando do Forme de adicionar Produtos...");
    }//GEN-LAST:event_adicionaProdutoActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionaProduto;
    private javax.swing.JButton cadFuncionario;
    private javax.swing.JButton cadProduto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField numeroMesa;
    private javax.swing.JTable produtos;
    private javax.swing.JButton sair;
    private javax.swing.JTable tabelaMesas;
    // End of variables declaration//GEN-END:variables

}
