package payetonkawa.api.orders.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payetonkawa.api.orders.model.Order;
import payetonkawa.api.orders.repository.OrderRepository;

import java.util.Optional;

@Data
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Récupère une commande par son identifiant.
     *
     * @param id l'identifiant de la commande
     * @return un Optional contenant la commande si elle est trouvée
     * @throws IllegalArgumentException si l'identifiant est null ou inférieur ou égal à 0
     * @throws EntityNotFoundException si la commande avec l'identifiant donné n'existe pas
     */
    public Optional<Order> findOrderById(Integer id) throws IllegalArgumentException, EntityNotFoundException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'identifiant de la commande est invalide");
        }
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) {
            throw new EntityNotFoundException("La commande avec l'identifiant " + id + " n'existe pas");
        }

        return order;
    }

    /**
     * Récupère toutes les commandes.
     *
     * @return un Iterable contenant toutes les commandes
     * @throws RuntimeException si aucune commande n'est trouvée
     */
    public Iterable<Order> getAllOrders() throws RuntimeException {
        Iterable<Order> orders = orderRepository.findAll();
        if (!orders.iterator().hasNext()) {
            throw new RuntimeException("No orders found");
        }
        return orders;
    }

    /**
     * Enregistre une nouvelle commande.
     *
     * @param order la commande à enregistrer
     * @return la commande enregistrée
     * @throws IllegalArgumentException si la commande est null
     */
    public Order saveOrder(Order order) throws IllegalArgumentException {
        if (order == null) {
            throw new IllegalArgumentException("donnée invalide");
        }

        return orderRepository.save(order);
    }

    /**
     * Supprime une commande par son identifiant.
     *
     * @param id l'identifiant de la commande à supprimer
     * @throws IllegalArgumentException si l'identifiant est null ou inférieur ou égal à 0
     * @throws EntityNotFoundException si la commande avec l'identifiant donné n'existe pas
     */
    public void deleteOrderById(Integer id) throws IllegalArgumentException, EntityNotFoundException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'identifiant de la commande est invalide");
        }

        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("La commande avec l'identifiant " + id + " n'existe pas");
        }

        orderRepository.deleteById(id);
    }
}

