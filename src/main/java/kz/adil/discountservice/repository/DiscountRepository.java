package kz.adil.discountservice.repository;

import kz.adil.discountservice.model.discount.AbstractDiscount;
import kz.adil.discountservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends MongoRepository<AbstractDiscount, String> {
    @Query("{name:'?0'}")
    User findByName(String name);
}
