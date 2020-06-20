import gpiozero


class Relay:
    def __init__(self):
        self.relay_pin = 21  # GPIO 21
        self.relay = gpiozero.OutputDevice(self.relay_pin, active_high=True, initial_value=False)

    def status(self):
        return self.relay.value

    def on(self):
        self.relay.on()

    def off(self):
        self.relay.off()
