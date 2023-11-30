package trabBD.Principal;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import trabBD.Produto.Produto;
import trabBD.Vendas.Vendas;

public class VendProd {
	private long idVendas;
	private double total;
	
	private long codProduto;
	private String nomeProd;
	private String descricao;
	private double precoUnit;
	private int qntProd;
	
	private LocalTime hVenda = LocalTime.now();
	private Time sqlTime = Time.valueOf(hVenda);
	
	private LocalDate dtVenda = LocalDate.now();
	private Date sqlDate = Date.valueOf(dtVenda);
	
	public VendProd(Vendas v, Produto p) {
    	this.idVendas = v.getIdVendas();
    	this.total = v.getTotal();
    	
    	this.codProduto = p.getCodProduto();
    	this.nomeProd = p.getNome();
    	this.descricao = p.getDescricao();
    	this.precoUnit = p.getPrecoUnit();
    	this.qntProd = p.getQntProd();
    }
	
	public VendProd(Vendas v) {
    	super();
    }
    
	public final long getIdVendas() {
		return idVendas;
	}
	public final void setIdVendas(long idVendas) {
		this.idVendas = idVendas;
	}
	public final double getTotal() {
		return total;
	}
	public final void setTotal(double total) {
		this.total = total;
	}

	public final long getCodProduto() {
		return codProduto;
	}

	public final void setCodProduto(long codProduto) {
		this.codProduto = codProduto;
	}

	public final String getNomeProd() {
		return nomeProd;
	}

	public final void setNomeProd(String nome) {
		this.nomeProd = nome;
	}

	public final String getDescricao() {
		return descricao;
	}

	public final void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public final Time getSqlTime() {
		return sqlTime;
	}

	public final Date getSqlDate() {
		return sqlDate;
	}
	
	
}
