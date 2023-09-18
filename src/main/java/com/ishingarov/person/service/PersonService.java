package com.ishingarov.person.service;

import com.ishingarov.person.model.PersonRequest;
import com.ishingarov.person.model.PersonResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    PersonResponse getPerson(Integer id);
    List<PersonResponse> getPersons();
    Integer createPerson(PersonRequest request);
    PersonResponse editPerson(Integer id, PersonRequest personRequest);
    void deletePerson(Integer id);
}
