package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    public LoginView() {
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("Help System - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    private void criarComponentes() {
        JLabel titulo = new JLabel("HELP SYSTEM");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setBounds(145, 40, 250, 40);
        add(titulo);

        JLabel labelEmail = new JLabel("E-mail:");
        labelEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        labelEmail.setBounds(70, 130, 80, 30);
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(150, 130, 270, 35);
        add(campoEmail);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 16));
        labelSenha.setBounds(70, 190, 80, 30);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(150, 190, 270, 35);
        add(campoSenha);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setFont(new Font("Arial", Font.PLAIN, 16));
        botaoEntrar.setBounds(180, 270, 140, 45);
        add(botaoEntrar);

        botaoEntrar.addActionListener(evento -> realizarLogin());

        campoSenha.addActionListener(evento -> realizarLogin());
    }

    private void realizarLogin() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());

        if (email.isBlank()) {
            mostrarAviso("Informe o e-mail.");
            campoEmail.requestFocus();
            return;
        }

        if (senha.isBlank()) {
            mostrarAviso("Informe a senha.");
            campoSenha.requestFocus();
            return;
        }

        /*
         * Login temporário para testar as telas.
         *
         * Depois essa parte será substituída pelo UsuarioService,
         * UsuarioDAO e consulta ao banco de dados.
         */

        if (
                email.equalsIgnoreCase("admin@helpsystem.com")
                && senha.equals("123456")
        ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Login de administrador realizado com sucesso!"
            );

            new MenuView(true).setVisible(true);
            dispose();
            return;
        }

        if (
                email.equalsIgnoreCase("usuario@helpsystem.com")
                && senha.equals("123456")
        ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Login realizado com sucesso!"
            );

            new MenuView(false).setVisible(true);
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "E-mail ou senha incorretos.",
                "Login inválido",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Campo obrigatório",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView telaLogin = new LoginView();
            telaLogin.setVisible(true);
        });
    }
}