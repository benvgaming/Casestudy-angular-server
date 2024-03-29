package com.info5059.casestudy.product;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
//uncomment CORS for local use
@Repository
@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends JpaRepository<Product, String> {
    // extend so we can return the number of rows deleted
    @Modifying
    @Transactional
    @Query("delete from Product where id = ?1")
    int deleteOne(String productid); 

    //List<Product> findByVendorId(Integer vendorid);
    
}

