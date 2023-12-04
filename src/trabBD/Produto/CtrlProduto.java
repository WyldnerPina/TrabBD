package trabBD.Produto;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trabBD.Vendas.CtrlVendas;

public class CtrlProduto {
	private LongProperty codProd = new SimpleLongProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty desc = new SimpleStringProperty("");
	private DoubleProperty precoUnit = new SimpleDoubleProperty(0);
	private IntegerProperty qntProd = new SimpleIntegerProperty(0);
	
	private ObservableList<Produto> listaProd = FXCollections.observableArrayList();
	private IProdDAO pDao;
	
//===================================================================================================================================
	public CtrlProduto() throws ClassNotFoundException, SQLException {
		pDao = new ProdDAO();
	}
	
		
	//---------------------------------------------------------------------------------
	
	public void limpar() {
		System.out.println("limpando");
		codProd.set(0);
		nome.set("");
		desc.set("");
		precoUnit.set(0);
		qntProd.set(0);
		
		listaProd.clear();
	}
	
	public void fromEntity(Produto p) { 
		codProd.set(p.getCodProduto());
		nome.set(p.getNome());
		desc.set(p.getDescricao());
		precoUnit.set(p.getPrecoUnit());
		qntProd.set(p.getQntProd());
	}
	
	//---------------------------------------------------------------------------------

	public void vender(Produto p) throws SQLException, ClassNotFoundException { 
		CtrlVendas cv = new CtrlVendas();
		int qnt = Integer.parseInt(JOptionPane.showInputDialog("qntos deseja vender?"));
		
		if(p.getQntProd() >= qnt && qnt > 0) {
				p.setQntProd(p.getQntProd() - qnt);
				cv.adicionar(p, qnt);
				pDao.atualizar(p.getCodProduto(), p);
				limpar();
				pesquisar();
		}else {
			JOptionPane.showMessageDialog(null, "Quantidade inválida!!");
		}
		
	}
	
	//---------------------------------------------------------------------------------
	
	public void excluir(Produto p) throws SQLException, ClassNotFoundException { 
			pDao.remover(p.getCodProduto());limpar();
			listaProd.remove(p);
			pesquisar();
	}
	
	//---------------------------------------------------------------------------------
	
	public void adicionar() throws SQLException { 
		if (codProd.get() <= 0 || nome.get().equals("") || precoUnit.get() == 0 || qntProd.get() <= 0) {
			JOptionPane.showMessageDialog(null, "Por favor, descreva ao menos:\n"
											+ " - Código do produto\n"
											+ " - Nome do produto\n"
											+ " - Preço\n"
											+ " - Quantidade");
			
		} else { 
			Produto p = new Produto();
			p.setCodProduto(codProd.get());
			p.setNome(nome.get());
			p.setDescricao(desc.get());
			p.setPrecoUnit(precoUnit.get());
			p.setQntProd(qntProd.get());
			
			for (int i = 0; i < listaProd.size(); i++) { 
				Produto prod = listaProd.get(i);
				if (prod.getCodProduto() == codProd.get()) { 
					listaProd.set(i, p);
				}
			}
			pDao.adicionar(p);
			limpar();
			pesquisar();
		}
	}
	
	//---------------------------------------------------------------------------------
	
	public void pesquisar() throws SQLException { 
		listaProd.clear();
		List<Produto> lst = pDao.procurarNome(nome.get());
		listaProd.addAll(lst);		
	}
	
	//---------------------------------------------------------------------------------
	
	public void alterar() throws SQLException { 
		if(codProd.get() == 0) {
			JOptionPane.showMessageDialog(null, "Selecione um produto para atualizar");
		}else {
			Produto p = new Produto();
			p.setCodProduto(codProd.get());
			p.setNome(nome.get());
			p.setDescricao(desc.get());
			p.setPrecoUnit(precoUnit.get());
			p.setQntProd(qntProd.get());
			
			pDao.atualizar(codProd.get(), p);
			limpar();
			pesquisar();
		}	
	}
	
//===================================================================================================================================
	public final ObservableList<Produto> getListaProd() {
		return listaProd;
	}
	public final LongProperty getCodProd() {
		return codProd;
	}
	public final StringProperty getNome() {
		return nome;
	}
	public final StringProperty getDesc() {
		return desc;
	}
	public final DoubleProperty getPreco() {
		return precoUnit;
	}
	public final IntegerProperty getQntProd() {
		return qntProd;
	}
}
