import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

import javax.swing.*;

public class TelaDeRemover extends JFrame
{
    private static JLabel lblId;
    private static JComboBox<String> cbxId;
    
    public static JLabel lblNome;
    public static JTextField txtNome;

    public static JLabel lblEmail;
    public static JTextField txtEmail;

    public static JLabel lblSenha;
    public static JPasswordField txtSenha;

    public static JButton btnRemover;
    public static JButton btnCancelar;

    public static JLabel lblNotificacoes;

    public static GridBagLayout gbLayout;
    public static GridBagConstraints gbConstraints;

    public TelaDeRemover(){
        super("Tela de Remover");

        gbLayout = new GridBagLayout();
        setLayout(gbLayout);
        gbConstraints = new GridBagConstraints();

        lblId = new JLabel("ID", SwingConstants.RIGHT);
        addComponent(lblId, 0, 0, 1, 1);

        cbxId = new JComboBox<String>();
        addComponent(cbxId, 0, 1, 1, 1);

        lblNome = new JLabel("Nome:", SwingConstants.RIGHT);
        txtNome.setEditable(false);
        addComponent(lblNome, 1, 0, 1, 1);

        txtNome = new JTextField(10);
        addComponent(txtNome, 1, 1, 1, 1);

        lblEmail = new JLabel("Email:", SwingConstants.RIGHT);
        addComponent(lblEmail, 2, 0, 1, 1);

        txtEmail = new JTextField(10);
        txtEmail.setEditable(false);
        addComponent(txtEmail, 2, 1, 1, 1);

        atualizarCampos(String.valueOf(cbxId.getSelectedItem()));

        lblSenha = new JLabel("Senha:", SwingConstants.RIGHT);
        addComponent(lblSenha, 3, 0, 1, 1);

        txtSenha = new JPasswordField(10);
        addComponent(txtSenha, 3, 1, 1, 1);

        btnRemover = new JButton("Remover");
        addComponent(btnRemover, 4, 0, 1, 1);

        btnCancelar = new JButton("Cancelar");
        addComponent(btnCancelar, 4, 1, 1, 1);

        lblNotificacoes = new JLabel("Notificações", SwingConstants.CENTER);
        addComponent(lblNotificacoes, 5, 0, 2, 1);

        cbxId.addItemListener(
            new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent event) {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        atualizarCampos(String.valueOf(cbxId.getSelectedItem()));
                    }
                }
            }
        );

         btnRemover.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    try {
                        Connection conexao = MySQLConnector.conectar();
                        String strSqlRemoverId = "delete from `db_senac`.`tbl_senac` where `id` = " + String.valueOf(cbxId.getSelectedItem()) + ";";
                        Statement stmSqlRemoverId = conexao.createStatement();
                        stmSqlRemoverId.addBatch(strSqlRemoverId);
                        stmSqlRemoverId.executeBatch();
                        notificarUsuario("O id " + String.valueOf(cbxId.getSelectedItem()) + " foi removido com sucesso.");
                    } catch (Exception e) {
                        notificarUsuario("Ops! Problema no servidor, tente novamente mais tarde.");
                        System.err.println("Erro: " + e);
                    }
                    try {
                        cbxId.setSelectedIndex(cbxId.getSelectedIndex() + 1);
                        cbxId.removeItemAt(cbxId.getSelectedIndex() - 1);
                    } catch (Exception e) {
                        cbxId.setSelectedIndex(cbxId.getSelectedIndex() - 1);
                        cbxId.removeItemAt(cbxId.getSelectedIndex() + 1);
                    }
                    atualizarCampos(String.valueOf(cbxId.getSelectedItem()));
                }
            }
        );

        btnCancelar.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event){
                    if (JOptionPane.showConfirmDialog(null, "Deseja cancelar e sair da Tela de Remover") == 0) {
                        System.exit(1);
                    }
                }   
            }
        );

        setSize(206,154);
        setVisible(true);
    }

    public void addComponent(Component component, int row, int column, int width, int height) {
        if (height > 1 && width > 1) {
            gbConstraints.fill = GridBagConstraints.BOTH;
        } else if (height > 1) {
            gbConstraints.fill = GridBagConstraints.VERTICAL;
        } else {
            gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        }
        gbConstraints.gridy = row;
        gbConstraints.gridx = column;
        gbConstraints.gridwidth = width;
        gbConstraints.gridheight = height;
        gbLayout.setConstraints(component, gbConstraints);
        add(component);
    }
}