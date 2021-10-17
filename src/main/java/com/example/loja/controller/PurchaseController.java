package com.example.loja.controller;

import com.example.loja.controller.request.PurchaseCreationRequest;
import com.example.loja.controller.response.PurchaseReturnResponse;
import com.example.loja.model.Purchase;
import com.example.loja.service.PurchaseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class PurchaseController {

    private final PurchaseService purchaseServ;

    public PurchaseController(PurchaseService purchaseServ) {
        this.purchaseServ = purchaseServ;
    }

    //Get All Purchases
    @GetMapping("/purchases")
    public List<PurchaseReturnResponse> getPurchases() {
        List<Purchase> purchases = purchaseServ.findAll();
        List<PurchaseReturnResponse> purchasesResp = new ArrayList<>();
        //Transforms the returned List<Purchase> from purchaseServ.findAll() into a List<PurchaseReturnResponse> and returns it
        if (!purchases.isEmpty()) {
            for (Purchase purchase : purchases) {
                purchasesResp.add(new PurchaseReturnResponse()
                        .createResponseFromPurchase(purchase));
            }
            return purchasesResp;
        }
        return Collections.emptyList();
    }

    //Get all purchases
    @GetMapping("/purchases/{id}")
    public PurchaseReturnResponse getPurchaseById(@PathVariable(value = "id") Long id) {
        Purchase purchase = purchaseServ.findById(id);
        //Transforms the returned Purchase from purchaseServ.findById() into a PurchaseReturnResponse and returns it
        return new PurchaseReturnResponse()
                .createResponseFromPurchase(purchase);
    }

    //Create Purchase
    @PostMapping(value = "/purchases/customer/{id}")
    public PurchaseReturnResponse addPurchaseToCustomer(@PathVariable(value = "id") Long id, @RequestBody PurchaseCreationRequest purchaseReq) {
        Purchase purchase = purchaseServ.save(purchaseReq.getIsPaid(), id);
        //Transforms the returned Purchase from purchaseServ.save() into a PurchaseReturnResponse and returns it
        return new PurchaseReturnResponse()
                .createResponseFromPurchase(purchase);
    }

    //Add Product to Purchase
    @PostMapping(value = "/purchases/{purchase_id}/product/{product_id}")
    public PurchaseReturnResponse addProductToPurchase(@PathVariable(value = "purchase_id") Long purchaseId, @PathVariable(value = "product_id") Long productId) {
        Purchase purchase = purchaseServ.addProductToPurchase(productId, purchaseId);
        //Transforms the returned Purchase from purchaseServ.addProductToPurchase() into a PurchaseReturnResponse and returns it
        return new PurchaseReturnResponse()
                .createResponseFromPurchase(purchase);
    }

    //Delete purchase
    @DeleteMapping(value = "/purchases/{id}")
    public void deletePurchase(@PathVariable(value = "id") Long id) {
        purchaseServ.deleteById(id);
    }
}
