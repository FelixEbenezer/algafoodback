alter table pedido add entrega_agendada tinyint(1) after codigo;
update pedido set entrega_agendada = false;
alter table pedido add entrega_agendada_para datetime after entrega_agendada;
alter table pedido drop entregaAgendada;
alter table pedido drop dataEntrega;