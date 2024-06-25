package payetonkawa.api.orders.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import payetonkawa.api.orders.model.Order;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.id =: id")
    Optional<Order> findById(Integer id);
}
