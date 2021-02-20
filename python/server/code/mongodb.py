from typing import Dict, List
import pymongo
from models import *

class MongoDB:
    def __init__(self, db_name : str, connection : str ) -> None:
        myclient = pymongo.MongoClient(connection)
        self.connection = myclient[db_name]

    def get_users(self, condition : Dict = None) -> List[User]:
        """Получение пользователей"""
        table = self.connection["Users"]
        result = table.find() if condition is None else table.find(condition)
        return [User(**item) for item in result]

    def set_users(self, users_list : List[User]):
        """Запись пользователей"""
        #TODO ЕСЛИ ПОЛЬЗОВАТЕЛЬ С ТАКИМ _id существует, то обновить его данные
        table = self.connection["Users"]
        for user in users_list:
            table.insert_one(user.to_mongo())

    def get_rooms(self, condition : Dict = None) -> List[Room]:
        """Получение комнат для опеределенного user_id"""
        table = self.connection["Rooms"]
        return [Room(**item) for item in table.find(condition)]

    def set_rooms(self, rooms_list : List[Room]):
        """Запись комнат в БД"""
        #TODO ЕСЛИ КОМНАТА С ТАКИМ _id существует, то обновить её данные
        table = self.connection["Rooms"]
        for room in rooms_list:
            table.insert_one(room.to_mongo())

    #TODO def get_messages(self )