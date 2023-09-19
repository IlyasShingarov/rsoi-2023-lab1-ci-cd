package com.ishingarov.person.unit;


import com.ishingarov.person.domain.Person;
import com.ishingarov.person.model.PersonRequest;
import com.ishingarov.person.model.PersonResponse;
import com.ishingarov.person.model.mapper.PersonMapperImpl;
import com.ishingarov.person.repository.PersonRepository;
import com.ishingarov.person.service.PersonServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest(classes = { PersonServiceImpl.class, PersonMapperImpl.class })
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonServiceImpl personService;

    @Test
    public void on_create_person_return_created_id_test() {
        PersonRequest sampleRequest = new PersonRequest("TestName", 12, "Moscow", "Student");
        Integer expectedId = 1;
        Person.PersonBuilder testPersonTemplate = Person.builder()
                .name("TestName")
                .age(12)
                .address("Moscow")
                .work("Student");

        Mockito.when(personRepository.save(testPersonTemplate.build()))
                .thenReturn(testPersonTemplate.id(expectedId).build());


        Integer generatedId = personService.createPerson(sampleRequest);

        Assertions.assertNotNull(generatedId);
        Assertions.assertEquals(expectedId, generatedId);
    }

    @Test
    public void on_create_if_ID_equals_null_throw_exception_test() {
        PersonRequest sampleRequest = new PersonRequest("TestName", 12, "Moscow", "Student");
        Mockito.when(personRepository.save(Person.builder().build()))
                .thenReturn(Person.builder().id(null).build());

        Assertions.assertThrows(NullPointerException.class, () ->
                personService.createPerson(sampleRequest));
    }

    @Test
    public void on_create_if_null_throw_exception_test() {
        PersonRequest sampleRequest = new PersonRequest("TestName", 12, "Moscow", "Student");
        Mockito.when(personRepository.save(Person.builder().build()))
                .thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () ->
                personService.createPerson(sampleRequest));
    }

    @Test
    public void on_get_unexsisting_person_throws_entity_not_found() {
        Integer id = 1;
        Mockito.when(personRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () ->
            personService.getPerson(id)
        );
    }

    @Test
    public void on_get_existing_person_success() {
        Integer id = 1;
        Person persistedPerson = Person.builder()
                .id(id)
                .name("TestName")
                .age(15)
                .address("TestAddress")
                .work("Student")
                .build();

        PersonResponse expectedResponse = new PersonResponse(
                persistedPerson.getId(),
                persistedPerson.getName(),
                persistedPerson.getAge(),
                persistedPerson.getAddress(),
                persistedPerson.getWork()
        );

        Mockito.when(personRepository.findById(id))
                .thenReturn(Optional.of(persistedPerson));

        PersonResponse actualResponse = personService.getPerson(id);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void on_get_person_with_ID_equals_null() {
        Integer id = 1;
        Person persistedPerson = Person.builder()
                .id(null)
                .name("TestName")
                .age(15)
                .address("TestAddress")
                .work("Student")
                .build();

        Mockito.when(personRepository.findById(id))
                .thenReturn(Optional.of(persistedPerson));

        Assertions.assertThrows(ValidationException.class,
                () -> personService.getPerson(id)
        );
    }


    @Test
    public void delete_existing_entity_success() {
        Assertions.assertDoesNotThrow(() -> personService.deletePerson(1));
    }
}
