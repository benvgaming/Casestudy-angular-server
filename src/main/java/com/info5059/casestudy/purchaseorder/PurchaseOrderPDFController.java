package com.info5059.casestudy.purchaseorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.info5059.casestudy.product.ProductRepository;
import com.info5059.casestudy.vendor.VendorRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

//uncomment CORS for local use
@CrossOrigin
@RestController
public class PurchaseOrderPDFController {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseOrderRepository purchaseorderRepository;

    @RequestMapping(value = "/poPDF", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> streamPDF(HttpServletRequest request) throws IOException {

        String repid = request.getParameter("poid");
        ByteArrayInputStream bis = PurchaseOrderPDFGenerator.generatePurchaseOrder(repid,
        purchaseorderRepository,
        vendorRepository,
        productRepository);
        // get formatted pdf as a stream
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=purchaseorder.pdf");
        // dump stream to browser
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}