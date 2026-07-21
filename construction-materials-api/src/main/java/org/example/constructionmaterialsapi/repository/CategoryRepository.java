package org.example.constructionmaterialsapi.repository;

import org.example.constructionmaterialsapi.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
