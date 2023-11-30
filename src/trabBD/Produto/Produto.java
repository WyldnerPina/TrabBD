package trabBD.Produto;

public class Produto {
	private long codProduto;
	private String nome;
	private String descricao;
	private double precoUnit;
	private int qntProd;
	
//===================================================================================================================================	
	public final long getCodProduto() {
		return codProduto;
	}
	public final void setCodProduto(long codProduto) {
		this.codProduto = codProduto;
	}
	
	//---------------------------------------------------------------------------------
	
	public final String getNome() {
		return nome;
	}
	public final void setNome(String nome) {
		this.nome = nome;
	}
	
	//---------------------------------------------------------------------------------
	
	public final String getDescricao() {
		return descricao;
	}
	public final void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	//---------------------------------------------------------------------------------
	
	public final double getPrecoUnit() {
		return precoUnit;
	}
	public final void setPrecoUnit(double precoUnit) {
		this.precoUnit = precoUnit;
	}
	
	
	//---------------------------------------------------------------------------------
	
	public final int getQntProd() {
		return qntProd;
	}
	public final void setQntProd(int qntProd) {
		this.qntProd = qntProd;
	}
	
	//---------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Produto [codProduto=" + codProduto + ", nome=" + nome + ", descricao=" + descricao + ", precoUnit="
				+ precoUnit + "]";
	}
}
