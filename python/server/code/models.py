import util
from abc import ABCMeta, abstractmethod
from typing import Dict, List
import datetime

class MongoModel(metaclass=ABCMeta):

    @abstractmethod
    def to_mongo(self) -> Dict:
        """Метод для записи модели в Mongo"""
        pass

class Room(MongoModel):
    """Класс комнаты"""
    def __init__(self, creator_id : str, users : List[str], name : str = None, _id : str = None) -> None:
        self.__creator_id = creator_id
        self.__users = users
        self.__name = name
        self.__id = _id
        if self.__name is None:
            self.__name = "Комната N"
        
        if self.__id is None:
            self.__id = util.random_string()

    def add_user(self):
        pass

    def to_mongo(self):
        return {"_id" : self.__id, "creator_id" : self.__creator_id, "name" : self.__name, "users" : self.__users}


class User(MongoModel):
    """Класс пользователя"""
    def __init__(self, name : str, login : str, password : str, key : str = None, _id : str = None) -> None:
        self.__name = name
        self.__login = login
        self.__password = password
        self.__id = _id

        self.__key = key
        if self.__key is None:
            self.__key = self.generate_key()

        if self.__id is None:
            self.__id = util.random_string()
    
    def generate_key(self):
        """Генерация ключа доступа"""
        return util.random_string()
    
    def to_mongo(self):
        return {"_id" : self.__id, "name" : self.__name, "login" : self.__login, "password" : self.__password, "key" : self.__key}

class Message(MongoModel):
    """Сообщение"""
    def __init__(self, from : str, text : str, room_id : str, time_created : datetime.datetime = datetime.datetime.now(), _id : str = None) -> None:
        
        if self.__id is None:
            self.__id = util.random_string()

    def to_mongo(self):
        return {}

class LongPoolInstance(MongoModel):
    """Экземпляр соединения с лонгпулом"""
    def __init__(self, user_id: int, _id : str = None) -> None:
        """
        key - ключ пользователя
        ts - id последнего события, которое надо получить
        """
        self.__user_id = user_id
        self.__key = key
        self.__ts = ts
        self.__url = self.generate_url() 

    def generate_url(self):
        """Генерация URL"""
        return "LongPoll/"+util.random_string(32)

    def to_mongo(self):
        return {}
    @property
    def url(self):
        return self.__url


if __name__ == "__main__":
    user = User("demka@mail.ru","1234")
    r = user.to_mongo()
    print(r)