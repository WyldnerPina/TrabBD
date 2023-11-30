package trabBD.Vendas;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trabBD.Produto.CtrlProduto;
import trabBD.Produto.Produto;

public class CtrlVendas {
	private LongProperty idVendas = new SimpleLongProperty(0);
	private IntegerProperty qnt = new SimpleIntegerProperty(0);
	private ObjectProperty<Date> dt = new SimpleObjectProperty<>();
	private DoubleProperty total = new SimpleDoubleProperty(0);

	private LongProperty codProd = new SimpleLongProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private DoubleProperty preco = new SimpleDoubleProperty(0);
	private StringProperty desc = new SimpleStringProperty("");
	private IntegerProperty qntProd = new SimpleIntegerProperty();

	private ObservableList<Vendas> listaVend = FXCollections.observableArrayList();
	private IVendasDAO vDao;

//===================================================================================================================================
	public CtrlVendas() throws ClassNotFoundException, SQLException {
		vDao = new VendasDAO();
	}

	// ---------------------------------------------------------------------------------

	public void limpar() {
		idVendas.set(0);
		qnt.set(0);
		dt.set(null);
		total.set(0);

		codProd.set(0);
		nome.set(null);
		preco.set(0.0);
		desc.set(null);
		qntProd.set(0);

		listaVend.clear();
	}

	// ---------------------------------------------------------------------------------

	public void excluir(Vendas v) throws SQLException {
		/*
		Long cod = v.getCodProd();
		int qnt = v.getQntProd();
		
		CtrlProduto cp = new CtrlProduto(cod, qnt);
		*/
		vDao.remover(v.getIdVendas());
		listaVend.remove(v);
	}

	public void fromEntity(Vendas v) {
		idVendas.set(v.getIdVendas());
		qnt.set(v.getQntidade());
		dt.set(v.getSqlDate());
		total.set(v.getTotal());

		codProd.set(v.getCodProd());
		nome.set(v.getNome());
		preco.set(v.getPrecoUnit());
		desc.set(v.getDesc());
		qntProd.set(v.getQntProd());
	}

	// ---------------------------------------------------------------------------------

	public void adicionar(Produto p, int qnt) throws SQLException {
		Vendas v = new Vendas(p);
		v.setQntidade(qnt);
		v.getSqlDate();
		v.setTotal(p.getPrecoUnit() * qnt);

		System.out.println(v);

		vDao.adicionar(v);

	}

	// ---------------------------------------------------------------------------------

	public void pesquisar() throws SQLException {
		if (idVendas.get() == 0) {
			listaVend.clear();
			List<Vendas> lst = vDao.procurarTodos();
			listaVend.addAll(lst);
		} else {
			Vendas v = new Vendas();
			v = vDao.procurarCodProd(idVendas.get());
			listaVend.clear();
			listaVend.add(v);
		}
	}

	// ---------------------------------------------------------------------------------

	public void atualizar() throws SQLException {
		if (idVendas.get() == 0) {
			JOptionPane.showMessageDialog(null, "Selecione uma venda para atualizar");
		} else {
			Vendas v = new Vendas();
			v.setIdVendas(idVendas.get());
			v.setQntidade(qnt.get());
			v.setSqlDate(dt.get());
			v.setTotal(total.get());

			v.setCodProd(codProd.get());
			v.setNome(nome.get());
			v.setDesc(desc.get());
			v.setPrecoUnit(preco.get());
			v.setQntProd(qntProd.get());

			vDao.atualizar(v.getIdVendas(), v);
			listaVend.add(v);
			limpar();
			pesquisar();
		}
	}

//===================================================================================================================================
	public final LongProperty getIdVend() {
		return idVendas;
	}

	public final void setIdVend(LongProperty idVend) {
		this.idVendas = idVend;
	}

	public final IntegerProperty getQnt() {
		return qnt;
	}

	public final void setQnt(IntegerProperty qnt) {
		this.qnt = qnt;
	}

	public final ObjectProperty<Date> getDt() {
		return dt;
	}

	public final void setDt(ObjectProperty<Date> dt) {
		this.dt = dt;
	}

	public final DoubleProperty getTotal() {
		return total;
	}

	public final void setTotal(DoubleProperty total) {
		this.total = total;
	}

	public final LongProperty getCodProd() {
		return codProd;
	}

	public final void setCodProd(LongProperty codProd) {
		this.codProd = codProd;
	}

	public final StringProperty getNome() {
		return nome;
	}

	public final void setNome(StringProperty nome) {
		this.nome = nome;
	}

	public final DoubleProperty getPreco() {
		return preco;
	}

	public final void setPreco(DoubleProperty preco) {
		this.preco = preco;
	}

	public final StringProperty getDesc() {
		return desc;
	}

	public final void setDesc(StringProperty desc) {
		this.desc = desc;
	}

	public final IntegerProperty getQntProd() {
		return qntProd;
	}

	public final void setQntProd(IntegerProperty qntProd) {
		this.qntProd = qntProd;
	}

	public final ObservableList<Vendas> getListaProd() {
		return listaVend;
	}

	public final void setListaProd(ObservableList<Vendas> listaProd) {
		this.listaVend = listaProd;
	}

	public final IVendasDAO getvDao() {
		return vDao;
	}

	public final void setvDao(IVendasDAO vDao) {
		this.vDao = vDao;
	}
}
