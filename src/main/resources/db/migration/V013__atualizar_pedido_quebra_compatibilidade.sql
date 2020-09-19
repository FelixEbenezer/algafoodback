alter table pedido add entregaAgendada tinyint(1) after codigo;  
update pedido set entregaAgendada = false;   
alter table pedido add dataEntrega datetime after entregaAgendada;  
update pedido set dataEntrega = null;   
 