package query.filter;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CatalogFilter {

    String category;
}