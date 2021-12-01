package com.telran.contacts.controller.contact;

import com.telran.contacts.dao.contact.ContactRepository;
import com.telran.contacts.pojo.dto.AddContactSuccessDto;
import com.telran.contacts.pojo.dto.ContactAddDto;
import com.telran.contacts.pojo.dto.ContactDto;
import com.telran.contacts.pojo.dto.ContactOperationResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Api(tags = {"Contact controller"})

@RestController
@Validated
@RequestMapping("contact")
public class ContactController {
    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping
    public AddContactSuccessDto add(@Valid @RequestBody @ApiParam("Add New Contact") ContactAddDto contact, @ApiIgnore Principal principal) {
        UUID id = contactRepository.addContact(contact, principal.getName());
        return new AddContactSuccessDto(id.toString());
    }

    @GetMapping("all")
    public List<ContactDto> getAll(@ApiIgnore Principal principal) {
        return contactRepository.getAllContacts(principal.getName());
    }

    @GetMapping("{id}")
    public ContactDto getById(
            @PathVariable
            @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "ID Format Incorrect")
            @ApiParam("ID Contact")
                    String id,
            @ApiIgnore Principal principal) {
        return contactRepository.getContactById(UUID.fromString(id), principal.getName());
    }

    @DeleteMapping("{id}")
    public ContactOperationResultDto deleteById(
            @PathVariable
            @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "ID Format Incorrect")
            @ApiParam("ID Contact")
                    String id,
            @ApiIgnore Principal principal) {
        contactRepository.removeContact(UUID.fromString(id),principal.getName());
        return new ContactOperationResultDto("Contact with id: " + id + " delete succesfully");
    }

    @PutMapping
    public ContactOperationResultDto update(@RequestBody @Valid @ApiParam("Updated contact") ContactDto contact, @ApiIgnore Principal principal) {
        contactRepository.updateContact(contact, principal.getName());
        return new ContactOperationResultDto("Contact update successfully");
    }

    @DeleteMapping("all")
    public ContactOperationResultDto deleteAll(@ApiIgnore Principal principal) {
        contactRepository.removeAllContacts(principal.getName());
        return new ContactOperationResultDto("All contacts deleted successfully");
    }
}
