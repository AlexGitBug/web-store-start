package query.filter;

import entity.embeddable.PersonalInformation;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

    PersonalInformation personalInformation;

}