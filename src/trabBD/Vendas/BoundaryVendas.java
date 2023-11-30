package trabBD.Vendas;

import java.sql.Date;
import java.sql.SQLException;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LongStringConverter;
import trabBD.Principal.IPrincipal;
import javafx.util.converter.IntegerStringConverter;

public class BoundaryVendas implements IPrincipal {
	private TextField txtidVend = new TextField();
	private TextField txtQnt = new TextField();
	private TextField txtDt = new TextField();
	private TextField txtTot = new TextField();
	
	private TextField txtCodProd = new TextField();
	private TextField txtNomeProd = new TextField();
	private TextField txtPrecoUnid = new TextField();
	private TextField txtDescProd = new TextField();
	private TextField txtQntProd = new TextField();
	
	private CtrlVendas ctrlVend;
	private TableView<Vendas> table = new TableView<>();
	
	private AnchorPane principal;

//===================================================================================================================================
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void ligacoes() { 
		Bindings.bindBidirectional(txtidVend.textProperty(), ctrlVend.getIdVend(),(StringConverter) new LongStringConverter());
		Bindings.bindBidirectional(txtQnt.textProperty(), ctrlVend.getQnt(), (StringConverter) new IntegerStringConverter());
		Bindings.bindBidirectional(txtDt.textProperty(), ctrlVend.getDt(), (StringConverter) new DateStringConverter());
		Bindings.bindBidirectional(txtTot.textProperty(), ctrlVend.getTotal(), (StringConverter) new DoubleStringConverter());
		
		
		Bindings.bindBidirectional(txtCodProd.textProperty(), ctrlVend.getCodProd(),(StringConverter) new LongStringConverter());
		Bindings.bindBidirectional(txtNomeProd.textProperty(), ctrlVend.getNome());
		Bindings.bindBidirectional(txtDescProd.textProperty(), ctrlVend.getDesc());
		Bindings.bindBidirectional(txtPrecoUnid.textProperty(), ctrlVend.getPreco(),(StringConverter) new DoubleStringConverter());
		Bindings.bindBidirectional(txtQntProd.textProperty(), ctrlVend.getQntProd(),(StringConverter) new IntegerStringConverter());
	}
	
	// ---------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public void abastecerTableView() { 
		TableColumn<Vendas, Long> colIdVend = new TableColumn<>("Venda");
		colIdVend.setCellValueFactory(new PropertyValueFactory<Vendas, Long>("idVendas"));
		
		TableColumn<Vendas, Integer> colQnt = new TableColumn<>("Quantidade");
		colQnt.setCellValueFactory(new PropertyValueFactory<Vendas, Integer>("qntidade"));
		
		TableColumn<Vendas, Date> colData = new TableColumn<>("Data");
		colData.setCellValueFactory(new PropertyValueFactory<Vendas, Date>("sqlDate"));

		TableColumn<Vendas, Double> colTotal = new TableColumn<>("Total");
		colTotal.setCellValueFactory(new PropertyValueFactory<Vendas, Double>("total"));
		
		TableColumn<Vendas, Long> colCodP = new TableColumn<>("Cod Prod");
		colCodP.setCellValueFactory(new PropertyValueFactory<Vendas, Long>("codProd"));
		
		TableColumn<Vendas, String> colNome = new TableColumn<>("Produto");
		colNome.setCellValueFactory(new PropertyValueFactory<Vendas, String>("nome"));
		
		TableColumn<Vendas, String> colDesc = new TableColumn<>("Descricao");
		colDesc.setCellValueFactory(new PropertyValueFactory<Vendas, String>("desc"));
		
		TableColumn<Vendas, Double> colPrecoUnit = new TableColumn<>("Preco unid");
		colPrecoUnit.setCellValueFactory(new PropertyValueFactory<Vendas, Double>("precoUnit"));
		
		TableColumn<Vendas, Integer> colQntRes = new TableColumn<>("Restante");
		colQntRes.setCellValueFactory(new PropertyValueFactory<Vendas, Integer>("qntProd"));
		
		//------------------------------------------------------------------------------------------------------
		
		TableColumn<Vendas, Void> colAcoes = new TableColumn<>("Ações");
		Callback<TableColumn<Vendas, Void>, TableCell<Vendas, Void>> callBack = new Callback<>() {

			@Override
			public TableCell<Vendas, Void> call(TableColumn<Vendas, Void> col) {
				TableCell<Vendas, Void> tCell = new TableCell<>() {

					final Button btnExcluir = new Button("Estornar");
					{
						btnExcluir.setOnAction(e -> {
							Vendas p = table.getItems().get(getIndex());
							try {
								ctrlVend.excluir(p);
							} catch (SQLException e1) {
								Alert a = new Alert(AlertType.ERROR,"Erro ao excluir, contate o Administrador", ButtonType.OK);
								a.showAndWait();
							}
						});
					}

					
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btnExcluir);
						}
					}
				};
				return tCell;
			}
		};
		
		colAcoes.setCellFactory(callBack);
		
		double dec = 960.0 / 10.0;
		
		colIdVend.setPrefWidth(dec);
		colQnt.setPrefWidth(dec);
		colData.setPrefWidth(dec);
		colTotal.setPrefWidth(dec);
		
		colCodP.setPrefWidth(dec);
		colNome.setPrefWidth(dec);
		colDesc.setPrefWidth(dec);
		colPrecoUnit.setPrefWidth(dec);
		colQntRes.setPrefWidth(dec);
		
		colAcoes.setPrefWidth(dec);
		
		// ---------------------------------------------------------------------------------
		
		table.getColumns().addAll(colIdVend, colQnt, colData, colTotal, colCodP, colNome, colDesc, colPrecoUnit, colQntRes, colAcoes);
		table.setItems(ctrlVend.getListaProd());

		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Vendas>() {
			@Override
			public void onChanged(Change<? extends Vendas> v) {
				if (!v.getList().isEmpty()) {
					ctrlVend.fromEntity(v.getList().get(0));
				}
			}
		});
	}
	
	//===================================================================================================================================
	
	@Override
	public void start() {
		try {
			ctrlVend = new CtrlVendas();
		} catch(SQLException | ClassNotFoundException e) { 
			Alert a = new Alert(AlertType.ERROR, "Erro ao acessar o banco de dados, contate o Administrador", ButtonType.OK);
			a.showAndWait();
		}
		principal = new AnchorPane();
		
		// ---------------------------------------------------------------------------------
		
		ligacoes();
		abastecerTableView();

		// ---------------------------------------------------------------------------------
		Label lblProduto = new Label("VENDIDOS");
		lblProduto.setCenterShape(true);
		lblProduto.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 20px; -fx-font-family: Arial;");
		AnchorPane.setTopAnchor(lblProduto, 15.0);
		AnchorPane.setLeftAnchor(lblProduto, 20.0);
		
		
		Label lblIdVend = new Label("Venda: ");
		lblIdVend.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");
		AnchorPane.setTopAnchor(lblIdVend, 50.0);
		AnchorPane.setLeftAnchor(lblIdVend, 20.0);
		
		AnchorPane.setTopAnchor(txtidVend, 45.0);
		AnchorPane.setLeftAnchor(txtidVend, 60.0);
		txtidVend.setPrefWidth(60.0);
		
		Label lblQntVend = new Label("Vendido:");
		lblQntVend.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");
		AnchorPane.setTopAnchor(lblQntVend, 50.0);
		AnchorPane.setLeftAnchor(lblQntVend, 130.0);
		
		AnchorPane.setTopAnchor(txtQnt, 45.0);
		AnchorPane.setLeftAnchor(txtQnt, 180.0);
		txtQnt.setPrefWidth(60.0);
		
		Label lblDtVend = new Label("Data:");
		lblDtVend.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");
		AnchorPane.setTopAnchor(lblDtVend, 50.0);
		AnchorPane.setLeftAnchor(lblDtVend, 250.0);
		
		AnchorPane.setTopAnchor(txtDt, 45.0);
		AnchorPane.setLeftAnchor(txtDt, 280.0);
		txtDt.setPrefWidth(120.0);
		
		Label lblCodProd = new Label("Codigo do Produto:");
		lblCodProd.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");
		AnchorPane.setTopAnchor(lblCodProd, 50.0);
		AnchorPane.setLeftAnchor(lblCodProd, 410.0);
		
		AnchorPane.setTopAnchor(txtCodProd, 45.0);
		AnchorPane.setLeftAnchor(txtCodProd, 515.0);
		txtCodProd.setPrefWidth(120.0);
		
		Label lblProd = new Label("Produto:");
		lblProd.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");
		AnchorPane.setTopAnchor(lblProd, 50.0);
		AnchorPane.setLeftAnchor(lblProd, 650.0);
		
		AnchorPane.setTopAnchor(txtNomeProd, 45.0);
		AnchorPane.setLeftAnchor(txtNomeProd, 700.0);
		AnchorPane.setRightAnchor(txtNomeProd, 20.0);
		txtNomeProd.setPrefWidth(120.0);
		
		Label lblDesc = new Label("Descricao:");
		lblDesc.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");
		AnchorPane.setTopAnchor(lblDesc, 90.0);
		AnchorPane.setLeftAnchor(lblDesc, 20.0);
		
		AnchorPane.setTopAnchor(txtDescProd, 85.0);
		AnchorPane.setLeftAnchor(txtDescProd, 85.0);
		txtDescProd.setPrefWidth(550.0);
		
		Label lblPreco = new Label("Preco unit:");
		lblPreco.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");
		AnchorPane.setTopAnchor(lblPreco, 90.0);
		AnchorPane.setLeftAnchor(lblPreco, 650.0);
		
		AnchorPane.setTopAnchor(txtPrecoUnid, 85.0);
		AnchorPane.setLeftAnchor(txtPrecoUnid, 710.0);
		txtPrecoUnid.setPrefWidth(60.0);
		
		Label lblQntProd = new Label("Restante:");
		lblQntProd.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");
		AnchorPane.setTopAnchor(lblQntProd, 90.0);
		AnchorPane.setLeftAnchor(lblQntProd, 780.0);
		
		AnchorPane.setTopAnchor(txtQntProd, 85.0);
		AnchorPane.setLeftAnchor(txtQntProd, 840.0);
		txtQntProd.setPrefWidth(60.0);
		
		// ---------------------------------------------------------------------------------

		Button btnLimpar = new Button("Limpar");
		btnLimpar.setOnAction(e -> ctrlVend.limpar());
		AnchorPane.setTopAnchor(btnLimpar, 120.0);
		AnchorPane.setRightAnchor(btnLimpar, 20.0);
		btnLimpar.setPrefWidth(100.0);
		
		
		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> {
			try {
				ctrlVend.pesquisar();
			} catch (SQLException e1) {
				Alert a = new Alert(AlertType.ERROR, "Erro ao pesquisar no banco de dados, contate o Administrador", ButtonType.OK);
				a.showAndWait();
			}
		});
		AnchorPane.setTopAnchor(btnPesquisar, 120.0);
		AnchorPane.setRightAnchor(btnPesquisar, 260.0);
		btnPesquisar.setPrefWidth(100.0);
		
		Button btnAtualizar = new Button("Atualizar");
		btnAtualizar.setOnAction(e -> {
			try {
				ctrlVend.atualizar();
			} catch (SQLException e1) {
				Alert a = new Alert(AlertType.ERROR, "Erro ao atualizar no banco de dados, contate o Administrador", ButtonType.OK);
				a.showAndWait();
			}
		});
		
		AnchorPane.setTopAnchor(btnAtualizar, 120.0);
		AnchorPane.setRightAnchor(btnAtualizar, 140.0);
		btnAtualizar.setPrefWidth(100.0);

		// ---------------------------------------------------------------------------------

		AnchorPane.setTopAnchor(table, 160.0);
		AnchorPane.setLeftAnchor(table, 20.0);
		AnchorPane.setRightAnchor(table, 20.0);
		AnchorPane.setBottomAnchor(table, 20.0);
		
		// ---------------------------------------------------------------------------------
		
		principal.getChildren().addAll(lblProduto, lblIdVend, txtidVend, lblQntVend, txtQnt, lblDtVend, txtDt, lblCodProd, txtCodProd, lblProd,
				txtNomeProd, lblDesc, txtDescProd, lblPreco, txtPrecoUnid, lblQntProd, txtQntProd,
				btnLimpar, btnAtualizar, btnPesquisar, table);
	}
	
	@Override
	public Pane render() {
		return principal;
	}

}
