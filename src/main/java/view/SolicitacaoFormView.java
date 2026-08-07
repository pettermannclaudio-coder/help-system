package view;

import model.Departamento;
import model.PrioridadeSolicitacao;
import model.Solicitacao;
import model.Usuario;
import service.DepartamentoService;
import service.SolicitacaoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;

public class SolicitacaoFormView extends JFrame {

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

    private final SolicitacaoService solicitacaoService =
            new SolicitacaoService();

    private final DepartamentoService departamentoService =
            new DepartamentoService();

    private final Usuario usuario;

    private JTextField txtTitulo;
    private JTextArea txtDescricao;

    private JComboBox<Departamento> cbDepartamento;
    private JComboBox<PrioridadeSolicitacao> cbPrioridade;

    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnCancelar;

    public SolicitacaoFormView(Usuario usuario) {

        this.usuario = usuario;

        configurarJanela();
        criarComponentes();
        configurarEventos();
        carregarDepartamentos();
    }

    private void configurarJanela() {

        setTitle("Help System - Nova Solicitação");

        setSize(680, 680);

        setMinimumSize(
                new Dimension(640, 640)
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setResizable(false);
    }

    private void criarComponentes() {

        JPanel painelFundo =
                new JPanel(new GridBagLayout());

        painelFundo.setBackground(COR_FUNDO);

        painelFundo.setBorder(
                new EmptyBorder(
                        24,
                        24,
                        24,
                        24
                )
        );

        RoundedPanel cartao =
                new RoundedPanel(
                        18,
                        Color.WHITE
                );

        cartao.setLayout(
                new BorderLayout()
        );

        cartao.setPreferredSize(
                new Dimension(580, 575)
        );

        cartao.setBorder(
                BorderFactory.createLineBorder(
                        COR_BORDA,
                        1
                )
        );

        cartao.add(
                criarCabecalho(),
                BorderLayout.NORTH
        );

        cartao.add(
                criarFormulario(),
                BorderLayout.CENTER
        );

        painelFundo.add(cartao);

        setContentPane(painelFundo);

        getRootPane().setDefaultButton(
                btnSalvar
        );
    }

    private JPanel criarCabecalho() {

        JPanel cabecalho =
                new JPanel(
                        new BorderLayout(
                                14,
                                0
                        )
                );

        cabecalho.setBackground(COR_AZUL);

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
                        Color.WHITE
                );

        logo.setLayout(
                new GridBagLayout()
        );

        Dimension tamanhoLogo =
                new Dimension(46, 46);

        logo.setPreferredSize(tamanhoLogo);
        logo.setMinimumSize(tamanhoLogo);
        logo.setMaximumSize(tamanhoLogo);

        JLabel lblLogo =
                new JLabel("H");

        lblLogo.setForeground(COR_AZUL);

        lblLogo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
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
                        "Nova Solicitação"
                );

        lblTitulo.setForeground(
                Color.WHITE
        );

        lblTitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        19
                )
        );

        lblTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        String nomeUsuario =
                usuario == null
                        ? "Usuário"
                        : usuario.getNome();

        JLabel lblSubtitulo =
                new JLabel(
                        "Solicitante: " + nomeUsuario
                );

        lblSubtitulo.setForeground(
                new Color(
                        230,
                        235,
                        255
                )
        );

        lblSubtitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
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

    private JPanel criarFormulario() {

        JPanel formulario =
                new JPanel();

        formulario.setBackground(
                Color.WHITE
        );

        formulario.setBorder(
                new EmptyBorder(
                        22,
                        34,
                        24,
                        34
                )
        );

        formulario.setLayout(
                new BoxLayout(
                        formulario,
                        BoxLayout.Y_AXIS
                )
        );

        txtTitulo =
                new JTextField();

        cbDepartamento =
                new JComboBox<>();

        cbPrioridade =
                new JComboBox<>(
                        PrioridadeSolicitacao.values()
                );

        cbPrioridade.setSelectedItem(
                PrioridadeSolicitacao.MEDIA
        );

        txtDescricao =
                new JTextArea();

        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);

        configurarCampoTexto(txtTitulo);
        configurarCombo(cbDepartamento);
        configurarCombo(cbPrioridade);
        configurarAreaTexto(txtDescricao);

        formulario.add(
                criarGrupoCampo(
                        "TÍTULO",
                        txtTitulo
                )
        );

        formulario.add(
                Box.createVerticalStrut(14)
        );

        formulario.add(
                criarGrupoCampo(
                        "DEPARTAMENTO",
                        cbDepartamento
                )
        );

        formulario.add(
                Box.createVerticalStrut(14)
        );

        formulario.add(
                criarGrupoCampo(
                        "PRIORIDADE",
                        cbPrioridade
                )
        );

        formulario.add(
                Box.createVerticalStrut(14)
        );

        JScrollPane scrollDescricao =
                new JScrollPane(
                        txtDescricao
                );

        scrollDescricao.setPreferredSize(
                new Dimension(
                        470,
                        135
                )
        );

        scrollDescricao.setMaximumSize(
                new Dimension(
                        470,
                        135
                )
        );

        scrollDescricao.setMinimumSize(
                new Dimension(
                        470,
                        135
                )
        );

        scrollDescricao.setBorder(
                BorderFactory.createLineBorder(
                        COR_BORDA,
                        1
                )
        );

        scrollDescricao.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        formulario.add(
                criarGrupoCampo(
                        "DESCRIÇÃO",
                        scrollDescricao
                )
        );

        formulario.add(
                Box.createVerticalStrut(24)
        );

        btnSalvar =
                criarBotaoPrimario(
                        "Publicar"
                );

        btnLimpar =
                criarBotaoSecundario(
                        "Limpar"
                );

        btnCancelar =
                criarBotaoPerigo(
                        "Fechar"
                );

        JPanel painelBotoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                0
                        )
                );

        painelBotoes.setOpaque(false);

        painelBotoes.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnCancelar);

        formulario.add(painelBotoes);

        return formulario;
    }

    private JPanel criarGrupoCampo(
            String texto,
            JComponent componente
    ) {

        JPanel grupo =
                new JPanel();

        grupo.setOpaque(false);

        grupo.setLayout(
                new BoxLayout(
                        grupo,
                        BoxLayout.Y_AXIS
                )
        );

        grupo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel label =
                new JLabel(
                        texto,
                        SwingConstants.CENTER
                );

        label.setForeground(
                COR_SECUNDARIA
        );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        componente.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        grupo.add(label);

        grupo.add(
                Box.createVerticalStrut(6)
        );

        grupo.add(componente);

        return grupo;
    }

    private void configurarCampoTexto(
            JTextField campo
    ) {

        Dimension tamanho =
                new Dimension(
                        470,
                        40
                );

        campo.setPreferredSize(tamanho);
        campo.setMinimumSize(tamanho);
        campo.setMaximumSize(tamanho);

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

    private void configurarCombo(
            JComboBox<?> combo
    ) {

        Dimension tamanho =
                new Dimension(
                        470,
                        40
                );

        combo.setPreferredSize(tamanho);
        combo.setMinimumSize(tamanho);
        combo.setMaximumSize(tamanho);

        combo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        combo.setForeground(
                COR_TEXTO
        );

        combo.setBackground(
                Color.WHITE
        );
    }

    private void configurarAreaTexto(
            JTextArea area
    ) {

        area.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        area.setForeground(
                COR_TEXTO
        );

        area.setBackground(
                Color.WHITE
        );

        area.setBorder(
                new EmptyBorder(
                        10,
                        11,
                        10,
                        11
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
                COR_AZUL,
                125
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
                105
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
                105
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

    private void configurarEventos() {

        btnSalvar.addActionListener(
                evento -> salvar()
        );

        btnLimpar.addActionListener(
                evento -> limparCampos()
        );

        btnCancelar.addActionListener(
                evento -> dispose()
        );
    }

    private void carregarDepartamentos() {

        cbDepartamento.removeAllItems();

        try {

            for (
                    Departamento departamento
                    : departamentoService.listar()
            ) {

                cbDepartamento.addItem(
                        departamento
                );
            }

            cbDepartamento.setSelectedIndex(-1);

        } catch (RuntimeException erro) {

            btnSalvar.setEnabled(false);

            mostrarErro(
                    "Não foi possível carregar os departamentos."
            );

            erro.printStackTrace();
        }
    }

    private void salvar() {

        String titulo =
                txtTitulo.getText().trim();

        String descricao =
                txtDescricao.getText().trim();

        Departamento departamento =
                (Departamento)
                        cbDepartamento.getSelectedItem();

        PrioridadeSolicitacao prioridade =
                (PrioridadeSolicitacao)
                        cbPrioridade.getSelectedItem();

        if (titulo.isBlank()) {

            mostrarAviso(
                    "Informe o título."
            );

            txtTitulo.requestFocus();

            return;
        }

        if (departamento == null) {

            mostrarAviso(
                    "Selecione um departamento."
            );

            cbDepartamento.requestFocus();

            return;
        }

        if (prioridade == null) {

            mostrarAviso(
                    "Selecione uma prioridade."
            );

            cbPrioridade.requestFocus();

            return;
        }

        if (descricao.isBlank()) {

            mostrarAviso(
                    "Informe a descrição."
            );

            txtDescricao.requestFocus();

            return;
        }

        Solicitacao solicitacao =
                new Solicitacao();

        solicitacao.setTitulo(titulo);

        solicitacao.setDescricao(descricao);

        solicitacao.setDepartamento(
                departamento
        );

        solicitacao.setUsuario(
                usuario
        );

        solicitacao.setStatus(
                "ABERTA"
        );

        solicitacao.setPrioridade(
                prioridade
        );

        solicitacao.setDataCriacao(
                LocalDateTime.now()
        );

        btnSalvar.setEnabled(false);

        try {

            solicitacaoService.salvar(
                    solicitacao
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Solicitação cadastrada com sucesso!",
                    "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (IllegalArgumentException erro) {

            mostrarAviso(
                    erro.getMessage()
            );

        } catch (RuntimeException erro) {

            mostrarErro(
                    "Não foi possível cadastrar a solicitação."
            );

            erro.printStackTrace();

        } finally {

            btnSalvar.setEnabled(true);
        }
    }

    private void limparCampos() {

        txtTitulo.setText("");

        txtDescricao.setText("");

        cbDepartamento.setSelectedIndex(-1);

        cbPrioridade.setSelectedItem(
                PrioridadeSolicitacao.MEDIA
        );

        txtTitulo.requestFocus();
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
                    (Graphics2D)
                            graphics.create();

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

            this.borderColor =
                    COR_AZUL;

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

            g2.setColor(fundo);

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

            super.paintComponent(graphics);
        }
    }
}