Create database Mercado;
use Mercado;
/*drop database mercado;*/
Create table produto(
	codProduto bigint primary key,
	nome varchar(30),
	descricao varchar(80),
	precoUnit numeric(10,2),
	qntProd int not null
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
	qntProd int not null
);





delimiter //
create procedure estornar(in codP bigint, in qnt int)
begin
	update produto set qntProd=qntProd+qnt where codProduto = codP;
end
//
delimiter ;


delimiter //
create trigger estornarVenda
after delete
on vendas
for each row
begin	
   call estornar(old.codProduto, old.qntProd);
end
//
delimiter ;

delete from produto;