package trabBD.Vendas;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import trabBD.Produto.Produto;

public class Vendas {
	private long idVendas;
	private int qntidade;
	private double total;
	
	private LocalTime hVenda = LocalTime.now();
	private Time sqlTime = Time.valueOf(hVenda);
	
	private LocalDate dtVenda = LocalDate.now();
	private Date sqlDate = Date.valueOf(dtVenda);
	
	private long codProd;
	private String nome;
	private String desc;
	private double precoUnit;
	private int qntProd;
	
	public Vendas(Produto p) {
		super();
		this.codProd = p.getCodProduto();
		this.nome = p.getNome();
		this.desc = p.getDescricao();
		this.precoUnit = p.getPrecoUnit();
		this.qntProd = p.getQntProd();
	}
	
	public Vendas() {
		super();
	}
	
	
	
//===================================================================================================================================	

	public final long getIdVendas() {
		return idVendas;
	}
	public final void setIdVendas(long idVendas) {
		this.idVendas = idVendas;
	}
	
	//---------------------------------------------------------------------------------
	
	public final int getQntidade() {
		return qntidade;
	}
	public final void setQntidade(int qntidade) {
		this.qntidade = qntidade;
	}

	//---------------------------------------------------------------------------------
	
	public final double getTotal() {
		return total;
	}
	public final void setTotal(double total) {
		this.total = total;
	}

	//---------------------------------------------------------------------------------
	
	public final Time getSqlTime() {
		return sqlTime;
	}
	public final void setSqlTime(Time sqlTime) {
		this.sqlTime = sqlTime;
	}
	public final Date getSqlDate() {
		return sqlDate;
	}
	public final void setSqlDate(Date sqlDate) {
		this.sqlDate = sqlDate;
	}
	
	//---------------------------------------------------------------------------------
	
	public final long getCodProd() {
		return codProd;
	}
	public final void setCodProd(long codProd) {
		this.codProd = codProd;
	}

	public final String getNome() {
		return nome;
	}
	public final void setNome(String nome) {
		this.nome = nome;
	}

	public final String getDesc() {
		return desc;
	}
	public final void setDesc(String desc) {
		this.desc = desc;
	}


	public final double getPrecoUnit() {
		return precoUnit;
	}
	public final void setPrecoUnit(double precoUnit) {
		this.precoUnit = precoUnit;
	}


	public final int getQntProd() {
		return qntProd;
	}
	public final void setQntProd(int qntProd) {
		this.qntProd = qntProd;
	}
	
	//---------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "Vendas [idVendas=" + idVendas + ", qntidade=" + qntidade + ", total=" + total + ", dtVenda=" + dtVenda + "]";
	}


}
