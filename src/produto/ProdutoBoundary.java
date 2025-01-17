package produto;

import categoria.Categoria;
import categoria.CategoriaController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import marca.Marca;
import marca.MarcaBoundary;
import marca.MarcaController;
import tamanho.Tamanho;
import tamanho.TamanhoController;

public class ProdutoBoundary extends Application {

    private TextField txtNome = new TextField();
    private TextField txtCodigo = new TextField();
    private TextField txtCor = new TextField();

    private ComboBox<String> cbMarca = new ComboBox<>();
    private ComboBox<String> cbCategoria = new ComboBox<>();
    private ComboBox<String> cbTamanho = new ComboBox<>();

    private TextField txtPreco = new TextField();
    private TextField txtQuantidade = new TextField();
    private TextField txtDescricao = new TextField();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAlterar = new Button("Alterar");
    private Button btnExcluir = new Button("Excluir");
    private Button btnConcluir = new Button("Concluir");

    ProdutoController prodCont = new ProdutoController();
    private MarcaController mcControl = new MarcaController();
    private CategoriaController ctControl = new CategoriaController();
    private TamanhoController tmControl = new TamanhoController();

    private String cbMarcaValue;
    private String cbTamanhoValue;
    private String cbCategoriaValue;

    private String cssBorda =   "-fx-border-color: grey;\n" +
                                "-fx-border-insets: 3;\n" +
                                "-fx-border-width: 1;\n" ;

    private Alert alertWarn = new Alert(Alert.AlertType.WARNING);
    private Alert alertMess = new Alert(Alert.AlertType.INFORMATION);

    private HBox addBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(20, 10, 10, 15));
        hbox.setSpacing(10);
        return hbox;
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane border = new BorderPane();
        HBox hbox = addBox();
        border.setTop(hbox);
        border.getStylesheets().add(MarcaBoundary.class.getResource("StyleText.css").toExternalForm());
        btnAdicionar.setVisible(true);
        txtNome.setEditable(true);
        txtCodigo.setEditable(true);
        txtCor.setEditable(true);
        txtPreco.setEditable(true);
        txtQuantidade.setEditable(true);
        txtDescricao.setEditable(true);
        cbTamanho.setDisable(false);
        cbMarca.setDisable(false);
        cbCategoria.setDisable(false);
        btnConcluir.setVisible(false);
        btnAlterar.setVisible(false);
        btnExcluir.setVisible(false);

        carregarCombo();

        BorderPane bP = new BorderPane();
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(border, 700, 450);
        gridPane.setVgap(20);
        gridPane.setHgap(5);
        bP.setCenter(gridPane);
        border.setCenter(bP);

        Label lblNome = new Label("Nome:");
        Label lblCodigo = new Label("Codigo:");
        Label lblCor = new Label("Cor:");
        Label lblMarca = new Label("Marca:");
        Label lblCategoria = new Label("Categoria:");
        Label lblTamanho = new Label("Tamanho:");
        Label lblPreco = new Label("Preço:");
        Label lblQuantidade = new Label("Quantidade em Estoque:");
        Label lblDescricao = new Label("Descrição:");

        gridPane.add(lblNome,0,1);
        gridPane.add(txtNome,1,1);
        gridPane.add(lblCodigo,0,2);
        gridPane.add(txtCodigo,1,2);
        gridPane.add(lblCor,2,2);
        gridPane.add(txtCor,3,2);
        gridPane.add(lblMarca,0,3);
        gridPane.add(cbMarca,1,3);
        gridPane.add(lblCategoria, 0,4);
        gridPane.add(cbCategoria,1,4);
        gridPane.add(lblTamanho,3,4);
        gridPane.add(cbTamanho,4,4);
        gridPane.add(lblPreco,0,5);
        gridPane.add(txtPreco,1,5);
        gridPane.add(lblQuantidade,2,5);
        gridPane.add(txtQuantidade,3,5);
        gridPane.add(lblDescricao,0,6);
        txtDescricao.getStylesheets().add(ProdutoBoundary.class.getResource("StylesProduto.css").toExternalForm());

        BorderPane pane = new BorderPane();
        AnchorPane anchorPane = new AnchorPane();
        HBox box = new HBox();
        box.setPadding(new Insets(0,40,10,20));
        box.setSpacing(10);
        box.getChildren().addAll(txtDescricao);
        anchorPane.getChildren().addAll(box);
        HBox box2 = new HBox();
        box2.getChildren().addAll(btnAlterar,btnConcluir,btnExcluir, btnAdicionar);
        btnExcluir.getStylesheets().add(ProdutoBoundary.class.getResource("StylesProduto.css").toExternalForm());
        box2.setPadding(new Insets(0,0,5,250));
        box2.setSpacing(20);
        bP.setStyle(cssBorda);
        bP.setBottom(anchorPane);
        pane.setBottom(box2);
        border.setBottom(pane);

        btnAdicionar.setOnAction((event -> {
            prodCont.adicionar(boundaryToEntity());
            this.entityToBoundary(new Produto());
            alertMess.setHeaderText("CADASTRADO COM SUCESSO!");
            alertMess.showAndWait();
        }));

        btnExcluir.setOnAction((event -> {
            try{
                prodCont.excluir(Integer.parseInt(txtCodigo.getText()));
            }catch (Exception e){

            }
            alertMess.setHeaderText("EXCLUIDO COM SUCESSO!");
            alertMess.showAndWait();
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
            stage.close();
        }));

        btnAlterar.setOnAction((event -> {
            txtNome.setEditable(true);
            txtCor.setEditable(true);
            txtPreco.setEditable(true);
            txtQuantidade.setEditable(true);
            txtDescricao.setEditable(true);
            cbTamanho.setDisable(false);
            cbMarca.setDisable(false);
            cbCategoria.setDisable(false);

            btnExcluir.setVisible(false);
            btnConcluir.setVisible(true);
            btnAlterar.setVisible(false);
        }));

        btnConcluir.setOnAction((event -> {
            prodCont.alterar(boundaryToEntity());
            alertMess.setHeaderText("ALTERADO COM SUCESSO!");
            alertMess.showAndWait();
            btnConcluir.setVisible(false);

            stage.close();
        }));

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Produto S2");
        stage.show();
    }

    public Produto boundaryToEntity(){
        Produto Pd = new Produto();
        Pd.setNomeProduto(txtNome.getText());
        Pd.setCor(txtCor.getText());
        Pd.setDescricao(txtDescricao.getText());
        cbMarcaValue = cbMarca.getValue();
        cbCategoriaValue = cbCategoria.getValue();
        cbTamanhoValue = cbTamanho.getValue();
        try {
            Pd.setCodProduto(Integer.parseInt(txtCodigo.getText()));
            Pd.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            Pd.setPreco(Double.parseDouble(txtPreco.getText()));
            Pd.setCodMarca(prodCont.buscarPorNomeMarca(cbMarcaValue));
            Pd.setCodCategoria(prodCont.buscarPorNomeCategoria(cbCategoriaValue));
            Pd.setCodTamanho(prodCont.buscarPorNomeTamanho(cbTamanhoValue));
        }catch (Exception e){

        }
        return Pd;
    }

    public boolean entityToBoundary(Produto Pd) {
        try {
            if (Pd != null) {
                Marca mc;
                Tamanho tm;
                Categoria ct;

                txtCodigo.setText(String.valueOf(Pd.getCodProduto()));
                txtNome.setText(Pd.getNomeProduto());
                txtDescricao.setText(Pd.getDescricao());
                txtCor.setText(Pd.getCor());
                txtQuantidade.setText(String.valueOf(Pd.getQuantidade()));
                txtPreco.setText(String.valueOf(Pd.getPreco()));

                mc = mcControl.pesquisarPorCodigo(Pd.getCodMarca());
                cbMarca.setValue(mc.getNomeMarca());

                tm = tmControl.pesquisarPorCodigo(Pd.getCodTamanho());
                cbTamanho.setValue(tm.getTamanho());

                ct = ctControl.pesquisarPorCodigo(Pd.getCodCategoria());
                cbCategoria.setValue(ct.getCategoria());

                return true;
            } else {
                entityToBoundary(new Produto());
                alertMess.setHeaderText("PRODUTO NÃO EXISTE.");
                alertMess.showAndWait();
            }
        }catch (Exception e){

        }
        return false;
    }

    public void carregarCombo(){
        cbMarca.getItems().removeAll(cbMarca.getItems());
        cbCategoria.getItems().removeAll(cbCategoria.getItems());
        cbTamanho.getItems().removeAll(cbTamanho.getItems());
        cbMarca.getItems().addAll(prodCont.carregarMarcas());
        cbTamanho.getItems().addAll(prodCont.carregarTamanhos());
        cbCategoria.getItems().addAll(prodCont.carregarCategoria());
    }

    public void pesquisar(int cod){
        txtNome.setEditable(false);
        txtCodigo.setEditable(false);
        txtCor.setEditable(false);
        txtPreco.setEditable(false);
        txtQuantidade.setEditable(false);
        txtDescricao.setEditable(false);
        cbTamanho.setDisable(true);
        cbMarca.setDisable(true);
        cbCategoria.setDisable(true);
        btnConcluir.setVisible(false);
        btnAdicionar.setVisible(false);
        if(entityToBoundary(prodCont.pesquisarPorCodigo(cod))){
            btnAlterar.setVisible(true);
            btnExcluir.setVisible(true);
        }
    }
}
