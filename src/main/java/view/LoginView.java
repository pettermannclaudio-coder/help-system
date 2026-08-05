package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public LoginView() {
        configurarJanela();
        criarComponentes();
        configurarEventos();

        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Help System - Login");
        setSize(430, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(
                "HELP SYSTEM",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JLabel lblSubtitulo = new JLabel(
                "Acesse sua conta",
                SwingConstants.CENTER
        );

        txtEmail = new JTextField(22);
        txtSenha = new JPasswordField(22);

        btnEntrar = new JButton("Entrar");

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        painelPrincipal.add(lblTitulo, gbc);

        // Subtítulo
        gbc.gridy = 1;
        painelPrincipal.add(lblSubtitulo, gbc);

        // E-mail
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;

        painelPrincipal.add(new JLabel("E-mail:"), gbc);

        gbc.gridx = 1;
        painelPrincipal.add(txtEmail, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 3;

        painelPrincipal.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        painelPrincipal.add(txtSenha, gbc);

        // Botão
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        painelPrincipal.add(btnEntrar, gbc);

        add(painelPrincipal);
    }

    private void configurarEventos() {
        btnEntrar.addActionListener(evento -> realizarLogin());

        /*
         * Permite fazer login pressionando Enter
         * no campo de senha.
         */
        txtSenha.addActionListener(evento -> realizarLogin());
    }

    private void realizarLogin() {
        String email = txtEmail.getText()
                .trim()
                .toLowerCase();

        String senha = new String(
                txtSenha.getPassword()
        );

        if (email.isBlank() || senha.isBlank()) {
            mostrarAviso("Preencha o e-mail e a senha.");
            return;
        }

        /*
         * LOGIN TEMPORÁRIO DO ADMINISTRADOR
         */
        if (
            email.equalsIgnoreCase("admin@helpsystem.com")
            && senha.equals("123456")
        ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Login de administrador realizado com sucesso!",
                    "Login realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            new MenuView(true).setVisible(true);

            dispose();
            return;
        }

        /*
         * LOGIN TEMPORÁRIO DO COLABORADOR
         */
        if (
            email.equalsIgnoreCase("usuario@helpsystem.com")
            && senha.equals("123456")
        ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Login de colaborador realizado com sucesso!",
                    "Login realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            new MenuView(false).setVisible(true);

            dispose();
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "E-mail ou senha incorretos.",
                "Login não realizado",
                JOptionPane.ERROR_MESSAGE
        );

        txtSenha.setText("");
        txtSenha.requestFocus();
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception erro) {
                System.out.println(
                        "Não foi possível aplicar o visual do sistema."
                );
            }

            new LoginView();
        });
    }
}