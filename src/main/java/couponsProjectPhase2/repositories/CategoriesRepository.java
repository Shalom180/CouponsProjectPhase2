package couponsProjectPhase2.repositories;

import couponsProjectPhase2.beans.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Integer> {
}
