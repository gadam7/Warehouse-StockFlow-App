package com.adamidis.learning.warehousestockflow.Rowmapper;

import com.adamidis.learning.warehousestockflow.Model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException { // This method is called for each row in the ResultSet. It takes the ResultSet and the row number as parameters and returns a Role object that represents the data in that row.
        return Role.builder() // Use the builder pattern to create a new Role object. This allows for more readable and maintainable code when constructing objects with multiple fields.
                .id(resultSet.getLong("id")) // Retrieve the value of the "id" column from the ResultSet and set it as the ID of the Role object. The getLong method is used to retrieve a long value from the ResultSet.
                .name(resultSet.getString("name")) // Retrieve the value of the "name" column from the ResultSet and set it as the name of the Role object. The getString method is used to retrieve a String value from the ResultSet.
                .permission(resultSet.getString("permission")) // Retrieve the value of the "permission" column from the ResultSet and set it as the permission of the Role object. The getString method is used to retrieve a String value from the ResultSet.
                .build(); // Call the build method to create the Role object with the specified fields. This method is part of the builder pattern and is used to finalize the construction of the object.
    }
}
