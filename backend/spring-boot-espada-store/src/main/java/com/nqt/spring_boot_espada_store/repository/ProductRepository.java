package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.Product;
import com.nqt.spring_boot_espada_store.entity.Subtype;
import com.nqt.spring_boot_espada_store.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM product p JOIN p.subtype st WHERE st.type = :type")
    Set<Product> findAllByType(@Param("type") Type type);

    Set<Product> findAllBySubtype(Subtype subtype);

    Page<Product> findAllBySubtype(Subtype subtype, Pageable pageable);

    Page<Product> findProductsByNameContaining(String search, Pageable pageable);
}
