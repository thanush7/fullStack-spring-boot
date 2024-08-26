package com.kgisl.captialmarketing.stock.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kgisl.captialmarketing.stock.entity.Stock;
import com.kgisl.captialmarketing.stock.service.StockService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class StockController {

    @Autowired
    private StockService service;

    @GetMapping("/details")
    public List<Stock> findAllProducts() {
        return service.getDetails();
    }

    @PostMapping("/addall")
    public List<Stock> addProducts(@RequestBody List<Stock> products) {
        return service.saveProducts(products);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStock(@PathVariable int id) {
        return service.deleteByid(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
        Stock savedStock = service.saveStock(stock);
        return ResponseEntity.ok(savedStock);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Optional<Stock>> update(@PathVariable int id, @RequestBody Stock stock) {
        Optional<Stock> updateStock = service.update(id, stock);
        if (updateStock != null) {
            return ResponseEntity.ok(updateStock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getone/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable int id) {
        Optional<Stock> stock = service.getStockById(id);
        if (stock.isPresent()) {
            return ResponseEntity.ok(stock.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
