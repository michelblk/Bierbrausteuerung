package de.michelblank.brausteuerung.backend.repository;

import de.michelblank.brausteuerung.backend.model.TargetStateConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetStateConfigurationRepository extends CrudRepository<TargetStateConfiguration, Integer> {

}
