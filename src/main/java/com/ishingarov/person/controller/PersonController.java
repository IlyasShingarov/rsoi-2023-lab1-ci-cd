package com.ishingarov.person.controller;

import com.ishingarov.person.model.ErrorResponse;
import com.ishingarov.person.model.PersonRequest;
import com.ishingarov.person.model.PersonResponse;
import com.ishingarov.person.model.ValidationErrorResponse;
import com.ishingarov.person.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/persons")
public class PersonController {
    private final PersonService personService;

    @Operation(
            summary = "Get person by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Person for ID",
                            content = { @Content(schema = @Schema(implementation = PersonResponse.class)) }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found Person for ID",
                            content = { @Content(schema = @Schema(implementation = ErrorResponse.class)) }
                    )
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonResponse getPerson(@PathVariable Integer id) {
        return personService.getPerson(id);
    }

    @Operation(
            summary = "Get all persons",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "List all persons",
                    content = { @Content(array = @ArraySchema(schema = @Schema(implementation = PersonResponse.class))) }
            )
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonResponse> listPersons() {
        return personService.getPersons();
    }

    @Operation(
            summary = "Create a new person",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created new person",
                            headers = { @Header(name = "Location", description = "Path to a new person") }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid data",
                            content = { @Content(schema = @Schema(implementation = ValidationErrorResponse.class)) }
                    )
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPerson(@Valid @RequestBody PersonRequest request) {
        var id = personService.createPerson(request);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(id)
                        .toUri()
        ).build();
    }

    @Operation(
            summary = "Update person by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Person with ID was updated",
                            content = { @Content(schema = @Schema(implementation = PersonResponse.class)) }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid data",
                            content = { @Content(schema = @Schema(implementation = ValidationErrorResponse.class)) }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found person for ID",
                            content = { @Content(schema = @Schema(implementation = ErrorResponse.class)) }
                    )
            }
    )
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    PersonResponse editPerson(@PathVariable Integer id, @Valid @RequestBody PersonRequest request) {
        return personService.editPerson(id, request);
    }

    @Operation(
            summary = "Remove Person by ID",
            responses = @ApiResponse(responseCode = "204", description = "Person for ID was removed")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void editPerson(@PathVariable Integer id) {
        personService.deletePerson(id);
    }
}
