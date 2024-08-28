package couponsProjectPhase2.repositories;

import couponsProjectPhase2.beans.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
}
