package view;

import dao.DepartamentoDAO;
import model.Departamento;
import service.UsuarioService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

public class CadastroView extends JFrame {

    private final UsuarioService usuarioService = new UsuarioService();
    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;
    private JComboBox<String> comboPerfil;
    private JComboBox<Departamento> comboDepartamento;
    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnFechar;

    public CadastroView() {
        configurarJanela();
        criarComponentes();
        configurarEventos();
        carregarDepartamentos();
    }

    private void configurarJanela() {
        setTitle("Help System - Cadastro de Usuário");
        setSize(520, 510);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(
                "Cadastro de usuário",
                SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 23));

        txtNome = new JTextField(24);
        txtEmail = new JTextField(24);
        txtSenha = new JPasswordField(24);
        txtConfirmarSenha = new JPasswordField(24);

        comboPerfil = new JComboBox<>(new String[] { "COMUM" });
        comboPerfil.setEnabled(false);
        comboDepartamento = new JComboBox<>();

        btnCadastrar = new JButton("Cadastrar");
        btnLimpar = new JButton("Limpar");
        btnFechar = new JButton("Fechar");

        adicionarComponente(painelPrincipal, lblTitulo, gbc, 0, 0, 2);
        adicionarCampo(painelPrincipal, gbc, "Nome:", txtNome, 1);
        adicionarCampo(painelPrincipal, gbc, "E-mail:", txtEmail, 2);
        adicionarCampo(painelPrincipal, gbc, "Senha:", txtSenha, 3);
        adicionarCampo(
                painelPrincipal,
                gbc,
                "Confirmar senha:",
                txtConfirmarSenha,
                4);
        adicionarCampo(painelPrincipal, gbc, "Perfil:", comboPerfil, 5);
        adicionarCampo(
                painelPrincipal,
                gbc,
                "Departamento:",
                comboDepartamento,
                6);

        JPanel painelBotoes = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFechar);
        adicionarComponente(painelPrincipal, painelBotoes, gbc, 0, 7, 2);

        add(painelPrincipal);
        getRootPane().setDefaultButton(btnCadastrar);
    }

    private void adicionarCampo(
            JPanel painel,
            GridBagConstraints gbc,
            String rotulo,
            java.awt.Component campo,
            int linha) {
        adicionarComponente(painel, new JLabel(rotulo), gbc, 0, linha, 1);
        adicionarComponente(painel, campo, gbc, 1, linha, 1);
    }

    private void adicionarComponente(
            JPanel painel,
            java.awt.Component componente,
            GridBagConstraints gbc,
            int coluna,
            int linha,
            int largura) {
        gbc.gridx = coluna;
        gbc.gridy = linha;
        gbc.gridwidth = largura;
        painel.add(componente, gbc);
    }

    private void configurarEventos() {
        btnCadastrar.addActionListener(evento -> cadastrar());
        btnLimpar.addActionListener(evento -> limparCampos());
        btnFechar.addActionListener(evento -> dispose());
    }

    private void carregarDepartamentos() {
        comboDepartamento.removeAllItems();

        try {
            List<Departamento> departamentos = departamentoDAO.listar();

            for (Departamento departamento : departamentos) {
                comboDepartamento.addItem(departamento);
            }

            comboDepartamento.setSelectedIndex(-1);
        } catch (RuntimeException e) {
            btnCadastrar.setEnabled(false);
            mostrarErro("Não foi possível carregar os departamentos.");
        }
    }

    private void cadastrar() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        String confirmarSenha = new String(txtConfirmarSenha.getPassword());
        Departamento departamento = (Departamento) comboDepartamento.getSelectedItem();

        if (!senha.equals(confirmarSenha)) {
            mostrarAviso("A senha e a confirmação são diferentes.");
            txtConfirmarSenha.setText("");
            txtConfirmarSenha.requestFocus();
            return;
        }

        btnCadastrar.setEnabled(false);

        try {
            usuarioService.cadastrar(nome, email, senha, departamento);
            JOptionPane.showMessageDialog(
                    this,
                    "Usuário cadastrado com sucesso!",
                    "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (IllegalArgumentException e) {
            mostrarAviso(e.getMessage());
        } catch (RuntimeException e) {
            mostrarErro("Não foi possível cadastrar o usuário no banco.");
        } finally {
            btnCadastrar.setEnabled(true);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
        txtConfirmarSenha.setText("");
        comboDepartamento.setSelectedIndex(-1);
        txtNome.requestFocus();
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }
}
