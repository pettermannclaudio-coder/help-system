package view;

import model.Resposta;
import model.Solicitacao;
import service.RespostaService;

import javax.swing.*;
import java.awt.*;

public class DetalheSolicitacaoView extends JFrame {

    private final Solicitacao solicitacao;

    private final RespostaService respostaService =
            new RespostaService();

    public DetalheSolicitacaoView(Solicitacao solicitacao) {

        this.solicitacao = solicitacao;

        setTitle("Detalhes da Solicitação");

        setSize(700,600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criarTela();

    }

    private void criarTela() {

        JPanel painel = new JPanel(new BorderLayout());

        JPanel dados = new JPanel(new GridLayout(0,1,5,5));

        dados.setBorder(
                BorderFactory.createTitledBorder("Solicitação")
        );

        dados.add(new JLabel(
                "Título: " + solicitacao.getTitulo()
        ));

        dados.add(new JLabel(
                "Criado por: " +
                        solicitacao.getUsuario().getNome()
        ));

        dados.add(new JLabel(
                "Departamento: " +
                        solicitacao.getDepartamento().getNome()
        ));

        dados.add(new JLabel(
                "Status: " +
                        solicitacao.getStatus()
        ));

        dados.add(new JLabel(
                "Data: " +
                        solicitacao.getDataCriacao()
        ));

        JTextArea descricao = new JTextArea(
                solicitacao.getDescricao()
        );

        descricao.setLineWrap(true);

        descricao.setWrapStyleWord(true);

        descricao.setEditable(false);

        JPanel descricaoPanel = new JPanel(
                new BorderLayout()
        );

        descricaoPanel.setBorder(
                BorderFactory.createTitledBorder("Descrição")
        );

        descricaoPanel.add(
                new JScrollPane(descricao)
        );

        JPanel topo = new JPanel(
                new BorderLayout()
        );

        topo.add(dados, BorderLayout.NORTH);

        topo.add(descricaoPanel, BorderLayout.CENTER);

        painel.add(topo, BorderLayout.NORTH);

        JTextArea respostas = new JTextArea();

        respostas.setEditable(false);

        for (Resposta resposta :
                respostaService.buscarPorSolicitacao(
                        solicitacao.getId()
                )) {

            respostas.append(
                    resposta.getUsuario().getNome()
                            + " ("
                            + resposta.getDataResposta()
                            + ")\n\n"

                            + resposta.getDescricao()

                            + "\n\n---------------------------------------\n\n"
            );

        }

        JPanel painelResposta = new JPanel(
                new BorderLayout()
        );

        painelResposta.setBorder(
                BorderFactory.createTitledBorder("Respostas")
        );

        painelResposta.add(
                new JScrollPane(respostas)
        );

        painel.add(
                painelResposta,
                BorderLayout.CENTER
        );

        JButton responder =
                new JButton("Responder");

        responder.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Tela de resposta."
            );

        });

        JButton fechar =
                new JButton("Fechar");

        fechar.addActionListener(e -> dispose());

        JPanel botoes = new JPanel();

        botoes.add(responder);

        botoes.add(fechar);

        painel.add(
                botoes,
                BorderLayout.SOUTH
        );

        add(painel);

    }

}