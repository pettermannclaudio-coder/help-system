package view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    private final boolean administrador;

    private JButton btnNovaSolicitacao;
    private JButton btnListarSolicitacoes;
    private JButton btnCadastrarUsuario;
    private JButton btnSair;

    public MenuView(boolean administrador) {
        this.administrador = administrador;

        configurarJanela();
        criarComponentes();
        configurarEventos();
    }

    private void configurarJanela() {
        setTitle("Help System - Menu Principal");
        setSize(520, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel(
                new BorderLayout(15, 15)
        );

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(25, 40, 25, 40)
        );

        JLabel lblTitulo = new JLabel(
                "HELP SYSTEM",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        String nomePerfil;

        if (administrador) {
            nomePerfil = "Administrador";
        } else {
            nomePerfil = "Colaborador";
        }

        JLabel lblPerfil = new JLabel(
                "Perfil conectado: " + nomePerfil,
                SwingConstants.CENTER
        );

        JPanel painelCabecalho = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        painelCabecalho.add(lblTitulo);
        painelCabecalho.add(lblPerfil);

        btnNovaSolicitacao =
                new JButton("Criar nova solicitação");

        btnListarSolicitacoes =
                new JButton("Visualizar solicitações");

        btnCadastrarUsuario =
                new JButton("Cadastrar usuário");

        btnSair =
                new JButton("Sair");

        JPanel painelBotoes = new JPanel(
                new GridLayout(4, 1, 10, 10)
        );

        painelBotoes.add(btnNovaSolicitacao);
        painelBotoes.add(btnListarSolicitacoes);

        /*
         * O botão de cadastro só aparece
         * quando o usuário é administrador.
         */
        if (administrador) {
            painelBotoes.add(btnCadastrarUsuario);
        }

        painelBotoes.add(btnSair);

        painelPrincipal.add(
                painelCabecalho,
                BorderLayout.NORTH
        );

        painelPrincipal.add(
                painelBotoes,
                BorderLayout.CENTER
        );

        add(painelPrincipal);
    }

    private void configurarEventos() {
        btnCadastrarUsuario.addActionListener(evento -> {
            new CadastroView().setVisible(true);
        });

        btnNovaSolicitacao.addActionListener(evento -> {
            JOptionPane.showMessageDialog(
                    this,
                    "A tela de nova solicitação será criada posteriormente.",
                    "Nova solicitação",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnListarSolicitacoes.addActionListener(evento -> {
            JOptionPane.showMessageDialog(
                    this,
                    "A tela de solicitações será criada posteriormente.",
                    "Solicitações",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnSair.addActionListener(evento -> sair());
    }

    private void sair() {
        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente sair da sua conta?",
                "Confirmar saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {
            dispose();
            new LoginView();
        }
    }
}
