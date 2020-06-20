package de.michelblank.brausteuerung.backend.rest;

import de.michelblank.brausteuerung.backend.calculator.TargetStateConfigurationManager;
import de.michelblank.brausteuerung.backend.model.TargetStateConfiguration;
import de.michelblank.brausteuerung.backend.repository.TargetStateConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/configuration")
@CrossOrigin(origins = "*")
public class ConfigurationController {
    private TargetStateConfigurationRepository repository;
    private TargetStateConfigurationManager manager;

    @Autowired
    public ConfigurationController(TargetStateConfigurationRepository repository,
                                   TargetStateConfigurationManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    @GetMapping(produces = "application/json")
    public Iterable<TargetStateConfiguration> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public void saveNewConfiguration(@RequestBody List<TargetStateConfiguration> configuration) {
        abortAll();
        for (TargetStateConfiguration config : configuration) {
            repository.save(config);
            manager.addConfiguration(config);
        }
    }

    @DeleteMapping
    public void abortAllConfigurations() {
        abortAll();
    }

    private void abortAll() {
        manager.clearConfigurations();
        repository.deleteAll();
    }
}
