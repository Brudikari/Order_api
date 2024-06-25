package payetonkawa.api.orders;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import payetonkawa.api.orders.model.Order;
import payetonkawa.api.orders.repository.OrderRepository;
import payetonkawa.api.orders.service.OrderService;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1);
        order.setCustomerId(123);
        order.setOrderNumber(456);
    }

    @Test
    void testFindOrderByIdOK() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        Optional<Order> foundOrder = orderService.findOrderById(1);
        assertTrue(foundOrder.isPresent());
        assertEquals(order, foundOrder.get());
    }

    @Test
    void testFindOrderById_InvalidId() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderService.findOrderById(1));
    }

    @Test
    void testFindOrderById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> orderService.findOrderById(null));
    }

    @Test
    void testFindOrderById_NegativeId() {
        assertThrows(IllegalArgumentException.class, () -> orderService.findOrderById(-1));
    }

    @Test
    void testGetAllOrdersOK() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order, new Order()));
        Iterable<Order> orders = orderService.getAllOrders();
        assertTrue(orders.iterator().hasNext());
    }

    @Test
    void testGetAllOrders_NoOrdersFound() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList());
        assertThrows(RuntimeException.class, () -> orderService.getAllOrders());
    }

    @Test
    void testSaveOrderOK() {
        when(orderRepository.save(order)).thenReturn(order);
        Order savedOrder = orderService.saveOrder(order);
        assertEquals(order, savedOrder);
    }

    @Test
    void testSaveOrder_NullOrder() {
        assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(null));
    }

    @Test
    void testDeleteOrderByIdOK() {
        when(orderRepository.existsById(1)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(1);
        orderService.deleteOrderById(1);
        verify(orderRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteOrderById_InvalidId() {
        when(orderRepository.existsById(1)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> orderService.deleteOrderById(1));
    }

    @Test
    void testDeleteOrderById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(null));
    }

    @Test
    void testDeleteOrderById_NegativeId() {
        assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(-1));
    }
}
