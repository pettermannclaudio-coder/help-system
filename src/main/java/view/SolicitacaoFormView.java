package view;

import model.Departamento;
import model.Solicitacao;
import model.Usuario;
import service.DepartamentoService;
import service.SolicitacaoService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class SolicitacaoFormView extends JFrame {

    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JComboBox<Departamento> cbDepartamento;

    private JButton btnSalvar;
    private JButton btnCancelar;

    private final SolicitacaoService solicitacaoService =
            new SolicitacaoService();

    private final DepartamentoService departamentoService = new DepartamentoService();


    private final Usuario usuario;

    public SolicitacaoFormView(Usuario usuario) {

        this.usuario = usuario;

        configurarJanela();

        criarComponentes();

        carregarDepartamentos();
    }

    private void configurarJanela() {

        setTitle("Nova Solicitação");

        setSize(600, 450);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    private void criarComponentes() {

        JPanel painel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8,8,8,8);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;

        painel.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1;

        txtTitulo = new JTextField(30);

        painel.add(txtTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        painel.add(new JLabel("Categoria:"), gbc);

        gbc.gridx = 1;

        cbDepartamento = new JComboBox<>();

        painel.add(cbDepartamento, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.anchor = GridBagConstraints.NORTHWEST;

        painel.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;

        txtDescricao = new JTextArea(8,30);

        txtDescricao.setLineWrap(true);

        txtDescricao.setWrapStyleWord(true);

        JScrollPane scroll =
                new JScrollPane(txtDescricao);

        painel.add(scroll, gbc);

        JPanel botoes = new JPanel();

        btnSalvar = new JButton("Salvar");

        btnCancelar = new JButton("Cancelar");

        botoes.add(btnSalvar);

        botoes.add(btnCancelar);

        add(painel, BorderLayout.CENTER);

        add(botoes, BorderLayout.SOUTH);

        configurarEventos();

    }

    private void configurarEventos() {

        btnCancelar.addActionListener(e -> dispose());

        btnSalvar.addActionListener(e -> salvar());

    }

    private void carregarDepartamentos() {

        cbDepartamento.removeAllItems();

        for (Departamento departamento :
                departamentoService.listar()) {

            cbDepartamento.addItem(departamento);

        }

    }

    private void salvar() {

        if (txtTitulo.getText().isBlank()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe o título."
            );

            return;

        }

        if (txtDescricao.getText().isBlank()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe a descrição."
            );

            return;

        }

        Departamento departamento =
                (Departamento) cbDepartamento.getSelectedItem();

        Solicitacao solicitacao =
                new Solicitacao();

        solicitacao.setTitulo(
                txtTitulo.getText()
        );

        solicitacao.setDescricao(
                txtDescricao.getText()
        );

        solicitacao.setDepartamento(
                departamento
        );

        solicitacao.setUsuario(
                usuario
        );

        solicitacao.setStatus(
                "ABERTA"
        );

        solicitacao.setDataCriacao(
                LocalDateTime.now()
        );

        solicitacaoService.salvar(solicitacao);

        JOptionPane.showMessageDialog(
                this,
                "Solicitação cadastrada com sucesso!"
        );

        dispose();

    }

}