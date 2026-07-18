package org.example.constructionmaterialsapi.repository;

import org.example.constructionmaterialsapi.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
