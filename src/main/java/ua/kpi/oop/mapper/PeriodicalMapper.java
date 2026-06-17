package ua.kpi.oop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.kpi.oop.dto.CreatePeriodicalRequest;
import ua.kpi.oop.dto.PeriodicalDto;
import ua.kpi.oop.entity.Periodical;

@Mapper(componentModel = "spring")
public interface PeriodicalMapper {
    PeriodicalDto toDto(Periodical periodical);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Periodical toEntity(CreatePeriodicalRequest request);
}
