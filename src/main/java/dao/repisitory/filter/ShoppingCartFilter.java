package dao.repisitory.filter;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ShoppingCartFilter {

    LocalDate createdAt;
}