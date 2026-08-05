package view;

import model.Solicitacao;
import service.SolicitacaoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SolicitacaoView extends JFrame {

    private JTextField txtPesquisa;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JButton btnPesquisar;
    private JButton btnNova;
    private JButton btnVisualizar;
    private JButton btnFechar;

    private final SolicitacaoService solicitacaoService =
            new SolicitacaoService();

    public SolicitacaoView() {

        configurarJanela();

        criarComponentes();

        carregarSolicitacoes();
    }

    private void configurarJanela() {

        setTitle("Solicitações");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    private void criarComponentes() {

        JPanel painel = new JPanel(new BorderLayout());

        JPanel topo = new JPanel();

        txtPesquisa = new JTextField(20);

        btnPesquisar = new JButton("Pesquisar");

        topo.add(new JLabel("Pesquisar:"));
        topo.add(txtPesquisa);
        topo.add(btnPesquisar);

        painel.add(topo, BorderLayout.NORTH);

        String[] colunas = {
                "ID",
                "Título",
                "Departamento",
                "Status"
        };

        modelo = new DefaultTableModel(colunas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        tabela = new JTable(modelo);

        painel.add(
                new JScrollPane(tabela),
                BorderLayout.CENTER
        );


        JPanel botoes = new JPanel();

        btnNova = new JButton("Nova");

        btnVisualizar = new JButton("Visualizar");

        btnFechar = new JButton("Fechar");

        botoes.add(btnNova);
        botoes.add(btnVisualizar);
        botoes.add(btnFechar);

        painel.add(
                botoes,
                BorderLayout.SOUTH
        );

        add(painel);

        configurarEventos();

    }

    private void configurarEventos() {

        btnFechar.addActionListener(evento -> dispose());

        btnPesquisar.addActionListener(evento -> pesquisar());

        btnNova.addActionListener(evento -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Tela de nova solicitação ainda será implementada."
            );

        });

        btnVisualizar.addActionListener(evento -> visualizar());

    }

    private void carregarSolicitacoes() {

        modelo.setRowCount(0);

        for (Solicitacao solicitacao :
                solicitacaoService.listar()) {

            modelo.addRow(new Object[]{

                    solicitacao.getId(),
                    solicitacao.getTitulo(),
                    solicitacao.getDepartamento().getNome(),
                    solicitacao.getStatus()

            });

        }

    }

    private void pesquisar() {

        String texto = txtPesquisa.getText().trim().toLowerCase();

        modelo.setRowCount(0);

        for (Solicitacao solicitacao :
                solicitacaoService.listar()) {

            if (texto.isBlank()
                    || solicitacao.getTitulo()
                    .toLowerCase()
                    .contains(texto)) {

                modelo.addRow(new Object[]{

                        solicitacao.getId(),
                        solicitacao.getTitulo(),
                        solicitacao.getDepartamento().getNome(),
                        solicitacao.getStatus()

                });

            }

        }

    }

    private void visualizar() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma solicitação."
            );

            return;

        }

        Integer id = (Integer) modelo.getValueAt(linha, 0);

        Solicitacao solicitacao = solicitacaoService.buscarPorId(id);

        new DetalheSolicitacaoView(solicitacao).setVisible(true);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new SolicitacaoView().setVisible(true);

        });

    }

}