package trabBD.Produto;

import java.sql.SQLException;
import java.util.List;


public interface IProdDAO {
	Produto adicionar(Produto P) throws SQLException;

	void atualizar(long id, Produto P) throws SQLException;

	void remover(long id) throws SQLException;

	List<Produto> procurarNome(String titulo) throws SQLException;

	Produto procurarCodProd(long id) throws SQLException;
}
