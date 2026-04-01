package com.adamidis.learning.warehousestockflow.Rowmapper;

import com.adamidis.learning.warehousestockflow.Model.Statistics;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsRowMapper implements RowMapper<Statistics> {

    @Override
    public Statistics mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Statistics.builder()
                .totalCategories(resultSet.getInt("total_categories"))
                .totalProducts(resultSet.getInt("total_products"))
                .totalCost(resultSet.getDouble("total_cost"))
                .build();
    }
}
