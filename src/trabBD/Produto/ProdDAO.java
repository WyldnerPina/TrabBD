package trabBD.Produto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trabBD.Principal.AcessoBD;

public class ProdDAO implements IProdDAO{
	private Connection con;
	
//===================================================================================================================================	
	public ProdDAO() throws ClassNotFoundException, SQLException {
		Class.forName(AcessoBD.getJdbcDriver());
		con = DriverManager.getConnection(AcessoBD.getJdbcUrl(), AcessoBD.getJdbcUser(), AcessoBD.getJdbcPass());
	}

	//---------------------------------------------------------------------------------
	
	@Override
	public Produto adicionar(Produto p) throws SQLException {
		String sql = "INSERT INTO produto (codProduto, nome, descricao, precoUnit, qntProd) VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setLong(1, p.getCodProduto());
		st.setString(2, p.getNome());
		st.setString(3, p.getDescricao());
		st.setDouble(4, p.getPrecoUnit());
		st.setInt(5, p.getQntProd());
			
		st.executeUpdate();
		
		ResultSet rs = st.getGeneratedKeys();
		if (rs.next()) { 
			long id = rs.getLong(1);
			p.setCodProduto(id);
		}
		return p;
	}
	
	//---------------------------------------------------------------------------------

	@Override
	public void atualizar(long id, Produto p) throws SQLException {
		String sql = "UPDATE produto SET nome = ?, descricao = ?, precoUnit = ?, qntProd = ? WHERE codProduto = ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, p.getNome());
		st.setString(2, p.getDescricao());
		st.setDouble(3, p.getPrecoUnit());
		st.setInt(4, p.getQntProd());
		st.setLong(5, id);
			
		st.executeUpdate();		
	}

	//---------------------------------------------------------------------------------
	
	@Override
	public void remover(long id) throws SQLException {
		String sql = "DELETE FROM produto WHERE codProduto = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		st.executeUpdate();
	}
	
	//---------------------------------------------------------------------------------

	@Override
	public List<Produto> procurarNome(String nome) throws SQLException {
		List<Produto> lista = new ArrayList<>();
		
		String sql = "SELECT * FROM produto WHERE nome LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + nome + "%");
		ResultSet rs = st.executeQuery();
		
		
		while(rs.next()) { 
			Produto p = new Produto();
			p.setCodProduto(rs.getLong("codProduto"));
			p.setNome(rs.getString("nome"));
			p.setDescricao(rs.getString("descricao"));
			p.setPrecoUnit(rs.getDouble("precoUnit"));
			p.setQntProd(rs.getInt("qntProd"));
			
			lista.add(p);
		}
		return lista;
	}
	
	//---------------------------------------------------------------------------------

	@Override
	public Produto procurarCodProd(long id) throws SQLException {
		String sql = "SELECT * FROM produto WHERE codProduto = ? ";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		
		
		if ( rs.next() ) { 
			Produto p = new Produto();
			p.setCodProduto(rs.getLong("codProduto"));
			p.setNome(rs.getString("nome"));
			p.setDescricao(rs.getString("descricao"));
			p.setPrecoUnit(rs.getDouble("precoUnit"));
			p.setQntProd(rs.getInt("qntProd"));
			
			return p;
		}
		return null;
	}

}
