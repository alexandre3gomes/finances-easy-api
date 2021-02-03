insert into budget (id, app_user, start_date, end_date, breakperiod) values (1, 1, '2020-01-01 00:00:00.000000', '2020-12-30 00:00:00.000000', 1);
insert into budget (id, app_user, start_date, end_date, breakperiod) values (2, 1, '2021-01-01 00:00:00.000000', '2021-01-30 00:00:00.000000', 2);
insert into budget_categories (category_id, budget_id, value) values (1, 1, 100);
insert into budget_categories (category_id, budget_id, value) values (2, 1, 200);
insert into budget_categories (category_id, budget_id, value) values (1, 2, 100);
insert into budget_categories (category_id, budget_id, value) values (2, 2, 200);
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 0, '2020-01-01 00:00:00', '2020-01-31 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 1, '2020-02-01 00:00:00', '2020-02-29 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 2, '2020-03-01 00:00:00', '2020-03-31 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 3, '2020-04-01 00:00:00', '2020-04-30 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 4, '2020-05-01 00:00:00', '2020-05-31 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 5, '2020-06-01 00:00:00', '2020-06-30 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 6, '2020-07-01 00:00:00', '2020-07-31 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 7, '2020-08-01 00:00:00', '2020-08-31 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 8, '2020-09-01 00:00:00', '2020-09-30 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 9, '2020-10-01 00:00:00', '2020-10-31 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 10, '2020-11-01 00:00:00', '2020-11-30 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (1, 11, '2020-12-01 00:00:00', '2020-12-31 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (2, 0, '2021-01-01 00:00:00', '2021-01-07 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (2, 1, '2021-01-08 00:00:00', '2021-01-15 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (2, 2, '2021-01-16 00:00:00', '2021-01-22 23:59:59');
insert into budget_periods (budget_id, id_period, start_date, end_date) values (2, 3, '2021-01-23 00:00:00', '2021-01-30 23:59:59');