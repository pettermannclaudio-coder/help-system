package view;

import model.TipoUsuario;
import model.Usuario;
import service.UsuarioService;
import util.SessaoUsuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JFrame {

    private static final Color COR_FUNDO =
            Color.decode("#F1F5F9");

    private static final Color COR_AZUL =
            Color.decode("#3853DC");

    private static final Color COR_TEXTO =
            Color.decode("#1E293B");

    private static final Color COR_SECUNDARIA =
            Color.decode("#64748B");

    private static final Color COR_BORDA =
            Color.decode("#CBD5E1");

    private final UsuarioService usuarioService =
            new UsuarioService();

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public LoginView() {
        configurarJanela();
        criarComponentes();
        configurarEventos();

        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Help System - Login");
        setSize(960, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(760, 580));
    }

    private void criarComponentes() {

        JPanel painelFundo =
                new JPanel(new GridBagLayout());

        painelFundo.setBackground(COR_FUNDO);

        JPanel painelCentral = new JPanel();

        painelCentral.setOpaque(false);

        painelCentral.setLayout(
                new BoxLayout(
                        painelCentral,
                        BoxLayout.Y_AXIS
                )
        );

        painelCentral.add(criarLogo());

        painelCentral.add(
                Box.createVerticalStrut(14)
        );

        JLabel lblTitulo =
                new JLabel(
                        "HELP SYSTEM",
                        SwingConstants.CENTER
                );

        lblTitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        23
                )
        );

        lblTitulo.setForeground(COR_TEXTO);

        lblTitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painelCentral.add(lblTitulo);

        painelCentral.add(
                Box.createVerticalStrut(5)
        );

        JLabel lblSubtitulo =
                new JLabel(
                        "Acesse sua conta",
                        SwingConstants.CENTER
                );

        lblSubtitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        lblSubtitulo.setForeground(
                COR_SECUNDARIA
        );

        lblSubtitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painelCentral.add(lblSubtitulo);

        painelCentral.add(
                Box.createVerticalStrut(28)
        );

        painelCentral.add(criarCartaoLogin());

        painelFundo.add(painelCentral);

        setContentPane(painelFundo);

        getRootPane().setDefaultButton(
                btnEntrar
        );
    }

    private JPanel criarLogo() {

        RoundedPanel painelLogo =
                new RoundedPanel(
                        16,
                        COR_AZUL
                );

        painelLogo.setLayout(
                new GridBagLayout()
        );

        Dimension tamanhoLogo =
                new Dimension(52, 52);

        painelLogo.setPreferredSize(tamanhoLogo);
        painelLogo.setMaximumSize(tamanhoLogo);
        painelLogo.setMinimumSize(tamanhoLogo);

        painelLogo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel lblLogo =
                new JLabel("H");

        lblLogo.setForeground(Color.WHITE);

        lblLogo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        21
                )
        );

        painelLogo.add(lblLogo);

        return painelLogo;
    }

    private JPanel criarCartaoLogin() {

        RoundedPanel cartao =
                new RoundedPanel(
                        18,
                        Color.WHITE
                );

        cartao.setLayout(
                new BorderLayout()
        );

        Dimension tamanhoCartao =
                new Dimension(390, 330);

        cartao.setPreferredSize(tamanhoCartao);
        cartao.setMaximumSize(tamanhoCartao);
        cartao.setMinimumSize(tamanhoCartao);

        cartao.setAlignmentX(
                Component.CENTER_ALIGNMENT
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

        return cartao;
    }

    private JPanel criarCabecalho() {

        JPanel painelCabecalho =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                14
                        )
                );

        painelCabecalho.setBackground(
                COR_AZUL
        );

        JLabel lblAutenticacao =
                new JLabel(
                        "Autenticação",
                        SwingConstants.CENTER
                );

        lblAutenticacao.setForeground(
                Color.WHITE
        );

        lblAutenticacao.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        painelCabecalho.add(
                lblAutenticacao
        );

        return painelCabecalho;
    }

    private JPanel criarFormulario() {

        JPanel formulario =
                new JPanel();

        formulario.setBackground(
                Color.WHITE
        );

        formulario.setBorder(
                new EmptyBorder(
                        20,
                        24,
                        18,
                        24
                )
        );

        formulario.setLayout(
                new BoxLayout(
                        formulario,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblEmail =
                criarLabelCampo("E-MAIL");

        txtEmail =
                new JTextField();

        configurarCampo(txtEmail);

        JLabel lblSenha =
                criarLabelCampo("SENHA");

        txtSenha =
                new JPasswordField();

        configurarCampo(txtSenha);

        btnEntrar =
                new RoundedButton(
                        "↪  Entrar",
                        8
                );

        btnEntrar.setBackground(
                COR_AZUL
        );

        btnEntrar.setForeground(
                Color.WHITE
        );

        btnEntrar.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        Dimension tamanhoBotao =
                new Dimension(340, 42);

        btnEntrar.setPreferredSize(tamanhoBotao);
        btnEntrar.setMaximumSize(tamanhoBotao);
        btnEntrar.setMinimumSize(tamanhoBotao);

        btnEntrar.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );


        formulario.add(lblEmail);

        formulario.add(
                Box.createVerticalStrut(7)
        );

        formulario.add(txtEmail);

        formulario.add(
                Box.createVerticalStrut(16)
        );

        formulario.add(lblSenha);

        formulario.add(
                Box.createVerticalStrut(7)
        );

        formulario.add(txtSenha);

        formulario.add(
                Box.createVerticalStrut(18)
        );

        JPanel painelBotao =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                0
                        )
                );

        painelBotao.setOpaque(false);

        painelBotao.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painelBotao.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        painelBotao.add(btnEntrar);

        formulario.add(painelBotao);


        return formulario;
    }

    private JLabel criarLabelCampo(
            String texto
    ) {

        JLabel label =
                new JLabel(
                        texto,
                        SwingConstants.CENTER
                );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        label.setForeground(
                COR_SECUNDARIA
        );

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return label;
    }

    private void configurarCampo(
            JTextField campo
    ) {

        campo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        Dimension tamanhoCampo =
                new Dimension(340, 40);

        campo.setPreferredSize(tamanhoCampo);
        campo.setMaximumSize(tamanhoCampo);
        campo.setMinimumSize(tamanhoCampo);

        campo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                COR_BORDA,
                                1
                        ),
                        new EmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );
    }

    private void configurarEventos() {

        btnEntrar.addActionListener(
                evento -> realizarLogin()
        );

        txtSenha.addActionListener(
                evento -> realizarLogin()
        );
    }

    private void realizarLogin() {

        String email =
                txtEmail.getText().trim();

        String senha =
                new String(
                        txtSenha.getPassword()
                );

        btnEntrar.setEnabled(false);

        try {

            Usuario usuario =
                    usuarioService.login(
                            email,
                            senha
                    );

            SessaoUsuario.iniciar(usuario);

            JOptionPane.showMessageDialog(
                    this,
                    "Bem-vindo, "
                            + usuario.getNome()
                            + "!",
                    "Login realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            boolean administrador =
                    usuario.getTipo()
                            == TipoUsuario.ADMIN;

            new MenuView(
                    administrador,
                    SessaoUsuario.getUsuarioAtual()
            ).setVisible(true);

            dispose();

        } catch (IllegalArgumentException e) {

            mostrarAviso(
                    e.getMessage()
            );

            txtSenha.setText("");
            txtSenha.requestFocus();

        } catch (RuntimeException e) {

            mostrarErro(
                    "Não foi possível consultar "
                            + "o usuário no banco."
            );

            e.printStackTrace();

        } finally {

            btnEntrar.setEnabled(true);
        }
    }

    private void mostrarAviso(
            String mensagem
    ) {

        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Login não realizado",
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

        public RoundedButton(
                String texto,
                int raio
        ) {

            super(texto);

            this.raio = raio;

            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
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

            if (isEnabled()) {
                g2.setColor(getBackground());
            } else {
                g2.setColor(getBackground().darker());
            }

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
}