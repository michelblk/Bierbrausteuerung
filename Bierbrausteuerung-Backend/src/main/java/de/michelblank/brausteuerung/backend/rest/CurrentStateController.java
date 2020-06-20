package de.michelblank.brausteuerung.backend.rest;

import de.michelblank.brausteuerung.backend.model.CurrentState;
import de.michelblank.brausteuerung.backend.repository.CurrentStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/currentState")
@CrossOrigin(origins = "*")
public class CurrentStateController {
    private CurrentStateRepository repository;

    @Autowired
    public CurrentStateController(CurrentStateRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public Iterable<CurrentState> getAllCurrentStates() {
        return repository.findAll();
    }
}
