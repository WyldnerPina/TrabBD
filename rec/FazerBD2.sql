Create database Mercado;
use Mercado;

Create table produto(
	codProduto bigint primary key,
	nome varchar(30),
	descricao varchar(80),
	precoUnit numeric(10,2),
	qntProd int
);


Create table vendas(
	idVendas bigint auto_increment primary key,
	qntidade int,
	dtVenda Date,
	codProduto bigint,
	total numeric(10,2),
	nome varchar(30),
	descricao varchar(80),
	precoUnit numeric(10,2),
	qntProd int
);

/* O comando TRUNCATE é mais rápido que o DELETE para excluir todos os registros, 
mas ele não gera logs de exclusão e não aciona gatilhos.*/
truncate vendas;
truncate produto;
select * from vendas;
select * from produto;



