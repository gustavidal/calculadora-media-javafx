package br.senai.sp.jandira.mediafinal.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class TelaMediaFinal extends Application {

    VBox painelResultado;
    TextField tfNomeAluno;
    TextField tfNota1;
    TextField tfNota2;
    TextField tfNota3;
    TextField tfNota4;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(600);
        stage.setHeight(500);
        stage.setResizable(false);
        stage.setTitle("Média Final");

        // Criar o painel principal (root)
        BorderPane root = new BorderPane();

        // Criar o label com o título da tela
        Label titulo = new Label();
        titulo.setText("Escola SENAI \"Prof. Vicente Amato\"");
        titulo.setStyle("-fx-font-size: 22; -fx-text-fill: white");
        titulo.setPadding(new Insets(10, 0, 10, 10));

        HBox painelSuperior = new HBox();
        painelSuperior.setStyle("-fx-background-color: #a7a3f9");
        painelSuperior.getChildren().addAll(titulo);

        // Criação do formulário
        VBox painelFormulario = new VBox();
        painelFormulario.setSpacing(3);
        painelFormulario.setStyle("-fx-background-color: #a7b3f9");
        painelFormulario.setAlignment(Pos.CENTER_LEFT);
        painelFormulario.setPadding(new Insets(30));
        Label lblNomeAluno = new Label("Nome do aluno:");
        Label lblNota1 = new Label("Nota 1:");
        Label lblNota2 = new Label("Nota 2:");
        Label lblNota3 = new Label("Nota 3:");
        Label lblNota4 = new Label("Nota 4:");
        tfNomeAluno = new TextField();
        tfNota1 = new TextField();
        tfNota2 = new TextField();
        tfNota3 = new TextField();
        tfNota4 = new TextField();
        painelFormulario.getChildren().addAll(
                lblNomeAluno, tfNomeAluno,
                lblNota1, tfNota1,
                lblNota2, tfNota2,
                lblNota3, tfNota3,
                lblNota4, tfNota4
        );

        VBox painelBotoes = new VBox();
        painelBotoes.setSpacing(7);
        painelBotoes.setAlignment(Pos.CENTER);
        painelBotoes.setPadding(new Insets(0, 10, 0, 0));
        painelBotoes.setStyle("-fx-background-color: #a7b3f9");
        Button btCalcularMedia = new Button("Calcular média");
        btCalcularMedia.setPrefWidth(150);
        btCalcularMedia.setPrefHeight(50);
        Button btLimpar = new Button("Limpar");
        btLimpar.setPrefWidth(150);
        btLimpar.setPrefHeight(40);
        Button btSair = new Button("Sair");
        btSair.setPrefWidth(150);
        btSair.setPrefHeight(40);
        painelBotoes.getChildren().addAll(
                btCalcularMedia,
                btLimpar,
                btSair
        );

        painelResultado = new VBox();
        painelResultado.setPadding(new Insets(15));
        painelResultado.setStyle("-fx-background-color: #a7a3f9");
        Label lblResultados = new Label("Resultados");
        lblResultados.setStyle("-fx-font-size: 22; -fx-text-fill: white; -fx-font-weight: bold");
        Label lblNomeResultado = new Label("Nome do aluno: ");
        lblNomeResultado.setStyle("-fx-text-fill: white");
        Label lblMediaFinal = new Label("Média final: ");
        lblMediaFinal.setStyle("-fx-text-fill: white");
        Label lblSituacao = new Label("Situação: ");
        lblSituacao.setStyle("-fx-text-fill: white");
        painelResultado.getChildren().addAll(
                lblResultados, lblNomeResultado,
                lblMediaFinal, lblSituacao
        );

        // Adicionando conteúdo ao root
        root.setTop(painelSuperior);
        root.setCenter(painelFormulario);
        root.setRight(painelBotoes);
        root.setBottom(painelResultado);

        // Criar o objeto scene
        Scene scene = new Scene(root);

        // Colocar a scene no stage
        stage.setScene(scene);
        stage.show();

        // Interceptar cliques nos botões
        btCalcularMedia.setOnAction(e -> {
            if(validarEntradas()) {
                String nomeAluno = tfNomeAluno.getText();
                lblNomeResultado.setText("Nome do aluno: " + nomeAluno);

                String nota1 = tfNota1.getText();
                String nota2 = tfNota2.getText();
                String nota3 = tfNota3.getText();
                String nota4 = tfNota4.getText();

                double media = calcularMedia(nota1, nota2, nota3, nota4);
                String mediaDecimal = String.format("%.2f", media);
                lblMediaFinal.setText("Média final: " + mediaDecimal);

                String situacao = definirSituacao(media);
                lblSituacao.setText("Situação: " + situacao);
            }
        });

        btLimpar.setOnAction(e -> {
            tfNomeAluno.setText("");
            tfNota1.setText("");
            tfNota2.setText("");
            tfNota3.setText("");
            tfNota4.setText("");
            lblNomeResultado.setText("Nome do aluno:");
            lblMediaFinal.setText("Média final:");
            lblSituacao.setText("Situação:");
            tfNomeAluno.requestFocus();
            painelResultado.setStyle("-fx-background-color: #a7a3f9");
        });

        btSair.setOnAction(e -> {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "Sair do programa?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> resposta = alerta.showAndWait();

            if (resposta.get() == ButtonType.YES){
                System.exit(0);
            }
        });
    }

    private double calcularMedia(String n1, String n2, String n3, String n4) {
        double nota1 = Double.parseDouble(n1);
        double nota2 = Double.parseDouble(n2);
        double nota3 = Double.parseDouble(n3);
        double nota4 = Double.parseDouble(n4);
        double media = (nota1 + nota2 + nota3 + nota4) / 4;
        return media;
    }

    private String definirSituacao(double media){
        if (media < 4.0){
            painelResultado.setStyle("-fx-background-color: #ff0000");
            return "REPROVADO";
        } else if (media >= 6.0){
            painelResultado.setStyle("-fx-background-color: #00ff00");
            return "APROVADO";
        } else {
            painelResultado.setStyle("-fx-background-color: #ffa500");
            return "RECUPERAÇÃO";
        }
    }

    private boolean validarEntradas(){
        if (tfNomeAluno.getText().isEmpty()){
            mostrarMensagem(Alert.AlertType.ERROR, "Preencha o nome do aluno!");
            tfNomeAluno.requestFocus();
            return false;
        } else if (tfNota1.getText().isEmpty()){
            mostrarMensagem(Alert.AlertType.ERROR, "Preencha a nota 1 do aluno!");
            tfNota1.requestFocus();
            return false;
        } else if (tfNota2.getText().isEmpty()){
            mostrarMensagem(Alert.AlertType.ERROR, "Preencha a nota 2 do aluno!");
            tfNota2.requestFocus();
            return false;
        } else if (tfNota3.getText().isEmpty()){
            mostrarMensagem(Alert.AlertType.ERROR, "Preencha a nota 3 do aluno!");
            tfNota3.requestFocus();
            return false;
        } else if (tfNota4.getText().isEmpty()){
            mostrarMensagem(Alert.AlertType.ERROR, "Preencha a nota 4 do aluno!");
            tfNota4.requestFocus();
            return false;
        }
        return true;
    }

    private void mostrarMensagem(Alert.AlertType tipo, String mensagem){
        Alert alerta = new Alert(tipo, mensagem);
        alerta.showAndWait();
    }
}
