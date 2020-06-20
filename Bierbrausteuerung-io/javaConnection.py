from py4j.java_gateway import JavaGateway
from py4j.protocol import Py4JNetworkError


class JavaConnection:
    def __init__(self):
        self.gateway = None

    def __connect(self):
        self.gateway = JavaGateway()

    def __check_connection_status(self):
        if self.gateway is None:
            self.__connect()

    def getState(self, current_temperature):
        try:
            self.__check_connection_status()

            entry_point = self.gateway.entry_point
            target_state = entry_point.getState(current_temperature)
            return target_state
        except Py4JNetworkError:
            self.gateway = None
            return None
