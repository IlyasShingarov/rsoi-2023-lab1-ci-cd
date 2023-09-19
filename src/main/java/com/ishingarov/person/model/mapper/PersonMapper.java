package com.ishingarov.person.model.mapper;

import com.ishingarov.person.domain.Person;
import com.ishingarov.person.model.PersonRequest;
import com.ishingarov.person.model.PersonResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonResponse toResponse(Person person);
    List<PersonResponse> toResponse(List<Person> person);


    @Mapping(target = "id", ignore = true)
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    Person toEntity(@MappingTarget Person entity, PersonRequest request);

    @Mapping(target = "id", ignore = true)
    Person toEntity(PersonRequest request);

}
