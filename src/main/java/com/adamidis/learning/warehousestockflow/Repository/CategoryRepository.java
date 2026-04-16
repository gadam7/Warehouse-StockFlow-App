package com.adamidis.learning.warehousestockflow.Repository;

import com.adamidis.learning.warehousestockflow.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>, ListCrudRepository<Category, Long> {
    Page<Category> findByNameContaining(String name, Pageable pageable);

    Optional<Category> findByName(String name);
}
