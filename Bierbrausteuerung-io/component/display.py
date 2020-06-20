import os

from PIL import ImageFont
from luma.core.error import DeviceNotFoundError
from luma.core.interface.serial import i2c
from luma.core.render import canvas
from luma.oled.device import sh1106


class Display:
    def __init__(self):
        self.driver = 'sh1112'
        self.width = 128
        self.height = 64
        self.rotate = 0
        self.port = 1
        self.address = 0x3C
        self.font = 'font.ttf'
        self.device = self.__get_device()

    def __get_device(self):
        try:
            serial = i2c(port=self.port, address=self.address)
            return sh1106(
                width=self.width,
                height=self.height,
                rotate=self.rotate,
                serial_interface=serial
            )
        except DeviceNotFoundError:
            return None

    def build_font(self, font_size=12):
        font_path = os.path.abspath(
            os.path.join(
                os.path.dirname(__file__),
                self.font
            )
        )
        return ImageFont.truetype(font_path, font_size)

    def __print(self, temperature, target_temperature, relay):
        if self.device is None:
            return

        font_big = self.build_font(20)
        font_small = self.build_font(14)

        with canvas(self.device) as draw:
            draw.text((0, 0), "Brausteuerung", font=font_big, fill="white")
            draw.text((0, 20), str(temperature) + " / " + str(target_temperature) + "°C", font=font_small, fill="white")
            draw.text((0, 20 + 14), "Not heating" if relay == 0 else "Heating...", font=font_small, fill="white")

    def try_print(self, temperature, target_temperature, relay):
        try:
            self.__print(temperature, target_temperature, relay)
        except (OSError, DeviceNotFoundError) as e:
            pass
