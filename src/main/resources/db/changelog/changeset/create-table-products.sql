-- liquibase formatted sql

-- changeset tyutyakov:create-table-products
create table if not exists orders_schema.products
(
    position_number             integer     not null    check (position_number >= 0),
    product_serial_number       varchar     not null,
    product_name                varchar     not null,
    quantity                    integer     not null    check (quantity >= 0),
    order_id                    integer     references orders_schema.orders(order_id),
    constraint ProductsId primary key (order_id, position_number)
)