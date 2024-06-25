package payetonkawa.api.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payetonkawa.api.orders.model.Order;
import payetonkawa.api.orders.service.OrderService;

import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Crée une nouvelle commande en base de données
     * @param order l'objet à créer
     * @return une réponse HTTP CREATED contenant la commande enregistrée, ou une réponse HTTP INTERNAL_SERVER_ERROR en cas d'erreur du serveur
     */
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order savedOrder = orderService.saveOrder(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Récupère une commande par son identifiant
     * @param id l'identifiant de la commande
     * @return une réponse HTTP OK contenant l'objet commande s'il existe, sinon une réponse HTTP NOT_FOUND ou une réponse HTTP INTERNAL_SERVER_ERROR en cas d'erreur du serveur
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") final Integer id) {
        try {
            Optional<Order> order = orderService.findOrderById(id);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Récupère une liste de commandes
     * @return une réponse HTTP OK contenant la liste des commandes, ou une réponse HTTP INTERNAL_SERVER_ERROR en cas d'erreurs
     */
    @GetMapping("/orders")
    public ResponseEntity<Iterable<Order>> getOrders() {
        try {
            Iterable<Order> orders = orderService.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Met à jour une commande
     * @param id l'identifiant de la commande
     * @param order la commande qu'on veut mettre à jour
     * @return une réponse HTTP OK contenant l'objet commande mis à jour, sinon une réponse HTTP NOT_FOUND ou une réponse HTTP INTERNAL_SERVER_ERROR en cas d'erreur du serveur
     */
    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") final Integer id, @RequestBody Order order) {
        try {
            Optional<Order> o = orderService.findOrderById(id);
            if (o.isPresent()) {
                Order currentOrder = o.get();

                Integer customerId = order.getCustomerId();
                if (customerId != null) {
                    currentOrder.setCustomerId(customerId);
                }
                Integer orderNumber = order.getOrderNumber();
                if (orderNumber != null) {
                    currentOrder.setOrderNumber(orderNumber);
                }

                orderService.saveOrder(currentOrder);
                return new ResponseEntity<>(currentOrder, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Supprime une commande par son identifiant
     * @param id l'identifiant de la commande
     * @return une réponse HTTP NO_CONTENT si la suppression est réussie, sinon une réponse HTTP INTERNAL_SERVER_ERROR en cas d'erreur du serveur
     */
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") final Integer id) {
        try {
            orderService.deleteOrderById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

