import math
import time
from _thread import allocate_lock
from threading import Thread

import adafruit_ads1x15.ads1115 as ADS
import board
import busio
from adafruit_ads1x15.analog_in import AnalogIn


class Thermistor:
    def __init__(self):
        self.__i2c = busio.I2C(board.SCL, board.SDA)
        self.__ads = ADS.ADS1115(self.__i2c)
        self.__reference_voltage = 3.3
        self.__resistor_resistance = 100000  # ohm
        self.__current_temperature = -1
        self.__readings_per_second = 70
        self.__current_temperature_lock = allocate_lock()
        self.thread = Thread(target=self.__run, args=(), daemon=True)

    def start(self):
        self.thread.start()

    def current_temperature(self):
        return self.__current_temperature

    def __run(self):
        while True:
            read_temperature = self.__read_temperature()

            self.__current_temperature_lock.acquire()
            self.__current_temperature = read_temperature
            self.__current_temperature_lock.release()

    def __read_temperature(self):
        voltage = 0
        for x in range(self.__readings_per_second):
            start_time = time.time()
            chan = AnalogIn(self.__ads, ADS.P0)
            voltage += chan.voltage
            end_time = time.time()

            delay = (1 / 860) - (end_time - start_time)  # target delay - time it took to read
            if delay > 0:
                time.sleep(1 / 860)  # maximum sample rate is 860
        voltage = voltage / self.__readings_per_second

        resistance = self.__calculate_resistance(voltage)
        return self.__calculate_celsius(resistance)

    @staticmethod
    def __calculate_celsius(resistance):
        steinhart = resistance / 107000  # (R / Ro)
        steinhart = math.log(steinhart)  # ln(R / Ro)
        steinhart /= -3950  # 1 / B * ln(R / Ro)  #! umgekehrt
        steinhart += 1.0 / (27 + 273.15)  # + (1 / To)
        steinhart = 1.0 / steinhart  # Invert
        steinhart -= 273.15  # convert to C
        temperature = steinhart

        return float("{:.1f}".format(temperature))

    def __calculate_resistance(self, read_voltage):
        return read_voltage * (self.__resistor_resistance / (self.__reference_voltage - read_voltage))
