package entity.converter;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;
import java.util.Date;

public record Birthday(LocalDate birthDate){

}
