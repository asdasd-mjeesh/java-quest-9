package dto;

import java.time.LocalDate;

public record ProductFilter(int limit,
                            int offset,
                            String name,
                            Long producer_id,
                            Integer cost,
                            LocalDate shelf_life,
                            Integer count,
                            Integer price) {
}
