package de.michelblank.brausteuerung.backend.repository;

import de.michelblank.brausteuerung.backend.model.CurrentState;
import org.springframework.data.repository.CrudRepository;

public interface CurrentStateRepository extends CrudRepository<CurrentState, Integer> {

}
