package view;

import model.Departamento;
import service.DepartamentoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CadastroDepartamentoView extends JFrame {

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

        setSize(560, 420);

        setMinimumSize(
                new Dimension(520, 390)
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

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
                new Dimension(470, 310)
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
                btnCadastrar
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
                        "Cadastro de Departamento"
                );

        lblTitulo.setForeground(
                Color.WHITE
        );

        lblTitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        lblTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel lblSubtitulo =
                new JLabel(
                        "Cadastre um novo departamento no sistema"
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
                        28,
                        32,
                        24,
                        32
                )
        );

        formulario.setLayout(
                new BoxLayout(
                        formulario,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblNome =
                new JLabel(
                        "NOME DO DEPARTAMENTO",
                        SwingConstants.CENTER
                );

        lblNome.setForeground(
                COR_SECUNDARIA
        );

        lblNome.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        lblNome.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        txtNome =
                new JTextField();

        configurarCampoTexto(txtNome);

        btnCadastrar =
                criarBotaoPrimario(
                        "Cadastrar"
                );

        btnLimpar =
                criarBotaoSecundario(
                        "Limpar"
                );

        btnFechar =
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

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFechar);

        formulario.add(lblNome);

        formulario.add(
                Box.createVerticalStrut(8)
        );

        formulario.add(txtNome);

        formulario.add(
                Box.createVerticalStrut(28)
        );

        formulario.add(painelBotoes);

        return formulario;
    }

    private void configurarCampoTexto(
            JTextField campo
    ) {

        Dimension tamanho =
                new Dimension(390, 42);

        campo.setPreferredSize(tamanho);
        campo.setMaximumSize(tamanho);
        campo.setMinimumSize(tamanho);

        campo.setAlignmentX(
                Component.CENTER_ALIGNMENT
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
                                9,
                                12,
                                9,
                                12
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

        String nome =
                txtNome.getText().trim();

        if (nome.isBlank()) {

            mostrarAviso(
                    "Informe o nome do departamento."
            );

            txtNome.requestFocus();

            return;
        }

        Departamento departamento =
                new Departamento();

        departamento.setNome(nome);

        btnCadastrar.setEnabled(false);

        try {

            departamentoService.salvar(
                    departamento
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Departamento cadastrado com sucesso!",
                    "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limparCampos();

        } catch (IllegalArgumentException e) {

            mostrarAviso(
                    e.getMessage()
            );

        } catch (RuntimeException e) {

            mostrarErro(
                    "Não foi possível cadastrar o departamento."
            );

            e.printStackTrace();

        } finally {

            btnCadastrar.setEnabled(true);
        }
    }

    private void limparCampos() {

        txtNome.setText("");

        txtNome.requestFocus();
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