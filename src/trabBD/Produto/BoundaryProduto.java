package trabBD.Produto;

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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import trabBD.Principal.IPrincipal;

public class BoundaryProduto implements IPrincipal {
	private TextField txtCodProd = new TextField();
	private TextField txtNomeProd = new TextField();
	private TextField txtPrecoUnid = new TextField();
	private TextField txtDescProd = new TextField();
	private TextField txtQntProd = new TextField();
	
	private CtrlProduto ctrlProd;
	private TableView<Produto> table = new TableView<>();
	
	private AnchorPane principal;

//===================================================================================================================================
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void ligacoes() { 
		Bindings.bindBidirectional(txtCodProd.textProperty(), ctrlProd.getCodProd(),(StringConverter) new LongStringConverter());
		Bindings.bindBidirectional(txtNomeProd.textProperty(), ctrlProd.getNome());
		Bindings.bindBidirectional(txtDescProd.textProperty(), ctrlProd.getDesc());
		Bindings.bindBidirectional(txtPrecoUnid.textProperty(), ctrlProd.getPreco(),(StringConverter) new DoubleStringConverter());
		Bindings.bindBidirectional(txtQntProd.textProperty(), ctrlProd.getQntProd(),(StringConverter) new IntegerStringConverter());
	}
	
	// ---------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public void abastecerTableView() { 
		TableColumn<Produto, Long> colCodProd = new TableColumn<>("Código");
		colCodProd.setCellValueFactory(new PropertyValueFactory<Produto, Long>("codProduto"));
		
		TableColumn<Produto, String> colNomeProd = new TableColumn<>("Produto");
		colNomeProd.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
		
		TableColumn<Produto, String> colDescProd = new TableColumn<>("Descrição");
		colDescProd.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao"));

		TableColumn<Produto, Double> colPrecoUnit = new TableColumn<>("Preço unidade");
		colPrecoUnit.setCellValueFactory(new PropertyValueFactory<Produto, Double>("precoUnit"));
		
		TableColumn<Produto, Integer> colQntProd = new TableColumn<>("Quantidade");
		colQntProd.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("qntProd"));
		
		//------------------------------------------------------------------------------------------------------
		
		TableColumn<Produto, Void> colAcoes = new TableColumn<>("Vender");
		Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> callBack = new Callback<>() {

			@Override
			public TableCell<Produto, Void> call(TableColumn<Produto, Void> col) {
				TableCell<Produto, Void> tCell = new TableCell<>() {
					
					//------------------------------------------------------------------------------------------------------
					final Button btnVender = new Button("Vender");
					{
						btnVender.setOnAction(e -> {
							Produto p = table.getItems().get(getIndex());
							try {
								ctrlProd.vender(p);
							} catch (SQLException | ClassNotFoundException e1) {
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
							setGraphic(btnVender);
						}
					}
				};
				return tCell;
			}
		};
		
		//------------------------------------------------------------------------------------------------------
		
		TableColumn<Produto, Void> colAcoes2 = new TableColumn<>("Excluir");
		Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> callBack2 = new Callback<>() {

				@Override
				public TableCell<Produto, Void> call(TableColumn<Produto, Void> col) {
				TableCell<Produto, Void> tCell = new TableCell<>() {
			
					//------------------------------------------------------------------------------------------------------
					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction(e -> {
							Produto p = table.getItems().get(getIndex());
							try {
								ctrlProd.excluir(p);
							} catch (SQLException | ClassNotFoundException e1) {
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
		colAcoes2.setCellFactory(callBack2);
		
		double sex = 960.0 / 6.0;
		colCodProd.setPrefWidth(sex);
		colNomeProd.setPrefWidth(sex);
		colDescProd.setPrefWidth(sex);
		colPrecoUnit.setPrefWidth(sex);
		colQntProd.setPrefWidth(sex);
		colAcoes.setPrefWidth(sex/2); 
		colAcoes2.setPrefWidth(sex/2);
		
		// ---------------------------------------------------------------------------------
		
		table.getColumns().addAll(colCodProd, colNomeProd, colDescProd, colPrecoUnit, colQntProd, colAcoes, colAcoes2);
		table.setItems(ctrlProd.getListaProd());

		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Produto>() {
			@Override
			public void onChanged(Change<? extends Produto> p) {
				if (!p.getList().isEmpty()) {
					ctrlProd.fromEntity(p.getList().get(0));
				}
			}
		});
	}
	
//===================================================================================================================================
	@Override
	public void start() {
		try {
			ctrlProd = new CtrlProduto();
		} catch(SQLException | ClassNotFoundException e) { 
			Alert a = new Alert(AlertType.ERROR, "Erro ao acessar o banco de dados, contate o Administrador", ButtonType.OK);
			a.showAndWait();
		}
		principal = new AnchorPane();
		
		// ---------------------------------------------------------------------------------
		ligacoes();
		abastecerTableView();

		// ---------------------------------------------------------------------------------
		Label lblProduto = new Label("PRODUTO");
		lblProduto.setCenterShape(true);
		lblProduto.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 20px; -fx-font-family: Arial;");

		Label lblCodProd = new Label("Codigo do Produto:");
		lblCodProd.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");

		Label lblPreco = new Label("Preco unidade:");
		lblPreco.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");

		Label lblNome = new Label("Nome:");
		lblNome.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");
		
		Label lblQntProd = new Label("Quantidade:");
		lblQntProd.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");

		Label lblDesc = new Label("Descricao:");
		lblDesc.setStyle("-fx-text-fill: '#c1c5d3'; -fx-font-size: 12px; -fx-font-family: Arial;");

		// ---------------------------------------------------------------------------------
		AnchorPane.setTopAnchor(lblProduto, 15.0);
		AnchorPane.setLeftAnchor(lblProduto, 20.0);

		AnchorPane.setTopAnchor(lblCodProd, 50.0);
		AnchorPane.setLeftAnchor(lblCodProd, 20.0);

		AnchorPane.setTopAnchor(lblPreco, 50.0);
		AnchorPane.setLeftAnchor(lblPreco, 320.0);

		AnchorPane.setTopAnchor(lblNome, 50.0);
		AnchorPane.setLeftAnchor(lblNome, 510.0);

		AnchorPane.setTopAnchor(lblQntProd, 85.0);
		AnchorPane.setLeftAnchor(lblQntProd, 20.0);
		
		AnchorPane.setTopAnchor(lblDesc, 85.0);
		AnchorPane.setLeftAnchor(lblDesc, 150.0);

		// ---------------------------------------------------------------------------------
		AnchorPane.setTopAnchor(txtCodProd, 45.0);
		AnchorPane.setLeftAnchor(txtCodProd, 130.0);
		txtCodProd.setPrefWidth(185.0);

		AnchorPane.setTopAnchor(txtPrecoUnid, 45.0);
		AnchorPane.setLeftAnchor(txtPrecoUnid, 405.0);
		txtPrecoUnid.setPrefWidth(100.0);

		AnchorPane.setTopAnchor(txtNomeProd, 45.0);
		AnchorPane.setLeftAnchor(txtNomeProd, 550.0);
		AnchorPane.setRightAnchor(txtNomeProd, 20.0);
		
		AnchorPane.setTopAnchor(txtQntProd, 80.0);
		AnchorPane.setLeftAnchor(txtQntProd, 90.0);
		txtQntProd.setPrefWidth(50.0);

		AnchorPane.setTopAnchor(txtDescProd, 80.0);
		AnchorPane.setLeftAnchor(txtDescProd, 210.0);
		AnchorPane.setRightAnchor(txtDescProd, 20.0);

		// ---------------------------------------------------------------------------------

		Button btnLimpar = new Button("Limpar");
		btnLimpar.setOnAction(e -> ctrlProd.limpar());
		
		Button btnAlterar = new Button("Alterar");
		btnAlterar.setOnAction(e -> {
			try {
				ctrlProd.alterar();
			} catch (SQLException e1) {
				Alert a = new Alert(AlertType.ERROR, "Erro ao gravar, contate o Administrador", ButtonType.OK);
				a.showAndWait();
			}
		});

		Button btnInserir = new Button("Inserir");
		btnInserir.setOnAction(e -> {
			try {
				ctrlProd.adicionar();
			} catch (SQLException e1) {
				Alert a = new Alert(AlertType.ERROR, "Erro ao gravar, contate o Administrador", ButtonType.OK);
				a.showAndWait();
			}
		});

		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> {
			try {
				ctrlProd.pesquisar();
			} catch (SQLException e1) {
				Alert a = new Alert(AlertType.ERROR, "Erro ao pesquisar no banco de dados, contate o Administrador", ButtonType.OK);
				a.showAndWait();
			}
		});

		AnchorPane.setTopAnchor(btnPesquisar, 120.0);
		AnchorPane.setRightAnchor(btnPesquisar, 380.0);
		btnPesquisar.setPrefWidth(100.0);

		AnchorPane.setTopAnchor(btnInserir, 120.0);
		AnchorPane.setRightAnchor(btnInserir, 260.0);
		btnInserir.setPrefWidth(100.0);

		AnchorPane.setTopAnchor(btnAlterar, 120.0);
		AnchorPane.setRightAnchor(btnAlterar, 140.0);
		btnAlterar.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(btnLimpar, 120.0);
		AnchorPane.setRightAnchor(btnLimpar, 20.0);
		btnLimpar.setPrefWidth(100.0);

		// ---------------------------------------------------------------------------------

		AnchorPane.setTopAnchor(table, 160.0);
		AnchorPane.setLeftAnchor(table, 20.0);
		AnchorPane.setRightAnchor(table, 20.0);
		AnchorPane.setBottomAnchor(table, 20.0);
		
		// ---------------------------------------------------------------------------------

		principal.getChildren().addAll(lblProduto, lblCodProd, lblPreco, lblNome, lblQntProd, lblDesc,
										txtCodProd, txtNomeProd, txtPrecoUnid, txtQntProd, txtDescProd, 
										btnLimpar, btnInserir, btnPesquisar, btnAlterar, table);
	}
	
	@Override
	public Pane render() {
		return principal;
	}

}
