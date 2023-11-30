package trabBD.Principal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import trabBD.Produto.BoundaryProduto;
import trabBD.Vendas.BoundaryVendas;

public class Principal extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		IPrincipal vendas = new BoundaryVendas();
		vendas.start();
		
		
		IPrincipal produto = new BoundaryProduto();
		produto.start();
		
		//---------------------------------------------------------------------------------
		BorderPane principal = new BorderPane();
		principal.setStyle("-fx-background-color: '#202124'");
		
		MenuBar menuBarra = new MenuBar();
		Menu menuMain = new Menu("Modos de Trabalho");
		
		menuBarra.getMenus().addAll(menuMain);
		
		//---------------------------------------------------------------------------------
		principal.setCenter(produto.render());
		
		MenuItem menuVendas = new MenuItem("Vendas");
		menuVendas.setOnAction(e-> principal.setCenter(vendas.render()));
		
		MenuItem menuProdutos = new MenuItem("Produtos");
		menuProdutos.setOnAction(e-> principal.setCenter(produto.render()));
		
		
		//---------------------------------------------------------------------------------
		menuMain.getItems().addAll(menuVendas, menuProdutos);		
		principal.setTop(menuBarra);
		
		//---------------------------------------------------------------------------------
		Scene cena = new Scene(principal, 1000, 720);
		
		cena.widthProperty().addListener((obs, oldWidth, newWidth) -> {
			if (newWidth.intValue() < 1000) {
				stage.setWidth(1000);
			}
		});
		cena.heightProperty().addListener((obs, oldHeight, newHeight) -> {
			if (newHeight.intValue() < 640) {
				stage.setHeight(640);
			}
		});
		
		//---------------------------------------------------------------------------------
		stage.setScene(cena);
		stage.setTitle("Mercadinho");
		stage.show();
	}
	
//===================================================================================================================================
	public static void main(String[] args) {
		Application.launch(Principal.class, args);
	}

}
