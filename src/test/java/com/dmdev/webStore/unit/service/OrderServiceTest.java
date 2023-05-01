package com.dmdev.webStore.unit.service;

import com.dmdev.webStore.dto.order.OrderCreateEditDto;
import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.user.UserCreateEditDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.DeliveryAdress;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import com.dmdev.webStore.entity.enums.ProgressStatus;
import com.dmdev.webStore.entity.enums.Role;
import com.dmdev.webStore.mapper.order.OrderCreateEditMapper;
import com.dmdev.webStore.mapper.order.OrderReadMapper;
import com.dmdev.webStore.repository.OrderRepository;
import com.dmdev.webStore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.dmdev.webStore.entity.enums.ProgressStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private static final Integer ORDER_ID = 1;
    @Mock
    private OrderReadMapper orderReadMapper;
    @Mock
    private OrderCreateEditMapper orderCreateEditMapper;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    void findByIdSuccess() {
        var order = getOrder();
        var expectOrderDto = getOrderReadDto();
        doReturn(Optional.of(order)).when(orderRepository).findById(ORDER_ID);
        doReturn(expectOrderDto).when(orderReadMapper).map(order);

        var actualResult = orderService.findById(ORDER_ID);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(expectOrderDto);
    }

    @Test
    void findByIdNotSuccess() {
        doReturn(Optional.empty()).when(orderRepository).findById(any());

        var actualResult = orderService.findById(any());

        assertThat(actualResult).isEmpty();
        verifyNoInteractions(orderReadMapper);
    }

    @Test
    void findAll() {
        var order = getOrder();
        var order2 = getOrder2();
        var orderReadDto = getOrderReadDto();
        var orderReadDto2 = getOrderReadDto2();
        var orders = List.of(order, order2);
        var ordersDtoList = List.of(orderReadDto, orderReadDto2);
        doReturn(orders).when(orderRepository).findAll();
        doReturn(ordersDtoList.get(0), ordersDtoList.get(1)).when(orderReadMapper).map(any(Order.class));

        var actualResult = orderService.findAll();
        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(ordersDtoList);
        assertThat(actualResult).containsExactlyInAnyOrder(orderReadDto, orderReadDto2);
    }

    @Test
    void findByUserId() {
        var order = getOrder();
        var orderReadDto = getOrderReadDto();
        var user = getUser();
        doReturn(Optional.of(order)).when(orderRepository).findByUserId(user.getId());
        doReturn(orderReadDto).when(orderReadMapper).map(order);

        var actualResult = orderService.findByUserId(user.getId());

        assertEquals(actualResult, orderReadDto);
    }

    @Test
    void findByIdAndStatus() {
        var order = getOrder();
        var orderReadDto = getOrderReadDto();
        doReturn(Optional.of(order)).when(orderRepository).findByIdAndStatus(order.getId(), IN_PROGRESS);
        doReturn(orderReadDto).when(orderReadMapper).map(order);

        var actualResult = orderService.findByIdAndStatus(order.getId(), IN_PROGRESS);

        assertEquals(actualResult.get(), orderReadDto);
    }

    @Test
    void orderNotFoundByIdAndStatus() {
        doReturn(Optional.empty()).when(orderRepository).findByIdAndStatus(123, IN_PROGRESS);

        var actualResult = orderService.findByIdAndStatus(123, IN_PROGRESS);

        assertEquals(Optional.empty(), actualResult);
    }


    

    private Order getOrder() {
        return Order.builder()
                .id(1)
                .deliveryAdress(new DeliveryAdress(
                        "test", "test", 1
                ))
                .deliveryDate(LocalDate.now())
                .user(getUser())
                .paymentCondition(PaymentCondition.CASH)
                .status(IN_PROGRESS)
                .build();
    }

    private Order getOrder2() {
        return Order.builder()
                .id(2)
                .deliveryAdress(new DeliveryAdress(
                        "test", "test", 1
                ))
                .deliveryDate(LocalDate.now())
                .user(getUser2())
                .paymentCondition(PaymentCondition.CASH)
                .status(IN_PROGRESS)
                .build();
    }

    private OrderReadDto getOrderReadDto() {
        return new OrderReadDto(1, "test", "test", 1, LocalDate.now(), PaymentCondition.CASH, IN_PROGRESS, getUserReadDto());
    }

    private OrderReadDto getOrderReadDto2() {
        return new OrderReadDto(2, "test", "test", 1, LocalDate.now(), PaymentCondition.CASH, IN_PROGRESS, getUserReadDto());
    }

    private OrderCreateEditDto getOrderCreateEditDto() {
        return new OrderCreateEditDto("test", "test", 1, LocalDate.now(), PaymentCondition.CASH, IN_PROGRESS, 1);
    }

    private User getUser() {
        return User.builder()
                .id(1)
                .personalInformation(new PersonalInformation(
                        "test",
                        "test",
                        "test@gmail.com",
                        "test",
                        LocalDate.now())
                )
                .password("test")
                .role(Role.ADMIN)
                .build();
    }

    private User getUser2() {
        return User.builder()
                .id(2)
                .personalInformation(new PersonalInformation(
                        "test2",
                        "test2",
                        "test2@gmail.com",
                        "test2",
                        LocalDate.now())
                )
                .password("test2")
                .role(Role.USER)
                .build();
    }

    private UserReadDto getUserReadDto() {
        return new UserReadDto(1, "test", "test", "test@gmail.com", "test", "test", LocalDate.now(), Role.ADMIN);
    }

    private UserReadDto getUserReadDto2() {
        return new UserReadDto(2, "test2", "test2", "test2@gmail.com", "test2", "test2", LocalDate.now(), Role.USER);
    }

    private UserCreateEditDto getUserCreateEditDto() {
        return new UserCreateEditDto("test", "test", "test@gmail.com", "test", "test", LocalDate.now(), Role.ADMIN);
    }
}
