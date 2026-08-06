package view;

import model.Departamento;
import model.PrioridadeSolicitacao;
import model.Resposta;
import model.Solicitacao;
import model.Usuario;
import service.DepartamentoService;
import service.RespostaService;
import service.SolicitacaoService;
import util.SessaoUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class MenuView extends JFrame {

    private static final String TODOS = "Todos";
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final boolean administrador;
    private final Usuario usuario;
    private final SolicitacaoService solicitacaoService = new SolicitacaoService();
    private final RespostaService respostaService = new RespostaService();
    private final DepartamentoService departamentoService = new DepartamentoService();

    private JComboBox<Object> cbCategoria;
    private JComboBox<String> cbStatus;
    private JComboBox<String> cbOrdenacao;
    private JTextField txtColaborador;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnCadastrarUsuario;

    public MenuView(boolean administrador, Usuario usuario) {
        this.administrador = administrador;
        this.usuario = usuario;
        configurarJanela();
        criarComponentes();
        carregarCategorias();
        carregarSolicitacoes();
    }

    private void configurarJanela() {
        setTitle("Help System - Solicitações");
        setSize(1100, 650);
        setMinimumSize(new Dimension(900, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarComponentes() {
        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        principal.add(criarCabecalho(), BorderLayout.NORTH);
        principal.add(criarTabela(), BorderLayout.CENTER);
        principal.add(criarBarraAcoes(), BorderLayout.SOUTH);
        add(principal);
    }

    private JComponent criarCabecalho() {
        JPanel cabecalho = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Solicitações");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel perfil = new JLabel(usuario.getNome() + " - "
                + (administrador ? "Administrador" : "Usuário comum"));

        JPanel identificacao = new JPanel(new GridLayout(2, 1));
        identificacao.add(titulo);
        identificacao.add(perfil);
        cabecalho.add(identificacao, BorderLayout.NORTH);

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbCategoria = new JComboBox<>();
        cbStatus = new JComboBox<>(new String[]{
                TODOS, "ABERTA", "RESPONDIDA", "RESOLVIDA"});
        cbOrdenacao = new JComboBox<>(new String[]{"Data", "Prioridade"});
        txtColaborador = new JTextField(14);
        txtColaborador.setEnabled(administrador);

        JButton filtrar = new JButton("Aplicar filtros");
        JButton limpar = new JButton("Limpar");
        filtrar.addActionListener(evento -> carregarSolicitacoes());
        limpar.addActionListener(evento -> limparFiltros());

        filtros.add(new JLabel("Categoria:"));
        filtros.add(cbCategoria);
        filtros.add(new JLabel("Status:"));
        filtros.add(cbStatus);
        filtros.add(new JLabel("Colaborador:"));
        filtros.add(txtColaborador);
        filtros.add(new JLabel("Ordenar por:"));
        filtros.add(cbOrdenacao);
        filtros.add(filtrar);
        filtros.add(limpar);
        cabecalho.add(filtros, BorderLayout.CENTER);
        return cabecalho;
    }

    private JComponent criarTabela() {
        modeloTabela = new DefaultTableModel(new String[]{
                "ID", "Título", "Categoria", "Solicitante",
                "Status", "Prioridade", "Data"
        }, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoCreateRowSorter(false);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(260);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evento) {
                if (evento.getClickCount() == 2) {
                    visualizarSelecionada();
                }
            }
        });
        return new JScrollPane(tabela);
    }

    private JComponent criarBarraAcoes() {
        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton nova = new JButton("Nova solicitação");
        JButton visualizar = new JButton("Visualizar / responder");
        JButton resolver = new JButton("Marcar como resolvida");
        JButton excluir = new JButton("Excluir");
        JButton atualizar = new JButton("Atualizar");
        btnCadastrarUsuario = new JButton("Cadastrar usuário");
        JButton sair = new JButton("Sair");

        nova.addActionListener(evento -> abrirFormularioSolicitacao());
        visualizar.addActionListener(evento -> visualizarSelecionada());
        resolver.addActionListener(evento -> resolverSelecionada());
        excluir.addActionListener(evento -> excluirSelecionada());
        atualizar.addActionListener(evento -> carregarSolicitacoes());
        btnCadastrarUsuario.addActionListener(
                evento -> new CadastroView().setVisible(true));
        sair.addActionListener(evento -> sair());

        acoes.add(nova);
        acoes.add(visualizar);
        acoes.add(resolver);
        acoes.add(excluir);
        acoes.add(atualizar);
        if (administrador) {
            acoes.add(btnCadastrarUsuario);
        }
        acoes.add(sair);
        return acoes;
    }

    private void carregarCategorias() {
        cbCategoria.removeAllItems();
        cbCategoria.addItem(TODOS);
        for (Departamento departamento : departamentoService.listar()) {
            cbCategoria.addItem(departamento);
        }
    }

    private void carregarSolicitacoes() {

        try {

            Departamento categoria =
                    cbCategoria.getSelectedItem() instanceof Departamento d
                            ? d
                            : null;

            String status =
                    TODOS.equals(cbStatus.getSelectedItem())
                            ? null
                            : (String) cbStatus.getSelectedItem();

            String colaborador =
                    txtColaborador.getText().trim();

            if (colaborador.isBlank()) {
                colaborador = null;
            }

            boolean ordenarPorPrioridade =
                    "Prioridade".equals(cbOrdenacao.getSelectedItem());

            List<Solicitacao> solicitacoes =
                    solicitacaoService.buscar(
                            categoria,
                            status,
                            colaborador,
                            ordenarPorPrioridade
                    );

            modeloTabela.setRowCount(0);

            for (Solicitacao solicitacao : solicitacoes) {

                modeloTabela.addRow(new Object[]{
                        solicitacao.getId(),
                        solicitacao.getTitulo(),
                        solicitacao.getDepartamento().getNome(),
                        solicitacao.getUsuario().getNome(),
                        solicitacao.getStatus(),
                        solicitacao.getPrioridade(),
                        formatarData(solicitacao.getDataCriacao())
                });

            }

        } catch (RuntimeException erro) {

            mostrarErro(
                    "Não foi possível carregar as solicitações.",
                    erro
            );

        }

    }

    private void limparFiltros() {
        cbCategoria.setSelectedIndex(0);
        cbStatus.setSelectedIndex(0);
        cbOrdenacao.setSelectedIndex(0);
        txtColaborador.setText("");
        carregarSolicitacoes();
    }

    private void abrirFormularioSolicitacao() {
        JTextField titulo = new JTextField(28);
        JTextArea descricao = new JTextArea(6, 28);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        JComboBox<Departamento> categoria = new JComboBox<>();
        departamentoService.listar().forEach(categoria::addItem);
        JComboBox<PrioridadeSolicitacao> prioridade =
                new JComboBox<>(PrioridadeSolicitacao.values());

        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        adicionarCampo(formulario, gbc, 0, "Título:", titulo);
        adicionarCampo(formulario, gbc, 1, "Categoria:", categoria);
        adicionarCampo(formulario, gbc, 2, "Prioridade:", prioridade);
        adicionarCampo(formulario, gbc, 3, "Descrição:", new JScrollPane(descricao));

        int opcao = JOptionPane.showConfirmDialog(
                this, formulario, "Nova solicitação",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setTitulo(titulo.getText().trim());
        solicitacao.setDescricao(descricao.getText().trim());
        solicitacao.setDepartamento((Departamento) categoria.getSelectedItem());
        solicitacao.setPrioridade((PrioridadeSolicitacao) prioridade.getSelectedItem());
        solicitacao.setUsuario(usuario);
        solicitacao.setStatus("ABERTA");
        solicitacao.setDataCriacao(LocalDateTime.now());

        try {
            solicitacaoService.salvar(solicitacao);
            JOptionPane.showMessageDialog(this, "Solicitação cadastrada com sucesso!");
            carregarSolicitacoes();
        } catch (IllegalArgumentException erro) {
            mostrarAviso(erro.getMessage());
        } catch (RuntimeException erro) {
            mostrarErro("Não foi possível cadastrar a solicitação.", erro);
        }
    }

    private void visualizarSelecionada() {
        Solicitacao solicitacao = obterSelecionada();
        if (solicitacao == null) {
            return;
        }

        JDialog dialogo = new JDialog(this, "Solicitação #" + solicitacao.getId(), true);
        dialogo.setSize(720, 600);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(8, 8));

        JTextArea dados = new JTextArea(
                "Título: " + solicitacao.getTitulo()
                + "\nSolicitante: " + solicitacao.getUsuario().getNome()
                + "\nCategoria: " + solicitacao.getDepartamento().getNome()
                + "\nStatus: " + solicitacao.getStatus()
                + "\nPrioridade: " + solicitacao.getPrioridade()
                + "\nData: " + formatarData(solicitacao.getDataCriacao())
                + "\n\n" + solicitacao.getDescricao());
        dados.setEditable(false);
        dados.setLineWrap(true);
        dados.setWrapStyleWord(true);
        dados.setBorder(BorderFactory.createTitledBorder("Detalhes"));

        JTextArea respostas = new JTextArea();
        respostas.setEditable(false);
        respostas.setLineWrap(true);
        respostas.setWrapStyleWord(true);
        carregarRespostas(solicitacao, respostas);

        JTextArea novaResposta = new JTextArea(3, 40);
        novaResposta.setLineWrap(true);
        novaResposta.setWrapStyleWord(true);
        JButton responder = new JButton("Enviar resposta");
        responder.setEnabled(!"RESOLVIDA".equals(solicitacao.getStatus()));
        responder.addActionListener(evento -> {
            try {
                respostaService.responder(solicitacao, usuario, novaResposta.getText().trim());
                novaResposta.setText("");
                carregarRespostas(solicitacao, respostas);
                carregarSolicitacoes();
                JOptionPane.showMessageDialog(dialogo, "Resposta enviada com sucesso!");
            } catch (IllegalArgumentException erro) {
                mostrarAviso(erro.getMessage());
            } catch (RuntimeException erro) {
                mostrarErro("Não foi possível enviar a resposta.", erro);
            }
        });

        JPanel painelResposta = new JPanel(new BorderLayout(5, 5));
        painelResposta.setBorder(BorderFactory.createTitledBorder("Respostas"));
        painelResposta.add(new JScrollPane(respostas), BorderLayout.CENTER);
        JPanel envio = new JPanel(new BorderLayout(5, 5));
        envio.add(new JScrollPane(novaResposta), BorderLayout.CENTER);
        envio.add(responder, BorderLayout.EAST);
        painelResposta.add(envio, BorderLayout.SOUTH);

        dialogo.add(new JScrollPane(dados), BorderLayout.NORTH);
        dialogo.add(painelResposta, BorderLayout.CENTER);
        dialogo.setVisible(true);
    }

    private void carregarRespostas(Solicitacao solicitacao, JTextArea area) {
        area.setText("");
        List<Resposta> respostas = respostaService.buscarPorSolicitacao(solicitacao.getId());
        if (respostas.isEmpty()) {
            area.setText("Nenhuma resposta cadastrada.");
            return;
        }
        for (Resposta resposta : respostas) {
            area.append(resposta.getUsuario().getNome()
                    + " - " + formatarData(resposta.getDataResposta())
                    + "\n" + resposta.getDescricao()
                    + "\n\n----------------------------------------\n\n");
        }
    }

    private void resolverSelecionada() {
        Solicitacao solicitacao = obterSelecionada();
        if (solicitacao == null) {
            return;
        }
        if (!Objects.equals(usuario.getId(), solicitacao.getUsuario().getId())) {
            mostrarAviso("Não é possível alterar o status de uma solicitação que não é sua");
            return;
        }
        if (!confirmar("Marcar esta solicitação como resolvida?")) {
            return;
        }
        try {
            solicitacaoService.marcarComoResolvida(solicitacao.getId(), usuario);
            carregarSolicitacoes();
        } catch (SecurityException erro) {
            mostrarAviso(erro.getMessage());
        } catch (RuntimeException erro) {
            mostrarErro("Não foi possível resolver a solicitação.", erro);
        }
    }

    private void excluirSelecionada() {
        Solicitacao solicitacao = obterSelecionada();
        if (!Objects.equals(usuario.getId(), solicitacao.getUsuario().getId())){
            mostrarAviso("Não é possível excluir uma solicitação que não é sua");
            return;
        }

        if (solicitacao == null || !confirmar("Excluir definitivamente esta solicitação?")) {
            return;
        }
        try {
            solicitacaoService.excluirPropria(solicitacao.getId(), usuario);
            carregarSolicitacoes();
        } catch (SecurityException erro) {
            mostrarAviso(erro.getMessage());
        } catch (RuntimeException erro) {
            mostrarErro("Não foi possível excluir a solicitação.", erro);
        }
    }

    private Solicitacao obterSelecionada() {
        int linhaVisual = tabela.getSelectedRow();
        if (linhaVisual < 0) {
            mostrarAviso("Selecione uma solicitação.");
            return null;
        }
        int linhaModelo = tabela.convertRowIndexToModel(linhaVisual);
        int id = (Integer) modeloTabela.getValueAt(linhaModelo, 0);
        return solicitacaoService.buscarPorId(id);
    }

    private void adicionarCampo(
            JPanel painel, GridBagConstraints gbc, int linha,
            String rotulo, Component campo) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.weightx = 0;
        painel.add(new JLabel(rotulo), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        painel.add(campo, gbc);
    }

    private String formatarData(LocalDateTime data) {
        return data == null ? "" : data.format(FORMATO_DATA);
    }

    private boolean confirmar(String mensagem) {
        return JOptionPane.showConfirmDialog(
                this, mensagem, "Confirmação",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(
                this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarErro(String mensagem, RuntimeException erro) {
        JOptionPane.showMessageDialog(
                this, mensagem + "\n" + erro.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void sair() {
        if (confirmar("Deseja realmente sair da sua conta?")) {
            SessaoUsuario.encerrar();
            dispose();
            new LoginView();
        }
    }
}
