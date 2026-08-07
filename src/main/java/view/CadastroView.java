package view;

import dao.DepartamentoDAO;
import model.Departamento;
import service.UsuarioService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class CadastroView extends JFrame {

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

    private final UsuarioService usuarioService =
            new UsuarioService();

    private final DepartamentoDAO departamentoDAO =
            new DepartamentoDAO();

    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;
    private JComboBox<String> comboPerfil;
    private JComboBox<Departamento> comboDepartamento;

    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnFechar;

    public CadastroView() {
        configurarJanela();
        criarComponentes();
        configurarEventos();
        carregarDepartamentos();
    }

    private void configurarJanela() {

        setTitle("Help System - Cadastro de Usuário");

        setSize(620, 680);

        setMinimumSize(
                new Dimension(580, 640)
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
                new Dimension(530, 575)
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
                        "Cadastro de Usuário"
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
                        "Cadastre um novo usuário no sistema"
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

        txtNome = new JTextField();
        txtEmail = new JTextField();
        txtSenha = new JPasswordField();
        txtConfirmarSenha = new JPasswordField();

        comboPerfil =
                new JComboBox<>(
                        new String[]{
                                "COMUM",
                                "ADMIN"
                        }
                );

        comboDepartamento =
                new JComboBox<>();

        configurarCampoTexto(txtNome);
        configurarCampoTexto(txtEmail);
        configurarCampoTexto(txtSenha);
        configurarCampoTexto(txtConfirmarSenha);

        configurarCombo(comboPerfil);
        configurarCombo(comboDepartamento);

        formulario.add(
                criarGrupoCampo(
                        "NOME",
                        txtNome
                )
        );

        formulario.add(
                Box.createVerticalStrut(12)
        );

        formulario.add(
                criarGrupoCampo(
                        "E-MAIL",
                        txtEmail
                )
        );

        formulario.add(
                Box.createVerticalStrut(12)
        );

        formulario.add(
                criarGrupoCampo(
                        "SENHA",
                        txtSenha
                )
        );

        formulario.add(
                Box.createVerticalStrut(12)
        );

        formulario.add(
                criarGrupoCampo(
                        "CONFIRMAR SENHA",
                        txtConfirmarSenha
                )
        );

        formulario.add(
                Box.createVerticalStrut(12)
        );

        formulario.add(
                criarGrupoCampo(
                        "PERFIL",
                        comboPerfil
                )
        );

        formulario.add(
                Box.createVerticalStrut(12)
        );

        formulario.add(
                criarGrupoCampo(
                        "DEPARTAMENTO",
                        comboDepartamento
                )
        );

        formulario.add(
                Box.createVerticalStrut(22)
        );

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
                        420,
                        38
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
                        420,
                        38
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

    private void carregarDepartamentos() {

        comboDepartamento.removeAllItems();

        try {

            List<Departamento> departamentos =
                    departamentoDAO.listar();

            for (
                    Departamento departamento
                    : departamentos
            ) {

                comboDepartamento.addItem(
                        departamento
                );
            }

            comboDepartamento.setSelectedIndex(-1);

        } catch (RuntimeException e) {

            btnCadastrar.setEnabled(false);

            mostrarErro(
                    "Não foi possível carregar os departamentos."
            );

            e.printStackTrace();
        }
    }

    private void cadastrar() {

        String nome =
                txtNome.getText().trim();

        String email =
                txtEmail.getText().trim();

        String senha =
                new String(
                        txtSenha.getPassword()
                );

        String confirmarSenha =
                new String(
                        txtConfirmarSenha.getPassword()
                );

        Departamento departamento =
                (Departamento)
                        comboDepartamento.getSelectedItem();

        if (!senha.equals(confirmarSenha)) {

            mostrarAviso(
                    "A senha e a confirmação são diferentes."
            );

            txtConfirmarSenha.setText("");

            txtConfirmarSenha.requestFocus();

            return;
        }

        btnCadastrar.setEnabled(false);

        try {

            usuarioService.cadastrar(
                    nome,
                    email,
                    senha,
                    departamento
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário cadastrado com sucesso!",
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
                    "Não foi possível cadastrar o usuário no banco."
            );

            e.printStackTrace();

        } finally {

            btnCadastrar.setEnabled(true);
        }
    }

    private void limparCampos() {

        txtNome.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
        txtConfirmarSenha.setText("");

        comboPerfil.setSelectedIndex(0);
        comboDepartamento.setSelectedIndex(-1);

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