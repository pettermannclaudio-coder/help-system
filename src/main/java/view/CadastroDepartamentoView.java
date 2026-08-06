package view;

import model.Departamento;
import service.DepartamentoService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class CadastroDepartamentoView extends JFrame {

    private final DepartamentoService departamentoService =
            new DepartamentoService();

    private JTextField txtNome;

    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnFechar;

    public CadastroDepartamentoView() {

        configurarJanela();

        criarComponentes();

        configurarEventos();

    }

    private void configurarJanela() {

        setTitle("Help System - Cadastro de Departamento");

        setSize(450, 240);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(false);

    }

    private void criarComponentes() {

        JPanel painelPrincipal =
                new JPanel(new GridBagLayout());

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(8,8,8,8);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo =
                new JLabel(
                        "Cadastro de Departamento",
                        SwingConstants.CENTER
                );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        txtNome = new JTextField(25);

        btnCadastrar = new JButton("Cadastrar");

        btnLimpar = new JButton("Limpar");

        btnFechar = new JButton("Fechar");

        adicionarComponente(
                painelPrincipal,
                lblTitulo,
                gbc,
                0,
                0,
                2
        );

        adicionarCampo(
                painelPrincipal,
                gbc,
                "Nome:",
                txtNome,
                1
        );

        JPanel painelBotoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                0
                        )
                );

        painelBotoes.add(btnCadastrar);

        painelBotoes.add(btnLimpar);

        painelBotoes.add(btnFechar);

        adicionarComponente(
                painelPrincipal,
                painelBotoes,
                gbc,
                0,
                2,
                2
        );

        add(painelPrincipal);

        getRootPane().setDefaultButton(btnCadastrar);

    }

    private void adicionarCampo(
            JPanel painel,
            GridBagConstraints gbc,
            String rotulo,
            Component campo,
            int linha) {

        adicionarComponente(
                painel,
                new JLabel(rotulo),
                gbc,
                0,
                linha,
                1
        );

        adicionarComponente(
                painel,
                campo,
                gbc,
                1,
                linha,
                1
        );

    }

    private void adicionarComponente(
            JPanel painel,
            Component componente,
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

        btnCadastrar.addActionListener(e -> cadastrar());

        btnLimpar.addActionListener(e -> limparCampos());

        btnFechar.addActionListener(e -> dispose());

    }

    private void cadastrar() {

        String nome = txtNome.getText().trim();

        if (nome.isBlank()) {

            mostrarAviso("Informe o nome do departamento.");

            txtNome.requestFocus();

            return;

        }

        Departamento departamento =
                new Departamento();

        departamento.setNome(nome);

        btnCadastrar.setEnabled(false);

        try {

            departamentoService.salvar(departamento);

            JOptionPane.showMessageDialog(
                    this,
                    "Departamento cadastrado com sucesso!",
                    "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limparCampos();

        } catch (IllegalArgumentException e) {

            mostrarAviso(e.getMessage());

        } catch (RuntimeException e) {

            mostrarErro(
                    "Não foi possível cadastrar o departamento."
            );

        } finally {

            btnCadastrar.setEnabled(true);

        }

    }

    private void limparCampos() {

        txtNome.setText("");

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

    private void mostrarErro(String mensagem) {

        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );

    }

}