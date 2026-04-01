package com.adamidis.learning.warehousestockflow.Query;

public class CategoryQuery {
    public static final String STATS_QUERY = "SELECT c.total_categories, p.total_products, cost.total_cost FROM (SELECT COUNT(*) total_categories FROM category) c, (SELECT COUNT(*) total_products FROM product) p, (SELECT ROUND(SUM(unit_price * quantity_stock), 2) AS total_cost FROM product) cost";
}
