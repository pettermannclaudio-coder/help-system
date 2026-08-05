package view;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class CadastroView extends JFrame {

    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;

    private JComboBox<String> comboPerfil;
    private JComboBox<String> comboDepartamento;

    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnFechar;

    public CadastroView() {
        configurarJanela();
        criarComponentes();
        configurarEventos();
    }

    private void configurarJanela() {
        setTitle("Help System - Cadastro de Usuário");
        setSize(520, 510);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel(
                new GridBagLayout()
        );

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(
                "Cadastro de usuário",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 23)
        );

        txtNome = new JTextField(24);
        txtEmail = new JTextField(24);
        txtSenha = new JPasswordField(24);
        txtConfirmarSenha = new JPasswordField(24);

        comboPerfil = new JComboBox<>(
                new String[]{
                    "COLABORADOR",
                    "ADMIN"
                }
        );

        comboDepartamento = new JComboBox<>(
                new String[]{
                    "Tecnologia",
                    "Recursos Humanos",
                    "Financeiro",
                    "Comercial",
                    "Atendimento",
                    "Administrativo"
                }
        );

        btnCadastrar = new JButton("Cadastrar");
        btnLimpar = new JButton("Limpar");
        btnFechar = new JButton("Fechar");

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        painelPrincipal.add(lblTitulo, gbc);

        // Nome
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;

        painelPrincipal.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        painelPrincipal.add(txtNome, gbc);

        // E-mail
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

        // Confirmar senha
        gbc.gridx = 0;
        gbc.gridy = 4;

        painelPrincipal.add(
                new JLabel("Confirmar senha:"),
                gbc
        );

        gbc.gridx = 1;
        painelPrincipal.add(txtConfirmarSenha, gbc);

        // Perfil
        gbc.gridx = 0;
        gbc.gridy = 5;

        painelPrincipal.add(new JLabel("Perfil:"), gbc);

        gbc.gridx = 1;
        painelPrincipal.add(comboPerfil, gbc);

        // Departamento
        gbc.gridx = 0;
        gbc.gridy = 6;

        painelPrincipal.add(
                new JLabel("Departamento:"),
                gbc
        );

        gbc.gridx = 1;
        painelPrincipal.add(comboDepartamento, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 0)
        );

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFechar);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;

        painelPrincipal.add(painelBotoes, gbc);

        add(painelPrincipal);
    }

    private void configurarEventos() {
        btnCadastrar.addActionListener(
                evento -> cadastrar()
        );

        btnLimpar.addActionListener(
                evento -> limparCampos()
        );

        btnFechar.addActionListener(
                evento -> dispose()
        );
    }

    private void cadastrar() {
        String nome = txtNome.getText().trim();

        String email = txtEmail.getText()
                .trim()
                .toLowerCase();

        String senha = new String(
                txtSenha.getPassword()
        );

        String confirmarSenha = new String(
                txtConfirmarSenha.getPassword()
        );

        String perfil = (String)
                comboPerfil.getSelectedItem();

        String departamento = (String)
                comboDepartamento.getSelectedItem();

        if (nome.isBlank()) {
            mostrarAviso("Informe o nome do usuário.");
            txtNome.requestFocus();
            return;
        }

        if (!emailValido(email)) {
            mostrarAviso("Informe um e-mail válido.");
            txtEmail.requestFocus();
            return;
        }

        if (senha.length() < 6) {
            mostrarAviso(
                    "A senha deve possuir pelo menos 6 caracteres."
            );

            txtSenha.requestFocus();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            mostrarAviso(
                    "A senha e a confirmação são diferentes."
            );

            txtConfirmarSenha.setText("");
            txtConfirmarSenha.requestFocus();
            return;
        }

        if (perfil == null) {
            mostrarAviso("Selecione o perfil.");
            return;
        }

        if (departamento == null) {
            mostrarAviso("Selecione o departamento.");
            return;
        }

        /*
         * Neste momento estamos apenas testando a interface.
         * Depois este trecho chamará:
         *
         * UsuarioDAO usuarioDAO = new UsuarioDAO();
         * usuarioDAO.salvar(usuario);
         */

        JOptionPane.showMessageDialog(
                this,
                "Usuário cadastrado com sucesso!\n\n"
                + "Nome: " + nome
                + "\nE-mail: " + email
                + "\nPerfil: " + perfil
                + "\nDepartamento: " + departamento,
                "Cadastro realizado",
                JOptionPane.INFORMATION_MESSAGE
        );

        limparCampos();
    }

    private boolean emailValido(String email) {
        String formato =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return Pattern.matches(formato, email);
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
        txtConfirmarSenha.setText("");

        comboPerfil.setSelectedIndex(0);
        comboDepartamento.setSelectedIndex(0);

        txtNome.requestFocus();
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );
    }
}