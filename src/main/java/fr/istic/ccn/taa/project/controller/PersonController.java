package fr.istic.ccn.taa.project.controller;

import fr.istic.ccn.taa.project.model.Person;
import fr.istic.ccn.taa.project.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/list")
    public List<Person> getPersons(){
        List<Person> personList = this.personService.getPersons();
        return personList;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Person addPerson(@RequestBody Person person) throws Exception{

        if(!this.personService.existPerson(person.getEmail())){
            this.personService.createPerson(person);
        } else{
            throw new Exception("person with " + person.getEmail() + " exists");
        }
        return person;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Person updatePerson(@RequestBody Person person){

        String message = null;
        Person personToUpdate = this.personService.updatePerson(person);
        if(personToUpdate != null){
            message = "Person " + personToUpdate.getFirstname() + "have been updated";
        }
        else{
            message = "Email " + person.getEmail()+ " don't exist ";
        }
        return person;
    }

    @RequestMapping(value="email", method = RequestMethod.GET)
    public Person displayPerson(@RequestParam String email){
        Person personToDisplay = this.personService.displayProfil(email);

        String message = null;
        if(personToDisplay != null){
            return personToDisplay;
        }
        else {
            message = "' " + email + " ' does'nt exist";
        }
        return personToDisplay;
    }

    /**TO DO*/
    @RequestMapping(value="delete/{email}", method = RequestMethod.DELETE)
    public String deletePerson(@PathVariable("email") String email){

        String message = "";
        Person personToDelete = this.personService.deletePerson(email);
        if(personToDelete != null){
           message = "Person " + personToDelete.getFirstname() + " have been deleted";
        }
        else {
            message = "Person " + email + " does'nt exist in the DB";
        }
        return message;
    }

}
