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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class MenuView extends JFrame {

    private static final String TODOS = "Todos";

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final Color COR_FUNDO = Color.decode("#F1F5F9");
    private static final Color COR_AZUL = Color.decode("#3853DC");
    private static final Color COR_AZUL_ESCURO = Color.decode("#2F46C7");
    private static final Color COR_TEXTO = Color.decode("#1E293B");
    private static final Color COR_SECUNDARIA = Color.decode("#64748B");
    private static final Color COR_BORDA = Color.decode("#CBD5E1");
    private static final Color COR_BRANCO = Color.WHITE;
    private static final Color COR_PERIGO = Color.decode("#DC2626");
    private static final Color COR_SUCESSO = Color.decode("#16A34A");

    private final boolean administrador;
    private final Usuario usuario;

    private final SolicitacaoService solicitacaoService =
            new SolicitacaoService();

    private final RespostaService respostaService =
            new RespostaService();

    private final DepartamentoService departamentoService =
            new DepartamentoService();

    private JComboBox<Object> cbCategoria;
    private JComboBox<String> cbStatus;
    private JComboBox<String> cbOrdenacao;
    private JTextField txtColaborador;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnCadastrarUsuario;
    private JButton btnCadastrarDepartamento;

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
        setSize(1180, 760);
        setMinimumSize(new Dimension(980, 680));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void criarComponentes() {
        JPanel principal = new JPanel(new BorderLayout(0, 18));
        principal.setBackground(COR_FUNDO);
        principal.setBorder(new EmptyBorder(22, 24, 22, 24));

        principal.add(criarCabecalho(), BorderLayout.NORTH);
        principal.add(criarConteudoCentral(), BorderLayout.CENTER);
        principal.add(criarBarraAcoes(), BorderLayout.SOUTH);

        setContentPane(principal);
    }

    private JComponent criarCabecalho() {
        RoundedPanel cabecalho = new RoundedPanel(18, COR_BRANCO);
        cabecalho.setLayout(new BorderLayout(20, 16));
        cabecalho.setBorder(new EmptyBorder(20, 22, 20, 22));

        JPanel identidade = new JPanel(new BorderLayout(14, 0));
        identidade.setOpaque(false);

        RoundedPanel logo = new RoundedPanel(16, COR_AZUL);
        logo.setPreferredSize(new Dimension(56, 56));
        logo.setMinimumSize(new Dimension(56, 56));
        logo.setMaximumSize(new Dimension(56, 56));
        logo.setLayout(new GridBagLayout());

        JLabel letraLogo = new JLabel("H");
        letraLogo.setForeground(Color.WHITE);
        letraLogo.setFont(new Font("SansSerif", Font.BOLD, 24));
        logo.add(letraLogo);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("HELP SYSTEM");
        titulo.setForeground(COR_TEXTO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 25));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        String perfilTexto = administrador ? "Administrador" : "Colaborador";

        JLabel perfil = new JLabel(
                "Olá, " + usuario.getNome() + "  •  " + perfilTexto
        );
        perfil.setForeground(COR_SECUNDARIA);
        perfil.setFont(new Font("SansSerif", Font.PLAIN, 13));
        perfil.setAlignmentX(Component.LEFT_ALIGNMENT);

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(5));
        textos.add(perfil);

        identidade.add(logo, BorderLayout.WEST);
        identidade.add(textos, BorderLayout.CENTER);

        JLabel tituloSecao = new JLabel("Solicitações");
        tituloSecao.setForeground(COR_AZUL);
        tituloSecao.setFont(new Font("SansSerif", Font.BOLD, 20));
        tituloSecao.setHorizontalAlignment(SwingConstants.RIGHT);

        cabecalho.add(identidade, BorderLayout.WEST);
        cabecalho.add(tituloSecao, BorderLayout.EAST);

        return cabecalho;
    }

    private JComponent criarConteudoCentral() {
        JPanel conteudo = new JPanel(new BorderLayout(0, 14));
        conteudo.setOpaque(false);

        conteudo.add(criarPainelFiltros(), BorderLayout.NORTH);
        conteudo.add(criarTabela(), BorderLayout.CENTER);

        return conteudo;
    }

    private JComponent criarPainelFiltros() {
        RoundedPanel filtros = new RoundedPanel(18, COR_BRANCO);
        filtros.setLayout(new GridBagLayout());
        filtros.setBorder(new EmptyBorder(18, 20, 18, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        cbCategoria = new JComboBox<>();
        cbStatus = new JComboBox<>(new String[]{
                TODOS, "ABERTA", "RESPONDIDA", "RESOLVIDA"
        });
        cbOrdenacao = new JComboBox<>(new String[]{
                "Data", "Prioridade"
        });

        txtColaborador = new JTextField();
        txtColaborador.setEnabled(administrador);

        configurarCombo(cbCategoria);
        configurarCombo(cbStatus);
        configurarCombo(cbOrdenacao);
        configurarCampoTexto(txtColaborador);

        adicionarFiltro(filtros, gbc, 0, "Categoria", cbCategoria, 1.1);
        adicionarFiltro(filtros, gbc, 1, "Status", cbStatus, 0.8);
        adicionarFiltro(filtros, gbc, 2, "Colaborador", txtColaborador, 1.2);
        adicionarFiltro(filtros, gbc, 3, "Ordenar por", cbOrdenacao, 0.8);

        JButton filtrar = criarBotaoPrimario("Aplicar filtros");
        JButton limpar = criarBotaoSecundario("Limpar");

        filtrar.addActionListener(evento -> carregarSolicitacoes());
        limpar.addActionListener(evento -> limparFiltros());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.add(limpar);
        painelBotoes.add(filtrar);

        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        filtros.add(painelBotoes, gbc);

        return filtros;
    }

    private void adicionarFiltro(
            JPanel painel,
            GridBagConstraints gbc,
            int coluna,
            String texto,
            JComponent componente,
            double peso
    ) {
        JLabel label = new JLabel(texto.toUpperCase());
        label.setForeground(COR_SECUNDARIA);
        label.setFont(new Font("SansSerif", Font.BOLD, 10));

        gbc.gridx = coluna;
        gbc.gridy = 0;
        gbc.weightx = peso;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(label, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(componente, gbc);
    }

    private JComponent criarTabela() {
        modeloTabela = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Título",
                        "Categoria",
                        "Solicitante",
                        "Status",
                        "Prioridade",
                        "Data"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoCreateRowSorter(true);
        tabela.setRowHeight(34);
        tabela.setShowVerticalLines(false);
        tabela.setGridColor(COR_BORDA);
        tabela.setSelectionBackground(Color.decode("#E0E7FF"));
        tabela.setSelectionForeground(COR_TEXTO);
        tabela.setForeground(COR_TEXTO);
        tabela.setBackground(Color.WHITE);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabela.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader cabecalhoTabela = tabela.getTableHeader();
        cabecalhoTabela.setBackground(COR_AZUL);
        cabecalhoTabela.setForeground(Color.WHITE);
        cabecalhoTabela.setFont(new Font("SansSerif", Font.BOLD, 11));
        cabecalhoTabela.setPreferredSize(new Dimension(0, 38));
        cabecalhoTabela.setReorderingAllowed(false);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(130);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(95);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(130);

        centralizarColunas(0, 4, 5, 6);

        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evento) {
                if (evento.getClickCount() == 2) {
                    visualizarSelecionada();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createLineBorder(COR_BORDA, 1));
        scroll.getViewport().setBackground(Color.WHITE);

        RoundedPanel painelTabela = new RoundedPanel(18, COR_BRANCO);
        painelTabela.setLayout(new BorderLayout());
        painelTabela.setBorder(new EmptyBorder(1, 1, 1, 1));
        painelTabela.add(scroll, BorderLayout.CENTER);

        return painelTabela;
    }

    private void centralizarColunas(int... colunas) {
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int coluna : colunas) {
            tabela.getColumnModel()
                    .getColumn(coluna)
                    .setCellRenderer(centralizado);
        }
    }

    private JComponent criarBarraAcoes() {
        RoundedPanel barra = new RoundedPanel(18, COR_BRANCO);

        barra.setLayout(
                new BoxLayout(
                        barra,
                        BoxLayout.Y_AXIS
                )
        );

        barra.setBorder(
                new EmptyBorder(
                        14,
                        18,
                        14,
                        18
                )
        );

        JPanel linhaSolicitacoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                8,
                                0
                        )
                );

        linhaSolicitacoes.setOpaque(false);
        linhaSolicitacoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel linhaAdministracao =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                8,
                                0
                        )
                );

        linhaAdministracao.setOpaque(false);
        linhaAdministracao.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton nova =
                criarBotaoPrimario("Nova solicitação");

        JButton visualizar =
                criarBotaoSecundario("Visualizar / responder");

        JButton resolver =
                criarBotaoSucesso("Marcar como resolvida");

        JButton excluir =
                criarBotaoPerigo("Excluir");

        JButton atualizar =
                criarBotaoSecundario("Atualizar");

        btnCadastrarUsuario =
                criarBotaoSecundario("Cadastrar usuário");

        btnCadastrarDepartamento =
                criarBotaoSecundario("Cadastrar departamento");

        JButton sair =
                criarBotaoPerigo("Sair");

        nova.addActionListener(
                evento -> abrirFormularioSolicitacao()
        );

        visualizar.addActionListener(
                evento -> visualizarSelecionada()
        );

        resolver.addActionListener(
                evento -> resolverSelecionada()
        );

        excluir.addActionListener(
                evento -> excluirSelecionada()
        );

        atualizar.addActionListener(
                evento -> carregarSolicitacoes()
        );

        btnCadastrarUsuario.addActionListener(
                evento -> new CadastroView().setVisible(true)
        );

        btnCadastrarDepartamento.addActionListener(
                evento -> new CadastroDepartamentoView().setVisible(true)
        );

        sair.addActionListener(
                evento -> sair()
        );

        linhaSolicitacoes.add(nova);
        linhaSolicitacoes.add(visualizar);
        linhaSolicitacoes.add(resolver);
        linhaSolicitacoes.add(excluir);
        linhaSolicitacoes.add(atualizar);

        if (administrador) {
            linhaAdministracao.add(btnCadastrarUsuario);
            linhaAdministracao.add(btnCadastrarDepartamento);
        }

        linhaAdministracao.add(sair);

        barra.add(linhaSolicitacoes);
        barra.add(Box.createVerticalStrut(10));
        barra.add(linhaAdministracao);

        return barra;
    }

    private JButton criarBotaoPrimario(String texto) {
        return criarBotao(texto, COR_AZUL, Color.WHITE, COR_AZUL);
    }

    private JButton criarBotaoSecundario(String texto) {
        return criarBotao(texto, Color.WHITE, COR_AZUL, COR_AZUL);
    }

    private JButton criarBotaoPerigo(String texto) {
        return criarBotao(texto, Color.WHITE, COR_PERIGO, COR_PERIGO);
    }

    private JButton criarBotaoSucesso(String texto) {
        return criarBotao(texto, Color.WHITE, COR_SUCESSO, COR_SUCESSO);
    }

    private JButton criarBotao(
            String texto,
            Color fundo,
            Color textoCor,
            Color borda
    ) {
        RoundedButton botao = new RoundedButton(texto, 10);
        botao.setBackground(fundo);
        botao.setForeground(textoCor);
        botao.setBorderColor(borda);
        botao.setFont(new Font("SansSerif", Font.BOLD, 11));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(calcularLarguraBotao(texto), 38));
        botao.setFocusPainted(false);
        return botao;
    }

    private int calcularLarguraBotao(String texto) {
        return Math.max(92, texto.length() * 8 + 28);
    }

    private void configurarCombo(JComboBox<?> combo) {
        combo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
        combo.setForeground(COR_TEXTO);
        combo.setPreferredSize(new Dimension(150, 36));
    }

    private void configurarCampoTexto(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        campo.setForeground(COR_TEXTO);
        campo.setBackground(Color.WHITE);
        campo.setPreferredSize(new Dimension(170, 36));
        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_BORDA, 1),
                        new EmptyBorder(7, 9, 7, 9)
                )
        );
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
                    cbCategoria.getSelectedItem() instanceof Departamento departamento
                            ? departamento
                            : null;

            String status =
                    TODOS.equals(cbStatus.getSelectedItem())
                            ? null
                            : (String) cbStatus.getSelectedItem();

            String colaborador = txtColaborador.getText().trim();

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

        configurarCampoTexto(titulo);
        configurarCombo(categoria);
        configurarCombo(prioridade);

        descricao.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descricao.setBorder(new EmptyBorder(8, 8, 8, 8));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(new EmptyBorder(8, 8, 8, 8));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        adicionarCampo(formulario, gbc, 0, "Título:", titulo);
        adicionarCampo(formulario, gbc, 1, "Categoria:", categoria);
        adicionarCampo(formulario, gbc, 2, "Prioridade:", prioridade);
        adicionarCampo(
                formulario,
                gbc,
                3,
                "Descrição:",
                new JScrollPane(descricao)
        );

        int opcao = JOptionPane.showConfirmDialog(
                this,
                formulario,
                "Nova solicitação",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setTitulo(titulo.getText().trim());
        solicitacao.setDescricao(descricao.getText().trim());
        solicitacao.setDepartamento(
                (Departamento) categoria.getSelectedItem()
        );
        solicitacao.setPrioridade(
                (PrioridadeSolicitacao) prioridade.getSelectedItem()
        );
        solicitacao.setUsuario(usuario);
        solicitacao.setStatus("ABERTA");
        solicitacao.setDataCriacao(LocalDateTime.now());

        try {
            solicitacaoService.salvar(solicitacao);

            JOptionPane.showMessageDialog(
                    this,
                    "Solicitação cadastrada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            carregarSolicitacoes();

        } catch (IllegalArgumentException erro) {
            mostrarAviso(erro.getMessage());

        } catch (RuntimeException erro) {
            mostrarErro(
                    "Não foi possível cadastrar a solicitação.",
                    erro
            );
        }
    }

    private void visualizarSelecionada() {
        Solicitacao solicitacao = obterSelecionada();

        if (solicitacao == null) {
            return;
        }

        JDialog dialogo = new JDialog(
                this,
                "Solicitação #" + solicitacao.getId(),
                true
        );

        dialogo.setSize(760, 620);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(12, 12));

        JPanel principal = new JPanel(new BorderLayout(12, 12));
        principal.setBackground(COR_FUNDO);
        principal.setBorder(new EmptyBorder(16, 16, 16, 16));

        JTextArea dados = new JTextArea(
                "Título: " + solicitacao.getTitulo()
                        + "\nSolicitante: " + solicitacao.getUsuario().getNome()
                        + "\nCategoria: " + solicitacao.getDepartamento().getNome()
                        + "\nStatus: " + solicitacao.getStatus()
                        + "\nPrioridade: " + solicitacao.getPrioridade()
                        + "\nData: " + formatarData(solicitacao.getDataCriacao())
                        + "\n\n" + solicitacao.getDescricao()
        );

        dados.setEditable(false);
        dados.setLineWrap(true);
        dados.setWrapStyleWord(true);
        dados.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dados.setBackground(Color.WHITE);
        dados.setForeground(COR_TEXTO);
        dados.setBorder(new EmptyBorder(12, 12, 12, 12));

        JTextArea respostas = new JTextArea();
        respostas.setEditable(false);
        respostas.setLineWrap(true);
        respostas.setWrapStyleWord(true);
        respostas.setFont(new Font("SansSerif", Font.PLAIN, 12));
        respostas.setBackground(Color.WHITE);
        respostas.setForeground(COR_TEXTO);
        respostas.setBorder(new EmptyBorder(10, 10, 10, 10));

        carregarRespostas(solicitacao, respostas);

        JTextArea novaResposta = new JTextArea(3, 40);
        novaResposta.setLineWrap(true);
        novaResposta.setWrapStyleWord(true);
        novaResposta.setFont(new Font("SansSerif", Font.PLAIN, 12));
        novaResposta.setBorder(new EmptyBorder(8, 8, 8, 8));

        JButton responder = criarBotaoPrimario("Enviar resposta");
        responder.setEnabled(!"RESOLVIDA".equals(solicitacao.getStatus()));

        responder.addActionListener(evento -> {
            try {
                respostaService.responder(
                        solicitacao,
                        usuario,
                        novaResposta.getText().trim()
                );

                novaResposta.setText("");
                carregarRespostas(solicitacao, respostas);
                carregarSolicitacoes();

                JOptionPane.showMessageDialog(
                        dialogo,
                        "Resposta enviada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (IllegalArgumentException erro) {
                mostrarAviso(erro.getMessage());

            } catch (RuntimeException erro) {
                mostrarErro(
                        "Não foi possível enviar a resposta.",
                        erro
                );
            }
        });

        RoundedPanel painelDetalhes = new RoundedPanel(16, Color.WHITE);
        painelDetalhes.setLayout(new BorderLayout());
        painelDetalhes.setBorder(new EmptyBorder(12, 12, 12, 12));
        painelDetalhes.add(new JScrollPane(dados), BorderLayout.CENTER);

        RoundedPanel painelResposta = new RoundedPanel(16, Color.WHITE);
        painelResposta.setLayout(new BorderLayout(8, 8));
        painelResposta.setBorder(new EmptyBorder(12, 12, 12, 12));
        painelResposta.add(new JScrollPane(respostas), BorderLayout.CENTER);

        JPanel envio = new JPanel(new BorderLayout(8, 8));
        envio.setOpaque(false);
        envio.add(new JScrollPane(novaResposta), BorderLayout.CENTER);
        envio.add(responder, BorderLayout.EAST);

        painelResposta.add(envio, BorderLayout.SOUTH);

        principal.add(painelDetalhes, BorderLayout.NORTH);
        principal.add(painelResposta, BorderLayout.CENTER);

        dialogo.setContentPane(principal);
        dialogo.setVisible(true);
    }

    private void carregarRespostas(
            Solicitacao solicitacao,
            JTextArea area
    ) {
        area.setText("");

        List<Resposta> respostas =
                respostaService.buscarPorSolicitacao(
                        solicitacao.getId()
                );

        if (respostas.isEmpty()) {
            area.setText("Nenhuma resposta cadastrada.");
            return;
        }

        for (Resposta resposta : respostas) {
            area.append(
                    resposta.getUsuario().getNome()
                            + " - "
                            + formatarData(resposta.getDataResposta())
                            + "\n"
                            + resposta.getDescricao()
                            + "\n\n----------------------------------------\n\n"
            );
        }
    }

    private void resolverSelecionada() {
        Solicitacao solicitacao = obterSelecionada();

        if (solicitacao == null) {
            return;
        }

        if (!Objects.equals(
                usuario.getId(),
                solicitacao.getUsuario().getId()
        )) {
            mostrarAviso(
                    "Não é possível alterar o status de uma solicitação que não é sua."
            );
            return;
        }

        if (!confirmar("Marcar esta solicitação como resolvida?")) {
            return;
        }

        try {
            solicitacaoService.marcarComoResolvida(
                    solicitacao.getId(),
                    usuario
            );

            carregarSolicitacoes();

        } catch (SecurityException erro) {
            mostrarAviso(erro.getMessage());

        } catch (RuntimeException erro) {
            mostrarErro(
                    "Não foi possível resolver a solicitação.",
                    erro
            );
        }
    }

    private void excluirSelecionada() {
        Solicitacao solicitacao = obterSelecionada();

        if (solicitacao == null) {
            return;
        }

        if (!Objects.equals(
                usuario.getId(),
                solicitacao.getUsuario().getId()
        )) {
            mostrarAviso(
                    "Não é possível excluir uma solicitação que não é sua."
            );
            return;
        }

        if (!confirmar("Excluir definitivamente esta solicitação?")) {
            return;
        }

        try {
            solicitacaoService.excluirPropria(
                    solicitacao.getId(),
                    usuario
            );

            carregarSolicitacoes();

        } catch (SecurityException erro) {
            mostrarAviso(erro.getMessage());

        } catch (RuntimeException erro) {
            mostrarErro(
                    "Não foi possível excluir a solicitação.",
                    erro
            );
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
            JPanel painel,
            GridBagConstraints gbc,
            int linha,
            String rotulo,
            Component campo
    ) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(rotulo);
        label.setForeground(COR_SECUNDARIA);
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        painel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(campo, gbc);
    }

    private String formatarData(LocalDateTime data) {
        return data == null ? "" : data.format(FORMATO_DATA);
    }

    private boolean confirmar(String mensagem) {
        return JOptionPane.showConfirmDialog(
                this,
                mensagem,
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void mostrarErro(
            String mensagem,
            RuntimeException erro
    ) {
        String detalhe = erro.getMessage() == null
                ? ""
                : "\n" + erro.getMessage();

        JOptionPane.showMessageDialog(
                this,
                mensagem + detalhe,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void sair() {
        if (confirmar("Deseja realmente sair da sua conta?")) {
            SessaoUsuario.encerrar();
            dispose();
            new LoginView();
        }
    }

    private static class RoundedPanel extends JPanel {

        private final int raio;
        private final Color cor;

        public RoundedPanel(int raio, Color cor) {
            this.raio = raio;
            this.cor = cor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(cor);
            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    raio,
                    raio
            );

            g2.dispose();
            super.paintComponent(graphics);
        }
    }

    private static class RoundedButton extends JButton {

        private final int raio;
        private Color borderColor;

        public RoundedButton(String texto, int raio) {
            super(texto);
            this.raio = raio;
            this.borderColor = COR_AZUL;

            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        public void setBorderColor(Color borderColor) {
            this.borderColor = borderColor;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Color fundo = getBackground();

            if (!isEnabled()) {
                fundo = Color.decode("#E2E8F0");
            } else if (getModel().isPressed()) {
                fundo = COR_AZUL_ESCURO;
            }

            g2.setColor(fundo);
            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    raio,
                    raio
            );

            g2.setColor(borderColor);
            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    raio,
                    raio
            );

            g2.dispose();
            super.paintComponent(graphics);
        }
    }
}