package com.info5059.casestudy.purchaseorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//uncomment CORS for local use
@CrossOrigin
@RestController
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderDAO purchaseorderDAO;
    @Autowired
    private PurchaseOrderRepository poRepository;

    PurchaseOrderController(PurchaseOrderRepository repo)
    {
        poRepository = repo;
    }

    @PostMapping("/api/pos")
    public ResponseEntity<PurchaseOrder> addOne(@RequestBody PurchaseOrder clientrep) { // use RequestBody here
        return new ResponseEntity<PurchaseOrder>(purchaseorderDAO.create(clientrep), HttpStatus.OK);
    }

    @GetMapping("/api/pos")
    public ResponseEntity<Iterable<PurchaseOrder>> findAll() {
        Iterable<PurchaseOrder> pos = poRepository.findAll();
        return new ResponseEntity<Iterable<PurchaseOrder>>(pos, HttpStatus.OK);
    }
}