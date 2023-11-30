
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


SELECT *
FROM VendProd vp
INNER JOIN Vendas v ON vp.idVendas = v.idVendas
INNER JOIN Produto p ON vp.codProduto = p.codProduto
WHERE vp.idVendas = <id_da_venda_desejada>;



SELECT vp.qntidade, vp.dtVenda, vp.hVenda,
       vp.idVendas, vp.codProd,
       v.total,
       p.nome, p.descricao, p.precoUnit, p.qntProd
FROM vendProd vp
INNER JOIN venda v ON vp.idVendas = v.idVendas
INNER JOIN produto p ON vp.codProd = p.codProd;



SELECT *
FROM VendProd vp
INNER JOIN Vendas v ON vp.idVendas = v.idVendas
WHERE vp.idVendas = 23;

