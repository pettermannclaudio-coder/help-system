package view;

import model.TipoUsuario;
import model.Usuario;
import service.UsuarioService;
import util.SessaoUsuario;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

public class LoginView extends JFrame {

    private final UsuarioService usuarioService = new UsuarioService();

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnCadastrar;

    public LoginView() {
        configurarJanela();
        criarComponentes();
        configurarEventos();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Help System - Login");
        setSize(430, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("HELP SYSTEM", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel lblSubtitulo = new JLabel(
                "Acesse sua conta",
                SwingConstants.CENTER);

        txtEmail = new JTextField(22);
        txtSenha = new JPasswordField(22);
        btnEntrar = new JButton("Entrar");
        btnCadastrar = new JButton("Criar usuário");

        adicionar(painelPrincipal, lblTitulo, gbc, 0, 0, 2);
        adicionar(painelPrincipal, lblSubtitulo, gbc, 0, 1, 2);
        adicionar(painelPrincipal, new JLabel("E-mail:"), gbc, 0, 2, 1);
        adicionar(painelPrincipal, txtEmail, gbc, 1, 2, 1);
        adicionar(painelPrincipal, new JLabel("Senha:"), gbc, 0, 3, 1);
        adicionar(painelPrincipal, txtSenha, gbc, 1, 3, 1);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.add(btnEntrar);
//        painelBotoes.add(btnCadastrar);
        adicionar(painelPrincipal, painelBotoes, gbc, 0, 4, 2);

        add(painelPrincipal);
        getRootPane().setDefaultButton(btnEntrar);
    }

    private void adicionar(
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
        btnEntrar.addActionListener(evento -> realizarLogin());
        btnCadastrar.addActionListener(
                evento -> new CadastroView().setVisible(true));
        txtSenha.addActionListener(evento -> realizarLogin());
    }

    private void realizarLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        btnEntrar.setEnabled(false);

        try {
            Usuario usuario = usuarioService.login(email, senha);
            SessaoUsuario.iniciar(usuario);

            JOptionPane.showMessageDialog(
                    this,
                    "Bem-vindo, " + usuario.getNome() + "!",
                    "Login realizado",
                    JOptionPane.INFORMATION_MESSAGE);

            boolean administrador = usuario.getTipo() == TipoUsuario.ADMIN;
            new MenuView(administrador, SessaoUsuario.getUsuarioAtual()).setVisible(true);
            dispose();
        } catch (IllegalArgumentException e) {
            mostrarAviso(e.getMessage());
            txtSenha.setText("");
            txtSenha.requestFocus();
        } catch (RuntimeException e) {
            mostrarErro("Não foi possível consultar o usuário no banco.");
        } finally {
            btnEntrar.setEnabled(true);
        }
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Login não realizado",
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
