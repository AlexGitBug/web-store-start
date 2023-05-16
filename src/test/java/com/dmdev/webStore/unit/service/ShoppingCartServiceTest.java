package com.dmdev.webStore.unit.service;


import com.dmdev.webStore.dto.TupleReadDto;
import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.dto.order.OrderCreateEditDto;
import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.dto.product.ProductReadDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartReadDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.*;
import com.dmdev.webStore.entity.embeddable.DeliveryAdress;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import com.dmdev.webStore.entity.enums.Role;
import com.dmdev.webStore.mapper.TupleReadMapper;
import com.dmdev.webStore.mapper.shoppingCart.ShoppingCartCreateEditMapper;
import com.dmdev.webStore.mapper.shoppingCart.ShoppingCartReadMapper;
import com.dmdev.webStore.repository.ShoppingCartRepository;
import com.dmdev.webStore.repository.filter.OrderFilter;
import com.dmdev.webStore.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.dmdev.webStore.entity.enums.ProgressStatus.IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartCreateEditMapper shoppingCartCreateEditMapper;
    @Mock
    private ShoppingCartReadMapper shoppingCartReadMapper;
    @Mock
    private TupleReadMapper tupleReadMapper;
    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Test
    void create() {
        var shoppingCart = getShoppingCart();
        var shoppingCartCreateEditDto = getShoppingCartCreateEditDto();
        var shoppingCartReadDto = getShoppingCartReadDto();
        doReturn(shoppingCart).when(shoppingCartCreateEditMapper).map(shoppingCartCreateEditDto);
        doReturn(shoppingCart).when(shoppingCartRepository).save(shoppingCart);
        doReturn(shoppingCartReadDto).when(shoppingCartReadMapper).map(shoppingCart);

        var actualResult = shoppingCartService.create(shoppingCartCreateEditDto);

        assertThat(actualResult).isEqualTo(shoppingCartReadDto);
        verify(shoppingCartRepository).save(shoppingCart);
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
    }

    @Test
    void findShoppingCartByOrderId() {
        ShoppingCart shoppingCart = getShoppingCart();
        ShoppingCart shoppingCart2 = getShoppingCart2();
        ShoppingCartReadDto shoppingCartReadDto = getShoppingCartReadDto();
        ShoppingCartReadDto shoppingCartReadDto2 = getShoppingCartReadDto2();
        List<ShoppingCart> shoppingCards = List.of(shoppingCart, shoppingCart2);
        List<ShoppingCartReadDto> shoppingCartReadDtos = List.of(shoppingCartReadDto, shoppingCartReadDto2);
        doReturn(shoppingCards).when(shoppingCartRepository).findShoppingCartByOrderId(getOrder().getId());
        doReturn(shoppingCartReadDto).when(shoppingCartReadMapper).map(shoppingCards.get(0));
        doReturn(shoppingCartReadDto2).when(shoppingCartReadMapper).map(shoppingCards.get(1));

        List<ShoppingCartReadDto> actualResult = shoppingCartService.findShoppingCartByOrderId(getOrder().getId());

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).containsExactlyInAnyOrder(shoppingCartReadDto, shoppingCartReadDto2);
        assertEquals(shoppingCartReadDtos, actualResult);
    }

    @Test
    void findAll() {
        var shoppingCart = getShoppingCart();
        var shoppingCart2 = getShoppingCart2();
        var shoppingCartReadDto = getShoppingCartReadDto();
        var shoppingCartReadDto2 = getShoppingCartReadDto2();
        var shoppingCards = List.of(shoppingCart, shoppingCart2);
        var shoppingCartReadDtos = List.of(shoppingCartReadDto, shoppingCartReadDto2);
        doReturn(shoppingCards).when(shoppingCartRepository).findAll();
        doReturn(shoppingCartReadDtos.get(0), shoppingCartReadDtos.get(1))
                .when(shoppingCartReadMapper).map(any(ShoppingCart.class));

        List<ShoppingCartReadDto> actualResult = shoppingCartService.findAll();

        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(shoppingCartReadDtos);
        assertThat(actualResult).containsExactlyInAnyOrder(shoppingCartReadDto, shoppingCartReadDto2);
        verify(shoppingCartRepository).findAll();
    }

    @Test
    void delete() {
        var shoppingCart = getShoppingCart();
        doReturn(Optional.of(shoppingCart)).when(shoppingCartRepository).findById(shoppingCart.getId());

        var actualResult = shoppingCartService.delete(shoppingCart.getId());

        assertThat(actualResult).isTrue();
    }

    @Test
    void findAllOrdersWithProductsOfOneUser() {
        var orderFilter = OrderFilter.builder()
                .deliveryDate(LocalDate.now())
                .build();
        var personalInfoFilter = PersonalInformationFilter.builder()
                .email("test@gmail.com")
                .build();
        var shoppingCart = getShoppingCart();
        var shoppingCart2 = getShoppingCart2();
        var shoppingCartReadDto = getShoppingCartReadDto();
        var shoppingCartReadDto2 = getShoppingCartReadDto2();
        var shoppingCards = List.of(shoppingCart, shoppingCart2);
        var shoppingCartReadDtos = List.of(shoppingCartReadDto, shoppingCartReadDto2);
        doReturn(shoppingCards).when(shoppingCartRepository).findAllOrdersWithProductsOfOneUser(personalInfoFilter, orderFilter);
        doReturn(shoppingCartReadDto).when(shoppingCartReadMapper).map(shoppingCards.get(0));
        doReturn(shoppingCartReadDto2).when(shoppingCartReadMapper).map(shoppingCards.get(1));

        var actualResult = shoppingCartService.findAllOrdersWithProductsOfOneUser(personalInfoFilter, orderFilter);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).containsExactlyInAnyOrder(shoppingCartReadDto, shoppingCartReadDto2);
        assertEquals(shoppingCartReadDtos, actualResult);
    }

    private TupleReadDto getTupleReadDto() {
        return new TupleReadDto(1, 1);
    }

    private TupleReadDto getTupleReadDto2() {
        return new TupleReadDto(2, 2);
    }

    private ShoppingCart getShoppingCart() {
        return ShoppingCart.builder()
                .id(1)
                .createdAt(LocalDate.now())
                .order(getOrder())
                .product(getProduct(getCatalog()))
                .build();
    }

    private ShoppingCart getShoppingCart2() {
        return ShoppingCart.builder()
                .id(2)
                .createdAt(LocalDate.now())
                .order(getOrder())
                .product(getProduct1(getCatalog()))
                .build();
    }

    private ShoppingCartReadDto getShoppingCartReadDto() {
        return new ShoppingCartReadDto(1, getOrderReadDto(), getProductReadDto(getCatalogReadDto()), LocalDate.now(), null);
    }

    private ShoppingCartReadDto getShoppingCartReadDto2() {
        return new ShoppingCartReadDto(2, getOrderReadDto(), getProductReadDto1(getCatalogReadDto()), LocalDate.now(), null);
    }

    private ShoppingCartCreateEditDto getShoppingCartCreateEditDto() {
        return new ShoppingCartCreateEditDto(1, 1, LocalDate.now(), null);
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

    private Product getProduct(Catalog catalog) {
        return Product.builder()
                .id(1)
                .model("test")
                .brand(Brand.APPLE)
                .dateOfRelease(LocalDate.now())
                .price(123)
                .color(Color.BLACK)
                .image("test")
                .catalog(catalog)
                .build();
    }

    private Product getProduct1(Catalog catalog) {
        return Product.builder()
                .id(1)
                .model("test")
                .brand(Brand.APPLE)
                .dateOfRelease(LocalDate.now())
                .price(123)
                .color(Color.BLACK)
                .image("test")
                .catalog(catalog)
                .build();
    }

    private Catalog getCatalog() {
        return Catalog.builder()
                .id(1)
                .category("test")
                .build();
    }

    private CatalogReadDto getCatalogReadDto() {
        return new CatalogReadDto(1, "test");
    }

    private ProductReadDto getProductReadDto(CatalogReadDto catalogReadDto) {
        return new ProductReadDto(1, "test", LocalDate.now(), 123, "test", Color.BLACK, Brand.APPLE, catalogReadDto);
    }

    private ProductReadDto getProductReadDto1(CatalogReadDto catalogReadDto) {
        return new ProductReadDto(1, "test", LocalDate.now(), 123, "test", Color.BLACK, Brand.APPLE, catalogReadDto);
    }

    private ProductCreateEditDto getProductCreateEditDto() {
        return new ProductCreateEditDto("test", LocalDate.now(), 123, Color.BLACK, Brand.APPLE, 9, new MockMultipartFile("test", new byte[0]));
    }

}
