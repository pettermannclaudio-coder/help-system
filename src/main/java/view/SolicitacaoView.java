package view;

import model.Solicitacao;
import service.SolicitacaoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class SolicitacaoView extends JFrame {

    private static final Color COR_FUNDO =
            Color.decode("#F1F5F9");

    private static final Color COR_AZUL =
            Color.decode("#3853DC");

    private static final Color COR_AZUL_ESCURO =
            Color.decode("#2F46C7");

    private static final Color COR_TEXTO =
            Color.decode("#1E293B");

    private static final Color COR_SECUNDARIA =
            Color.decode("#64748B");

    private static final Color COR_BORDA =
            Color.decode("#CBD5E1");

    private static final Color COR_PERIGO =
            Color.decode("#DC2626");

    private JTextField txtPesquisa;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JButton btnPesquisar;
    private JButton btnLimpar;
    private JButton btnNova;
    private JButton btnVisualizar;
    private JButton btnAtualizar;
    private JButton btnFechar;

    private final SolicitacaoService solicitacaoService =
            new SolicitacaoService();

    public SolicitacaoView() {
        configurarJanela();
        criarComponentes();
        configurarEventos();
        carregarSolicitacoes();
    }

    private void configurarJanela() {

        setTitle(
                "Help System - Solicitações"
        );

        setSize(
                900,
                650
        );

        setMinimumSize(
                new Dimension(
                        780,
                        580
                )
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setResizable(true);
    }

    private void criarComponentes() {

        JPanel painelPrincipal =
                new JPanel(
                        new BorderLayout(
                                0,
                                16
                        )
                );

        painelPrincipal.setBackground(
                COR_FUNDO
        );

        painelPrincipal.setBorder(
                new EmptyBorder(
                        22,
                        24,
                        22,
                        24
                )
        );

        painelPrincipal.add(
                criarCabecalho(),
                BorderLayout.NORTH
        );

        painelPrincipal.add(
                criarConteudo(),
                BorderLayout.CENTER
        );

        painelPrincipal.add(
                criarBarraBotoes(),
                BorderLayout.SOUTH
        );

        setContentPane(
                painelPrincipal
        );

        getRootPane().setDefaultButton(
                btnPesquisar
        );
    }

    private JComponent criarCabecalho() {

        RoundedPanel cabecalho =
                new RoundedPanel(
                        18,
                        Color.WHITE
                );

        cabecalho.setLayout(
                new BorderLayout(
                        16,
                        0
                )
        );

        cabecalho.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );

        RoundedPanel logo =
                new RoundedPanel(
                        14,
                        COR_AZUL
                );

        logo.setLayout(
                new GridBagLayout()
        );

        Dimension tamanhoLogo =
                new Dimension(
                        52,
                        52
                );

        logo.setPreferredSize(
                tamanhoLogo
        );

        logo.setMinimumSize(
                tamanhoLogo
        );

        logo.setMaximumSize(
                tamanhoLogo
        );

        JLabel lblLogo =
                new JLabel("H");

        lblLogo.setForeground(
                Color.WHITE
        );

        lblLogo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        logo.add(lblLogo);

        JPanel painelTextos =
                new JPanel();

        painelTextos.setOpaque(false);

        painelTextos.setLayout(
                new BoxLayout(
                        painelTextos,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblTitulo =
                new JLabel(
                        "Solicitações"
                );

        lblTitulo.setForeground(
                COR_TEXTO
        );

        lblTitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel lblSubtitulo =
                new JLabel(
                        "Consulte e visualize as solicitações cadastradas"
                );

        lblSubtitulo.setForeground(
                COR_SECUNDARIA
        );

        lblSubtitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        lblSubtitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelTextos.add(
                lblTitulo
        );

        painelTextos.add(
                Box.createVerticalStrut(4)
        );

        painelTextos.add(
                lblSubtitulo
        );

        cabecalho.add(
                logo,
                BorderLayout.WEST
        );

        cabecalho.add(
                painelTextos,
                BorderLayout.CENTER
        );

        return cabecalho;
    }

    private JComponent criarConteudo() {

        JPanel conteudo =
                new JPanel(
                        new BorderLayout(
                                0,
                                14
                        )
                );

        conteudo.setOpaque(false);

        conteudo.add(
                criarPainelPesquisa(),
                BorderLayout.NORTH
        );

        conteudo.add(
                criarPainelTabela(),
                BorderLayout.CENTER
        );

        return conteudo;
    }

    private JComponent criarPainelPesquisa() {

        RoundedPanel painelPesquisa =
                new RoundedPanel(
                        18,
                        Color.WHITE
                );

        painelPesquisa.setLayout(
                new BorderLayout(
                        12,
                        0
                )
        );

        painelPesquisa.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );

        JPanel grupoPesquisa =
                new JPanel();

        grupoPesquisa.setOpaque(false);

        grupoPesquisa.setLayout(
                new BoxLayout(
                        grupoPesquisa,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblPesquisa =
                new JLabel(
                        "PESQUISAR PELO TÍTULO"
                );

        lblPesquisa.setForeground(
                COR_SECUNDARIA
        );

        lblPesquisa.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        lblPesquisa.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        txtPesquisa =
                new JTextField();

        configurarCampoTexto(
                txtPesquisa
        );

        txtPesquisa.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        grupoPesquisa.add(
                lblPesquisa
        );

        grupoPesquisa.add(
                Box.createVerticalStrut(6)
        );

        grupoPesquisa.add(
                txtPesquisa
        );

        btnPesquisar =
                criarBotaoPrimario(
                        "Pesquisar"
                );

        btnLimpar =
                criarBotaoSecundario(
                        "Limpar"
                );

        JPanel painelBotoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                19
                        )
                );

        painelBotoes.setOpaque(false);

        painelBotoes.add(
                btnLimpar
        );

        painelBotoes.add(
                btnPesquisar
        );

        painelPesquisa.add(
                grupoPesquisa,
                BorderLayout.CENTER
        );

        painelPesquisa.add(
                painelBotoes,
                BorderLayout.EAST
        );

        return painelPesquisa;
    }

    private JComponent criarPainelTabela() {

        String[] colunas = {
                "ID",
                "Título",
                "Departamento",
                "Status"
        };

        modelo =
                new DefaultTableModel(
                        colunas,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int linha,
                            int coluna
                    ) {

                        return false;
                    }
                };

        tabela =
                new JTable(modelo);

        tabela.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabela.setAutoCreateRowSorter(
                true
        );

        tabela.setRowHeight(
                36
        );

        tabela.setShowVerticalLines(
                false
        );

        tabela.setShowHorizontalLines(
                true
        );

        tabela.setGridColor(
                COR_BORDA
        );

        tabela.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );

        tabela.setBackground(
                Color.WHITE
        );

        tabela.setForeground(
                COR_TEXTO
        );

        tabela.setSelectionBackground(
                Color.decode("#E0E7FF")
        );

        tabela.setSelectionForeground(
                COR_TEXTO
        );

        tabela.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        JTableHeader cabecalhoTabela =
                tabela.getTableHeader();

        cabecalhoTabela.setBackground(
                COR_AZUL
        );

        cabecalhoTabela.setForeground(
                Color.WHITE
        );

        cabecalhoTabela.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        cabecalhoTabela.setPreferredSize(
                new Dimension(
                        0,
                        40
                )
        );

        cabecalhoTabela.setReorderingAllowed(
                false
        );

        tabela.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        tabela.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(350);

        tabela.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(180);

        tabela.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(120);

        centralizarColunas(
                0,
                3
        );

        tabela.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent evento
                    ) {

                        if (
                                evento.getClickCount() == 2
                        ) {

                            visualizar();
                        }
                    }
                }
        );

        JScrollPane scroll =
                new JScrollPane(
                        tabela
                );

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        COR_BORDA,
                        1
                )
        );

        scroll.getViewport()
                .setBackground(
                        Color.WHITE
                );

        RoundedPanel painelTabela =
                new RoundedPanel(
                        18,
                        Color.WHITE
                );

        painelTabela.setLayout(
                new BorderLayout()
        );

        painelTabela.setBorder(
                new EmptyBorder(
                        1,
                        1,
                        1,
                        1
                )
        );

        painelTabela.add(
                scroll,
                BorderLayout.CENTER
        );

        return painelTabela;
    }

    private void centralizarColunas(
            int... colunas
    ) {

        DefaultTableCellRenderer centralizado =
                new DefaultTableCellRenderer();

        centralizado.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        for (int coluna : colunas) {

            tabela.getColumnModel()
                    .getColumn(coluna)
                    .setCellRenderer(
                            centralizado
                    );
        }
    }

    private JComponent criarBarraBotoes() {

        RoundedPanel barra =
                new RoundedPanel(
                        18,
                        Color.WHITE
                );

        barra.setLayout(
                new FlowLayout(
                        FlowLayout.CENTER,
                        10,
                        14
                )
        );

        btnNova =
                criarBotaoPrimario(
                        "Nova solicitação"
                );

        btnVisualizar =
                criarBotaoSecundario(
                        "Visualizar"
                );

        btnAtualizar =
                criarBotaoSecundario(
                        "Atualizar"
                );

        btnFechar =
                criarBotaoPerigo(
                        "Fechar"
                );

        barra.add(
                btnNova
        );

        barra.add(
                btnVisualizar
        );

        barra.add(
                btnAtualizar
        );

        barra.add(
                btnFechar
        );

        return barra;
    }

    private void configurarCampoTexto(
            JTextField campo
    ) {

        Dimension tamanho =
                new Dimension(
                        420,
                        40
                );

        campo.setPreferredSize(
                tamanho
        );

        campo.setMinimumSize(
                tamanho
        );

        campo.setMaximumSize(
                tamanho
        );

        campo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        campo.setForeground(
                COR_TEXTO
        );

        campo.setBackground(
                Color.WHITE
        );

        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                COR_BORDA,
                                1
                        ),
                        new EmptyBorder(
                                8,
                                11,
                                8,
                                11
                        )
                )
        );
    }

    private JButton criarBotaoPrimario(
            String texto
    ) {

        return criarBotao(
                texto,
                COR_AZUL,
                Color.WHITE,
                COR_AZUL
        );
    }

    private JButton criarBotaoSecundario(
            String texto
    ) {

        return criarBotao(
                texto,
                Color.WHITE,
                COR_AZUL,
                COR_AZUL
        );
    }

    private JButton criarBotaoPerigo(
            String texto
    ) {

        return criarBotao(
                texto,
                Color.WHITE,
                COR_PERIGO,
                COR_PERIGO
        );
    }

    private JButton criarBotao(
            String texto,
            Color fundo,
            Color corTexto,
            Color corBorda
    ) {

        RoundedButton botao =
                new RoundedButton(
                        texto,
                        10
                );

        botao.setBackground(
                fundo
        );

        botao.setForeground(
                corTexto
        );

        botao.setBorderColor(
                corBorda
        );

        botao.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        int largura =
                Math.max(
                        110,
                        texto.length() * 8 + 32
                );

        Dimension tamanho =
                new Dimension(
                        largura,
                        40
                );

        botao.setPreferredSize(
                tamanho
        );

        botao.setMinimumSize(
                tamanho
        );

        botao.setMaximumSize(
                tamanho
        );

        botao.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return botao;
    }

    private void configurarEventos() {

        btnFechar.addActionListener(
                evento -> dispose()
        );

        btnPesquisar.addActionListener(
                evento -> pesquisar()
        );

        txtPesquisa.addActionListener(
                evento -> pesquisar()
        );

        btnLimpar.addActionListener(
                evento -> limparPesquisa()
        );

        btnAtualizar.addActionListener(
                evento -> carregarSolicitacoes()
        );

        btnNova.addActionListener(
                evento -> {

                    JOptionPane.showMessageDialog(
                            this,
                            "Para abrir uma nova solicitação, "
                                    + "é necessário informar o usuário conectado.",
                            "Nova solicitação",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
        );

        btnVisualizar.addActionListener(
                evento -> visualizar()
        );
    }

    private void carregarSolicitacoes() {

        modelo.setRowCount(0);

        try {

            for (
                    Solicitacao solicitacao
                    : solicitacaoService.listar()
            ) {

                adicionarSolicitacaoTabela(
                        solicitacao
                );
            }

        } catch (RuntimeException erro) {

            mostrarErro(
                    "Não foi possível carregar as solicitações."
            );

            erro.printStackTrace();
        }
    }

    private void pesquisar() {

        String texto =
                txtPesquisa
                        .getText()
                        .trim()
                        .toLowerCase();

        modelo.setRowCount(0);

        try {

            for (
                    Solicitacao solicitacao
                    : solicitacaoService.listar()
            ) {

                String titulo =
                        solicitacao.getTitulo() == null
                                ? ""
                                : solicitacao
                                .getTitulo()
                                .toLowerCase();

                if (
                        texto.isBlank()
                                || titulo.contains(texto)
                ) {

                    adicionarSolicitacaoTabela(
                            solicitacao
                    );
                }
            }

        } catch (RuntimeException erro) {

            mostrarErro(
                    "Não foi possível pesquisar as solicitações."
            );

            erro.printStackTrace();
        }
    }

    private void adicionarSolicitacaoTabela(
            Solicitacao solicitacao
    ) {

        String departamento =
                solicitacao.getDepartamento() == null
                        ? ""
                        : solicitacao
                        .getDepartamento()
                        .getNome();

        modelo.addRow(
                new Object[]{
                        solicitacao.getId(),
                        solicitacao.getTitulo(),
                        departamento,
                        solicitacao.getStatus()
                }
        );
    }

    private void limparPesquisa() {

        txtPesquisa.setText("");

        carregarSolicitacoes();

        txtPesquisa.requestFocus();
    }

    private void visualizar() {

        int linhaVisual =
                tabela.getSelectedRow();

        if (linhaVisual < 0) {

            mostrarAviso(
                    "Selecione uma solicitação."
            );

            return;
        }

        int linhaModelo =
                tabela.convertRowIndexToModel(
                        linhaVisual
                );

        Integer id =
                (Integer) modelo.getValueAt(
                        linhaModelo,
                        0
                );

        try {

            Solicitacao solicitacao =
                    solicitacaoService
                            .buscarPorId(id);

            if (solicitacao == null) {

                mostrarAviso(
                        "A solicitação selecionada não foi encontrada."
                );

                return;
            }

            new DetalheSolicitacaoView(
                    solicitacao
            ).setVisible(true);

        } catch (RuntimeException erro) {

            mostrarErro(
                    "Não foi possível abrir a solicitação."
            );

            erro.printStackTrace();
        }
    }

    private void mostrarAviso(
            String mensagem
    ) {

        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void mostrarErro(
            String mensagem
    ) {

        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () ->
                        new SolicitacaoView()
                                .setVisible(true)
        );
    }

    private static class RoundedPanel
            extends JPanel {

        private final int raio;
        private final Color cor;

        public RoundedPanel(
                int raio,
                Color cor
        ) {

            this.raio = raio;
            this.cor = cor;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics graphics
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            graphics.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    cor
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    raio,
                    raio
            );

            g2.dispose();

            super.paintComponent(
                    graphics
            );
        }
    }

    private static class RoundedButton
            extends JButton {

        private final int raio;
        private Color borderColor;

        public RoundedButton(
                String texto,
                int raio
        ) {

            super(texto);

            this.raio = raio;
            this.borderColor = COR_AZUL;

            setOpaque(false);

            setContentAreaFilled(false);

            setFocusPainted(false);

            setBorderPainted(false);
        }

        public void setBorderColor(
                Color borderColor
        ) {

            this.borderColor =
                    borderColor;
        }

        @Override
        protected void paintComponent(
                Graphics graphics
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            graphics.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Color fundo =
                    getBackground();

            if (!isEnabled()) {

                fundo =
                        Color.decode("#E2E8F0");

            } else if (
                    getModel().isPressed()
            ) {

                fundo =
                        COR_AZUL_ESCURO;
            }

            g2.setColor(
                    fundo
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    raio,
                    raio
            );

            g2.setColor(
                    borderColor
            );

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    raio,
                    raio
            );

            g2.dispose();

            super.paintComponent(
                    graphics
            );
        }
    }
}