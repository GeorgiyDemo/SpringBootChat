from typing import Dict, List
import pymongo
from models import User, Room, Message, LongPoolInstance


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

    def get_user_messages(self, user_id: str):
        """Отдает все сообщения, связанные с пользователем"""
        messages_list = []
        # Получаем комнаты, где состоит пользователь
        rooms_table = self.connection["Rooms"]
        rooms_list = rooms_table.find({"users": user_id})

        # По каждой комнате получаем сообщения
        messages_table = self.connection["Messages"]
        for room in rooms_list:
            messages_list.extend(messages_table.find({"room_id": room["_id"]}))
        return messages_list

    def get_user_last_message_date(self, user_id: str):
        """Отдает UNIX-дату последнего сообщения, полученного пользователем"""

        messages_list = self.get_user_messages(user_id)
        # Получаем комнаты, где состоит пользователь
        rooms_table = self.connection["Rooms"]
        rooms_list = rooms_table.find({"users": user_id})

        # По каждой комнате получаем сообщения
        messages_table = self.connection["Messages"]
        for room in rooms_list:
            messages_list.extend(messages_table.find({"room_id": room.id}))

        return messages_list

        table.find({""})

    mongo.get_message({"user_id": ""})
    # def get_messages(self, chat_id, user_id)
    # TODO def get_messages(self )
