package de.michelblank.brausteuerung.backend.calculator;

import de.michelblank.brausteuerung.backend.model.TargetStateConfiguration;
import de.michelblank.brausteuerung.backend.repository.TargetStateConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TargetStateConfigurationLoader {
    private TargetStateConfigurationManager tscm;
    private TargetStateConfigurationRepository tscr;

    @Autowired
    public TargetStateConfigurationLoader(TargetStateConfigurationManager tscm,
                                          TargetStateConfigurationRepository tscr) {
        this.tscm = tscm;
        this.tscr = tscr;
    }

    @PostConstruct
    public void init() {
        Iterable<TargetStateConfiguration> tscIterable = tscr.findAll();
        for (TargetStateConfiguration tsc : tscIterable) {
            tscm.addConfiguration(tsc);
        }
    }

}
