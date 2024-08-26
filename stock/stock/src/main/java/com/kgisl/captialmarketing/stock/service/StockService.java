package com.kgisl.captialmarketing.stock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgisl.captialmarketing.stock.entity.Stock;
import com.kgisl.captialmarketing.stock.repository.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    public List<Stock> getDetails(){
        List<Stock> all=repository.findAll();
        return all;
    }

    public List<Stock> saveProducts(List<Stock> products){
        return repository.saveAll(products);
    }
    public String deleteByid(int id){
        repository.deleteById(id);
        return "removed";
    }

    public Stock saveStock(Stock stock){
        return repository.save(stock);
    }
    public Optional<Stock> update(int id,Stock updateStock){
        return repository.findById(id).map(stock->{
            stock.setSymbol(updateStock.getSymbol());
            stock.setCompanyName(updateStock.getCompanyName());
            stock.setCurrentPrice(updateStock.getCurrentPrice());
            stock.setDate(updateStock.getDate());
            return repository.save(stock);
        });
    }
    public Optional<Stock> getStockById(int id) {
        return repository.findById(id);
    }    
}
