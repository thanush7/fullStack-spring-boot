package com.kgisl.captialmarketing.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kgisl.captialmarketing.stock.entity.Stock;

public interface StockRepository extends JpaRepository<Stock,Integer> {
    
}
