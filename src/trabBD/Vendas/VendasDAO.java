package trabBD.Vendas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trabBD.Principal.AcessoBD;

public class VendasDAO implements IVendasDAO{
	private Connection con;
	
//===================================================================================================================================	
	public VendasDAO() throws ClassNotFoundException, SQLException {
		Class.forName(AcessoBD.getJdbcDriver());
		con = DriverManager.getConnection(AcessoBD.getJdbcUrl(), AcessoBD.getJdbcUser(), AcessoBD.getJdbcPass());
	}

	//---------------------------------------------------------------------------------
	
	@Override
	public Vendas adicionar(Vendas v) throws SQLException {
		String sql = "INSERT INTO vendas (qntidade, dtVenda, codProduto, total, nome, descricao, precoUnit, qntProd)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setDouble(1, v.getQntidade());
		st.setDate(2, v.getSqlDate());
		st.setLong(3, v.getCodProd());
		st.setDouble(4, v.getTotal());
		st.setString(5, v.getNome());
		st.setString(6, v.getDesc());
		st.setDouble(7, v.getPrecoUnit());
		st.setInt(8, v.getQntProd());
		

		st.executeUpdate();

		ResultSet rs = st.getGeneratedKeys();
		if (rs.next()) {
			long id = rs.getLong(1);
			v.setIdVendas(id);
		}
		return v;
	}

	//---------------------------------------------------------------------------------
	
	@Override
	public void atualizar(long id, Vendas v) throws SQLException {
		String sql = "UPDATE vendas SET qntidade = ?, dtVenda = ?, codProduto = ?, total = ?, nome = ?,"
				+ "descricao = ?, precoUnit = ?, qntProd = ? WHERE idVendas = ?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, v.getQntidade());
		st.setDate(2, v.getSqlDate());
		st.setLong(3, v.getCodProd());
		st.setDouble(4, v.getTotal());
		st.setString(5, v.getNome());
		st.setString(6, v.getDesc());
		st.setDouble(7, v.getPrecoUnit());
		st.setInt(8, v.getQntProd());
		st.setLong(9, id);

		st.executeUpdate();
	}

	//---------------------------------------------------------------------------------
	
	@Override
	public void remover(long id) throws SQLException {
		String sql = "DELETE FROM vendas WHERE idVendas = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		st.executeUpdate();
	}

	//---------------------------------------------------------------------------------
	
	@Override
	public Vendas procurarCodProd(long id) throws SQLException {
		String sql = "SELECT * FROM vendas WHERE idVendas = ? ";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			Vendas v = new Vendas();
			v.setIdVendas(rs.getLong("idVendas"));
			v.setQntidade(rs.getInt("qntidade"));
			v.setSqlDate(rs.getDate("dtVenda"));
			
			v.setCodProd(rs.getLong("codProduto"));
			v.setTotal(rs.getDouble("total"));
			v.setNome(rs.getString("nome"));
			v.setDesc(rs.getString("descricao"));
			v.setPrecoUnit(rs.getDouble("precoUnit"));
			v.setQntProd(rs.getInt("qntProd"));

			return v;
		}
		return null;
	}

	//---------------------------------------------------------------------------------
		
	@Override
	public List<Vendas> procurarTodos() throws SQLException {
		List<Vendas> lista = new ArrayList<>();

		String sql = "SELECT * FROM vendas";
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Vendas v = new Vendas();
			v.setIdVendas(rs.getLong("idVendas"));
			v.setQntidade(rs.getInt("qntidade"));
			v.setSqlDate(rs.getDate("dtVenda"));
			
			v.setCodProd(rs.getLong("codProduto"));
			v.setTotal(rs.getDouble("total"));
			v.setNome(rs.getString("nome"));
			v.setDesc(rs.getString("descricao"));
			v.setPrecoUnit(rs.getDouble("precoUnit"));
			v.setQntProd(rs.getInt("qntProd"));
			
			lista.add(v);
		}
		return lista;
	}
}
