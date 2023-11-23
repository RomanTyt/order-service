-- liquibase formatted sql

-- changeset tyutyakov:create-table-orders
create table if not exists orders_schema.orders
(
    order_id                integer         primary key   auto_increment,
    customer_name           varchar         not null,
    customer_address        varchar         not null,
    total_order_amount      integer         not null    check (total_order_amount >= 0),
    date_of_creation        datetime        not null
)
