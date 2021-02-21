from typing import Dict, List
import pymongo
from models import User, Room, Message, LongPoll


class MongoDB:
    def __init__(self, db_name: str, connection: str) -> None:
        myclient = pymongo.MongoClient(connection)
        self.connection = myclient[db_name]

    def get_users(self, condition: Dict = None) -> List[User]:
        """Получение пользователей"""
        table = self.connection["Users"]
        result = table.find() if condition is None else table.find(condition)
        return [User(**item) for item in result]

    def set_users(self, users_list: List[User]):
        """Запись пользователей"""
        # TODO ЕСЛИ ПОЛЬЗОВАТЕЛЬ С ТАКИМ _id существует, то обновить его данные
        table = self.connection["Users"]
        for user in users_list:
            table.insert_one(user.to_mongo())

    def get_rooms(self, condition: Dict = None) -> List[Room]:
        """Получение комнат"""
        table = self.connection["Rooms"]
        return [Room(**item) for item in table.find(condition)]

    def set_rooms(self, rooms_list: List[Room]):
        """Запись комнат"""
        # TODO ЕСЛИ КОМНАТА С ТАКИМ _id существует, то обновить её данные
        table = self.connection["Rooms"]
        for room in rooms_list:
            table.insert_one(room.to_mongo())

    def get_messages(self, condition: Dict = None) -> List[Message]:
        """Получение сообщений"""
        table = self.connection["Messages"]
        return [Message(**item) for item in table.find(condition)]

    def set_messages(self, messages_list: List[Message]):
        """Запись сообщений"""
        table = self.connection["Messages"]
        for message in messages_list:
            table.insert_one(message.to_mongo())

    def get_longpolls(self, condition: Dict = None) -> List[LongPoll]:
        """Получение инфы о лонгпуле"""
        table = self.connection["LongPolls"]
        return [LongPoll(**item) for item in table.find(condition)]

    def set_longpolls(self, longpolls_list: List[Message]):
        """Запись инфы о лонгпулах"""
        table = self.connection["LongPolls"]
        for poll in longpolls_list:
            table.insert_one(poll.to_mongo())

    def get_user_rooms(self, user_id : str) -> List:
        """Отдает список комнат, в которых состоит пользователь"""
        # Получаем комнаты, где состоит пользователь
        rooms_table = self.connection["Rooms"]
        return rooms_table.find({"users": user_id})

    def get_user_messages(self, user_id: str) -> List:
        """Отдает все сообщения, связанные с пользователем"""
       
        rooms_list = self.get_user_rooms(user_id)
        messages_list = []
        # По каждой комнате получаем сообщения
        messages_table = self.connection["Messages"]
        for room in rooms_list:
            messages_list.extend(messages_table.find({"room_id": room["_id"]}))
        return messages_list

    def get_user_last_message_date(self, user_id: str):
        """Отдает UNIX-дату последнего сообщения, полученного пользователем"""

        messages_list = self.get_user_messages(user_id)
        messages_list.sort(key=lambda x: x["time_created"], reverse=True)
        print("print(messages_list)")
        print(messages_list)
        if len(messages_list) == 0:
            raise ValueError("Невозможно намутить лонгпул без сообщений!")
        return messages_list[0]["time_created"]

    def get_new_messages(self, user_id : str, ts : int):
        """Получает новые сообщения для пользователя"""

        # По каждой комнате получаем сообщения
        messages_list = []
        messages_table = self.connection["Messages"]
        for room in self.get_user_rooms(user_id):
            messages_list.extend(messages_table.find({"room_id": room["_id"], "time_created": { "$gt": ts }}))
        return messages_list