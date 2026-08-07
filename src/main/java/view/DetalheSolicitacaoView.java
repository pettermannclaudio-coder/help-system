package view;

import model.Resposta;
import model.Solicitacao;
import service.RespostaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DetalheSolicitacaoView extends JFrame {

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

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Solicitacao solicitacao;

    private final RespostaService respostaService =
            new RespostaService();

    private JTextArea areaRespostas;

    public DetalheSolicitacaoView(
            Solicitacao solicitacao
    ) {

        this.solicitacao = solicitacao;

        configurarJanela();
        criarTela();
    }

    private void configurarJanela() {

        setTitle(
                "Help System - Detalhes da Solicitação"
        );

        setSize(820, 720);

        setMinimumSize(
                new Dimension(760, 650)
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setResizable(true);
    }

    private void criarTela() {

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

        setContentPane(painelPrincipal);
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
                new Dimension(52, 52);

        logo.setPreferredSize(tamanhoLogo);
        logo.setMinimumSize(tamanhoLogo);
        logo.setMaximumSize(tamanhoLogo);

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
                        "Detalhes da Solicitação"
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
                        "Solicitação #"
                                + solicitacao.getId()
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

        painelTextos.add(lblTitulo);

        painelTextos.add(
                Box.createVerticalStrut(4)
        );

        painelTextos.add(lblSubtitulo);

        JLabel lblStatus =
                new JLabel(
                        solicitacao.getStatus()
                );

        lblStatus.setOpaque(true);

        lblStatus.setBackground(
                Color.decode("#E0E7FF")
        );

        lblStatus.setForeground(
                COR_AZUL
        );

        lblStatus.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        lblStatus.setBorder(
                new EmptyBorder(
                        8,
                        14,
                        8,
                        14
                )
        );

        cabecalho.add(
                logo,
                BorderLayout.WEST
        );

        cabecalho.add(
                painelTextos,
                BorderLayout.CENTER
        );

        cabecalho.add(
                lblStatus,
                BorderLayout.EAST
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
                criarPainelDados(),
                BorderLayout.NORTH
        );

        conteudo.add(
                criarPainelRespostas(),
                BorderLayout.CENTER
        );

        return conteudo;
    }

    private JComponent criarPainelDados() {

        RoundedPanel painel =
                new RoundedPanel(
                        18,
                        Color.WHITE
                );

        painel.setLayout(
                new BorderLayout(
                        0,
                        14
                )
        );

        painel.setBorder(
                new EmptyBorder(
                        20,
                        22,
                        20,
                        22
                )
        );

        JLabel tituloSecao =
                criarTituloSecao(
                        "INFORMAÇÕES DA SOLICITAÇÃO"
                );

        JPanel dados =
                new JPanel(
                        new GridLayout(
                                0,
                                2,
                                16,
                                12
                        )
                );

        dados.setOpaque(false);

        dados.add(
                criarInformacao(
                        "Título",
                        solicitacao.getTitulo()
                )
        );

        dados.add(
                criarInformacao(
                        "Criado por",
                        solicitacao
                                .getUsuario()
                                .getNome()
                )
        );

        dados.add(
                criarInformacao(
                        "Departamento",
                        solicitacao
                                .getDepartamento()
                                .getNome()
                )
        );

        dados.add(
                criarInformacao(
                        "Status",
                        solicitacao.getStatus()
                )
        );

        dados.add(
                criarInformacao(
                        "Prioridade",
                        String.valueOf(
                                solicitacao.getPrioridade()
                        )
                )
        );

        dados.add(
                criarInformacao(
                        "Data de criação",
                        formatarData(
                                solicitacao.getDataCriacao()
                        )
                )
        );

        JPanel painelDescricao =
                new JPanel();

        painelDescricao.setOpaque(false);

        painelDescricao.setLayout(
                new BoxLayout(
                        painelDescricao,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblDescricao =
                new JLabel("DESCRIÇÃO");

        lblDescricao.setForeground(
                COR_SECUNDARIA
        );

        lblDescricao.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        lblDescricao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JTextArea descricao =
                new JTextArea(
                        solicitacao.getDescricao()
                );

        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setEditable(false);

        descricao.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        descricao.setForeground(
                COR_TEXTO
        );

        descricao.setBackground(
                Color.decode("#F8FAFC")
        );

        descricao.setBorder(
                new EmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );

        JScrollPane scrollDescricao =
                new JScrollPane(descricao);

        scrollDescricao.setPreferredSize(
                new Dimension(0, 105)
        );

        scrollDescricao.setBorder(
                BorderFactory.createLineBorder(
                        COR_BORDA,
                        1
                )
        );

        scrollDescricao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelDescricao.add(lblDescricao);

        painelDescricao.add(
                Box.createVerticalStrut(6)
        );

        painelDescricao.add(scrollDescricao);

        JPanel corpo =
                new JPanel();

        corpo.setOpaque(false);

        corpo.setLayout(
                new BoxLayout(
                        corpo,
                        BoxLayout.Y_AXIS
                )
        );

        dados.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelDescricao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        corpo.add(dados);

        corpo.add(
                Box.createVerticalStrut(18)
        );

        corpo.add(painelDescricao);

        painel.add(
                tituloSecao,
                BorderLayout.NORTH
        );

        painel.add(
                corpo,
                BorderLayout.CENTER
        );

        return painel;
    }

    private JPanel criarInformacao(
            String rotulo,
            String valor
    ) {

        JPanel informacao =
                new JPanel();

        informacao.setOpaque(false);

        informacao.setLayout(
                new BoxLayout(
                        informacao,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel labelRotulo =
                new JLabel(
                        rotulo.toUpperCase()
                );

        labelRotulo.setForeground(
                COR_SECUNDARIA
        );

        labelRotulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        9
                )
        );

        JLabel labelValor =
                new JLabel(
                        valor == null
                                ? ""
                                : valor
                );

        labelValor.setForeground(
                COR_TEXTO
        );

        labelValor.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        labelRotulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        labelValor.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        informacao.add(labelRotulo);

        informacao.add(
                Box.createVerticalStrut(4)
        );

        informacao.add(labelValor);

        return informacao;
    }

    private JComponent criarPainelRespostas() {

        RoundedPanel painel =
                new RoundedPanel(
                        18,
                        Color.WHITE
                );

        painel.setLayout(
                new BorderLayout(
                        0,
                        12
                )
        );

        painel.setBorder(
                new EmptyBorder(
                        18,
                        22,
                        18,
                        22
                )
        );

        JLabel tituloSecao =
                criarTituloSecao(
                        "RESPOSTAS"
                );

        areaRespostas =
                new JTextArea();

        areaRespostas.setEditable(false);
        areaRespostas.setLineWrap(true);
        areaRespostas.setWrapStyleWord(true);

        areaRespostas.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        areaRespostas.setForeground(
                COR_TEXTO
        );

        areaRespostas.setBackground(
                Color.decode("#F8FAFC")
        );

        areaRespostas.setBorder(
                new EmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        areaRespostas
                );

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        COR_BORDA,
                        1
                )
        );

        carregarRespostas();

        painel.add(
                tituloSecao,
                BorderLayout.NORTH
        );

        painel.add(
                scroll,
                BorderLayout.CENTER
        );

        return painel;
    }

    private JLabel criarTituloSecao(
            String texto
    ) {

        JLabel titulo =
                new JLabel(texto);

        titulo.setForeground(
                COR_AZUL
        );

        titulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        return titulo;
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

        JButton responder =
                criarBotaoPrimario(
                        "Responder"
                );

        JButton atualizar =
                criarBotaoSecundario(
                        "Atualizar"
                );

        JButton fechar =
                criarBotaoPerigo(
                        "Fechar"
                );

        responder.addActionListener(
                evento -> abrirResposta()
        );

        atualizar.addActionListener(
                evento -> carregarRespostas()
        );

        fechar.addActionListener(
                evento -> dispose()
        );

        barra.add(responder);
        barra.add(atualizar);
        barra.add(fechar);

        return barra;
    }

    private void carregarRespostas() {

        areaRespostas.setText("");

        try {

            List<Resposta> respostas =
                    respostaService
                            .buscarPorSolicitacao(
                                    solicitacao.getId()
                            );

            if (respostas.isEmpty()) {

                areaRespostas.setText(
                        "Nenhuma resposta cadastrada."
                );

                return;
            }

            for (Resposta resposta : respostas) {

                areaRespostas.append(
                        resposta
                                .getUsuario()
                                .getNome()
                                + "  •  "
                                + formatarData(
                                        resposta
                                                .getDataResposta()
                                )
                                + "\n\n"
                                + resposta.getDescricao()
                                + "\n\n"
                                + "────────────────────────────────────────"
                                + "\n\n"
                );
            }

            areaRespostas.setCaretPosition(0);

        } catch (RuntimeException erro) {

            mostrarErro(
                    "Não foi possível carregar as respostas."
            );

            erro.printStackTrace();
        }
    }

    private void abrirResposta() {

        JTextArea campoResposta =
                new JTextArea(
                        6,
                        35
                );

        campoResposta.setLineWrap(true);
        campoResposta.setWrapStyleWord(true);

        campoResposta.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        campoResposta.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        campoResposta
                );

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        COR_BORDA,
                        1
                )
        );

        int opcao =
                JOptionPane.showConfirmDialog(
                        this,
                        scroll,
                        "Nova resposta",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        String resposta =
                campoResposta
                        .getText()
                        .trim();

        if (resposta.isBlank()) {

            mostrarAviso(
                    "Informe uma resposta."
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "A tela está pronta para receber "
                        + "a integração de envio da resposta.",
                "Resposta",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private JButton criarBotaoPrimario(
            String texto
    ) {

        return criarBotao(
                texto,
                COR_AZUL,
                Color.WHITE,
                COR_AZUL,
                130
        );
    }

    private JButton criarBotaoSecundario(
            String texto
    ) {

        return criarBotao(
                texto,
                Color.WHITE,
                COR_AZUL,
                COR_AZUL,
                120
        );
    }

    private JButton criarBotaoPerigo(
            String texto
    ) {

        return criarBotao(
                texto,
                Color.WHITE,
                COR_PERIGO,
                COR_PERIGO,
                110
        );
    }

    private JButton criarBotao(
            String texto,
            Color fundo,
            Color corTexto,
            Color corBorda,
            int largura
    ) {

        RoundedButton botao =
                new RoundedButton(
                        texto,
                        10
                );

        botao.setBackground(fundo);
        botao.setForeground(corTexto);
        botao.setBorderColor(corBorda);

        botao.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        Dimension tamanho =
                new Dimension(
                        largura,
                        40
                );

        botao.setPreferredSize(tamanho);
        botao.setMinimumSize(tamanho);
        botao.setMaximumSize(tamanho);

        botao.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return botao;
    }

    private String formatarData(
            LocalDateTime data
    ) {

        return data == null
                ? ""
                : data.format(FORMATO_DATA);
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
                    (Graphics2D) graphics.create();

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

            this.borderColor = borderColor;
        }

        @Override
        protected void paintComponent(
                Graphics graphics
        ) {

            Graphics2D g2 =
                    (Graphics2D) graphics.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Color fundo =
                    getBackground();

            if (!isEnabled()) {

                fundo =
                        Color.decode("#E2E8F0");

            } else if (getModel().isPressed()) {

                fundo =
                        COR_AZUL_ESCURO;
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