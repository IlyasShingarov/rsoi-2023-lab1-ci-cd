package com.ishingarov.person.service;

import com.ishingarov.person.model.PersonRequest;
import com.ishingarov.person.model.PersonResponse;
import com.ishingarov.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public PersonResponse getPerson(Integer id) {
        return null;
    }

    @Override
    public List<PersonResponse> getPersons() {
        return null;
    }

    @Override
    public Integer createPerson(PersonRequest request) {
        return null;
    }

    @Override
    public PersonResponse editPerson(Integer id, PersonRequest personRequest) {
        return null;
    }

    @Override
    public void deletePerson(Integer id) {

    }
}
