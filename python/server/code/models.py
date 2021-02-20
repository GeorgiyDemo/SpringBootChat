class Room:
    """Класс комнаты"""
    def __init__(self) -> None:
        pass


class User:
    """Класс пользователя"""
    def __init__(self) -> None:
        pass

class Message:
    """Сообщение"""
    def __init__(self) -> None:
        pass

class LongPoolInstance:
    """Экземпляр соединения с лонгпулом"""
    def __init__(self, key : str, ts : int) -> None:
        """
        key - ключ пользователя
        ts - id последнего события, которое надо получить
        """
        self.__url = None 
        pass

    def generate_url(self):
        """Генерация URL"""
        self.__url = 


    @property
    def url(self):
        pass
