package trabBD.Vendas;

import java.sql.SQLException;
import java.util.List;

public interface IVendasDAO {
	Vendas adicionar(Vendas P) throws SQLException;

	void atualizar(long id, Vendas P) throws SQLException;

	void remover(long id) throws SQLException;

//	List<Vendas> procurarNome(String titulo) throws SQLException;

	Vendas procurarCodProd(long id) throws SQLException;
	
	public List<Vendas> procurarTodos() throws SQLException;
}
