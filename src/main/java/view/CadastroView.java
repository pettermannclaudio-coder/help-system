package view;

import javax.swing.*;
import java.awt.*;

public class CadastroView extends JFrame {

    private JTextField campoNome;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmarSenha;
    private JComboBox<String> campoDepartamento;
    private JComboBox<String> campoPerfil;

    public CadastroView() {
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("Help System - Cadastro de Usuário");
        setSize(580, 630);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    private void criarComponentes() {
        JLabel titulo = new JLabel("CADASTRAR USUÁRIO");
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setBounds(150, 30, 320, 40);
        add(titulo);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(70, 100, 140, 30);
        add(labelNome);

        campoNome = new JTextField();
        campoNome.setBounds(220, 100, 280, 35);
        add(campoNome);

        JLabel labelEmail = new JLabel("E-mail:");
        labelEmail.setBounds(70, 155, 140, 30);
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(220, 155, 280, 35);
        add(campoEmail);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(70, 210, 140, 30);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(220, 210, 280, 35);
        add(campoSenha);

        JLabel labelConfirmar = new JLabel("Confirmar senha:");
        labelConfirmar.setBounds(70, 265, 140, 30);
        add(labelConfirmar);

        campoConfirmarSenha = new JPasswordField();
        campoConfirmarSenha.setBounds(220, 265, 280, 35);
        add(campoConfirmarSenha);

        JLabel labelDepartamento = new JLabel("Departamento:");
        labelDepartamento.setBounds(70, 320, 140, 30);
        add(labelDepartamento);

        String[] departamentos = {
                "Selecione",
                "Tecnologia da Informação",
                "Recursos Humanos",
                "Financeiro",
                "Comercial",
                "Atendimento",
                "Administrativo"
        };

        campoDepartamento = new JComboBox<>(departamentos);
        campoDepartamento.setBounds(220, 320, 280, 35);
        add(campoDepartamento);

        JLabel labelPerfil = new JLabel("Perfil:");
        labelPerfil.setBounds(70, 375, 140, 30);
        add(labelPerfil);

        String[] perfis = {
                "COLABORADOR",
                "ADMIN"
        };

        campoPerfil = new JComboBox<>(perfis);
        campoPerfil.setBounds(220, 375, 280, 35);
        add(campoPerfil);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(205, 465, 160, 45);
        add(botaoCadastrar);

        botaoCadastrar.addActionListener(
                evento -> cadastrarUsuario()
        );
    }

    private void cadastrarUsuario() {
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());

        String confirmarSenha =
                new String(campoConfirmarSenha.getPassword());

        String departamento =
                campoDepartamento.getSelectedItem().toString();

        String perfil =
                campoPerfil.getSelectedItem().toString();

        if (nome.isBlank()) {
            mostrarAviso("Informe o nome.");
            campoNome.requestFocus();
            return;
        }

        if (email.isBlank() || !email.contains("@")) {
            mostrarAviso("Informe um e-mail válido.");
            campoEmail.requestFocus();
            return;
        }

        if (senha.length() < 6) {
            mostrarAviso(
                    "A senha deve ter pelo menos 6 caracteres."
            );
            campoSenha.requestFocus();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            mostrarAviso("As senhas não são iguais.");
            campoConfirmarSenha.requestFocus();
            return;
        }

        if (departamento.equals("Selecione")) {
            mostrarAviso("Selecione o departamento.");
            campoDepartamento.requestFocus();
            return;
        }

        /*
         * Depois esta parte chamará:
         *
         * Usuario usuario = new Usuario();
         * usuario.setNome(nome);
         * usuario.setEmail(email);
         * usuario.setSenha(senha);
         * usuario.setDepartamento(departamento);
         * usuario.setPerfil(perfil);
         *
         * UsuarioService service = new UsuarioService();
         * service.cadastrar(usuario);
         */

        JOptionPane.showMessageDialog(
                this,
                "Usuário cadastrado com sucesso!\n"
                        + "Nome: " + nome + "\n"
                        + "Departamento: " + departamento + "\n"
                        + "Perfil: " + perfil,
                "Cadastro concluído",
                JOptionPane.INFORMATION_MESSAGE
        );

        limparCampos();
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Dados inválidos",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void limparCampos() {
        campoNome.setText("");
        campoEmail.setText("");
        campoSenha.setText("");
        campoConfirmarSenha.setText("");
        campoDepartamento.setSelectedIndex(0);
        campoPerfil.setSelectedIndex(0);
        campoNome.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CadastroView().setVisible(true);
        });
    }
}