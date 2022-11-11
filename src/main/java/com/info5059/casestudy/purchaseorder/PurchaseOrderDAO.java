package com.info5059.casestudy.purchaseorder;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.info5059.casestudy.product.Product;
import com.info5059.casestudy.product.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

@Component
public class PurchaseOrderDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private ProductRepository repo;

    public PurchaseOrderDAO(ProductRepository repo)
    {
        this.repo = repo;
    }

    @Transactional
    public PurchaseOrder create(PurchaseOrder clientorder) {
        PurchaseOrder realPurchaseOrder = new PurchaseOrder();

        // we also need to update the QOO on the product table
        
        realPurchaseOrder.setVendorid(clientorder.getVendorid());
        realPurchaseOrder.setPodate(LocalDateTime.now());
        realPurchaseOrder.setAmount(clientorder.getAmount());
        entityManager.persist(realPurchaseOrder);
        for (PurchaseOrderLineitem item : clientorder.getItems()) {
            
            Product prod = repo.getReferenceById(item.getProductid());
            prod.setQoo(prod.getQoo() + item.getQty());
            repo.saveAndFlush(prod);
            PurchaseOrderLineitem realItem = new PurchaseOrderLineitem();
            realItem.setPoid(realPurchaseOrder.getId());
            realItem.setProductid(item.getProductid());
            realItem.setPrice(item.getPrice());
            realItem.setQty(item.getQty());

            entityManager.persist(realItem);
        }

        entityManager.refresh(realPurchaseOrder);
        return realPurchaseOrder;
    }
}
