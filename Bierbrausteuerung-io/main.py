#!/usr/bin/env python
import time

from component.display import Display
from component.relay import Relay
from component.thermistor import Thermistor
from javaConnection import JavaConnection


def main():
    thermistor = Thermistor()
    relay = Relay()
    display = Display()
    java_connection = JavaConnection()

    thermistor.start()

    while True:
        try:
            temperature = thermistor.current_temperature()

            target_state = java_connection.getState(float(temperature))
            target_temperature = target_state.getTargetTemperature() if target_state is not None else None
            target_heating = target_state.isHeating() if target_state is not None else None

            if target_heating:
                if relay.status() == 0:
                    relay.on()
            else:
                relay.off()

            display.try_print(temperature, target_temperature, relay.status())
            time.sleep(0.5)
        except KeyboardInterrupt:
            break


if __name__ == "__main__":
    main()
