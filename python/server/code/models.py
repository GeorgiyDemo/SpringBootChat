import util
from abc import ABCMeta, abstractmethod
from typing import Dict, List


class MongoModel(metaclass=ABCMeta):
    @abstractmethod
    def to_mongo(self) -> Dict:
        """Метод для записи модели в Mongo"""
        pass


class Room(MongoModel):
    """Класс комнаты"""

    def __init__(
        self,
        creator_id: str,
        users: List[str],
        name: str = None,
        time_created: int = None,
        _id: str = None,
    ) -> None:
        self.__creator_id = creator_id
        self.__users = users
        self.__name = name
        self.__id = _id
        self.__time_created = time_created

        if self.__time_created is None:
            self.__time_created = util.current_time()

        if self.__name is None:
            self.__name = "Комната N"

        if self.__id is None:
            self.__id = util.random_string()

    def add_user(self):
        pass

    def to_mongo(self):
        return {
            "_id": self.__id,
            "creator_id": self.__creator_id,
            "name": self.__name,
            "users": self.__users,
            "time_created": self.__time_created,
        }

    @property
    def id(self):
        return self.__id

    @property
    def name(self):
        return self.__name


class User(MongoModel):
    """Класс пользователя"""

    def __init__(
        self,
        name: str,
        login: str,
        password: str,
        key: str = None,
        time_created: int = None,
        _id: str = None,
    ) -> None:
        self.__name = name
        self.__login = login
        self.__password = password
        self.__time_created = time_created
        self.__id = _id

        self.__key = key
        if self.__key is None:
            self.__key = self.generate_key()

        if self.__time_created is None:
            self.__time_created = util.current_time()

        if self.__id is None:
            self.__id = util.random_string()

    def generate_key(self):
        """Генерация ключа доступа"""
        return util.random_string()

    def to_mongo(self):
        return {
            "_id": self.__id,
            "name": self.__name,
            "login": self.__login,
            "password": self.__password,
            "time_created": self.__time_created,
            "key": self.__key,
        }

    @property
    def name(self):
        return self.__name

    @property
    def key(self):
        return self.__key

    @property
    def id(self):
        return self.__id


class Message(MongoModel):
    """Сообщение"""

    def __init__(
        self,
        user_id: str,
        user_name: str,
        text: str,
        room_id: str,
        time_created: int = None,
        _id: str = None,
    ) -> None:
        self.__user_id = user_id
        self.__user_name = user_name
        self.__text = text
        self.__room_id = room_id
        self.__time_created = time_created
        self.__id = _id

        if self.__time_created is None:
            self.__time_created = util.current_time()
        if self.__id is None:
            self.__id = util.random_string()

    def to_mongo(self):
        return {
            "_id": self.__id,
            "user_id": self.__user_id,
            "user_name": self.__user_name,
            "text": self.__text,
            "room_id": self.__room_id,
            "time_created": self.__time_created,
        }


class LongPoll(MongoModel):
    """Экземпляр соединения с лонгпулом"""

    def __init__(self, user_id: str, ts: int, _id: str = None) -> None:
        """
        key - ключ пользователя
        ts - время последнего события, которое получил пользователь
        """
        self.__user_id = user_id
        self.__ts = ts
        self.__key = util.random_string()
        self.__url = "LongPoll/" + util.random_string()
        self.__id = _id

        if self.__id is None:
            self.__id = util.random_string()

    def to_mongo(self):
        return {
            "_id": self.__id,
            "user_id": self.__user_id,
            "ts": self.__ts,
            "key": self.__key,
            "url": self.__url,
        }

    @property
    def url(self):
        return self.__url

    @property
    def user_id(self):
        return self.__user_id

    @property
    def key(self):
        return self.__key


if __name__ == "__main__":
    user = User("demka@mail.ru", "1234")
    r = user.to_mongo()
    print(r)
