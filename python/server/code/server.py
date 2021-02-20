"""
FLASK REST API для RaspiBox
"""

#Относится к Websocketам и соединениям
#TODO СОЗДАНИЕ СОЕДИНЕНИЯ МЕЖДУ ПОЛЬЗОВАТЕЛЯМИ?
#TODO Добавление метода авторизации клиента в список соединений (?)

import pymongo
import os
import time

from flask import Flask, request
from flask_restful import Resource, Api

app = Flask(__name__)
api = Api(app)
myclient = pymongo.MongoClient(os.environ.get("CONNECTION_STRING"))
mongo = myclient["SpringBootChat"]

longpoll_list = []

#TODO
@app.route("/auth", methods=["GET"])
def auth():
    """Авторизации пользователя"""
    return {"result" : True}

#TODO
@app.route("/register", methods=["GET"])
def register():
    """Регистрация пользователя"""
    return {"result" : True}

#TODO
@app.route("/search", methods=["GET"])
def search():
    """Поиск пользователя в системе (для последующего чата)"""
    return {"result" : True}

#TODO
@app.route("/getUserRooms", methods=["GET"])
def get_user_rooms():
    """Получение списка комнат пользователя"""
    return {"result" : True}

#TODO
@app.route("/createRoom", methods=["GET"])
def create_room():
    """
    Создание комнаты для общения пользователей
    - Принимает список id пользователей
    """
    return {"result" : True}

#TODO
@app.route("/writeMessage", methods=["GET"])
def write_message():
    """
    Отправка сообщения в опеределенную комнату room_id
    """
    #TODO room_id
    return {"result" : True}

#TODO LongPollServer
#TODO server, key, ts
@app.route("/getLongPollServer", methods=["GET"])
def get_longpoll_server():
    ts
    """
    Получение URL для лонгпула по определенному user_id
    """

    return URL
    pass

@app.route("/LongPoll/<key>", methods=["GET"])
def longpoll(key):

    request_time = time.time()
    while not self._is_updated(request_time):
        time.sleep(0.5)
    content = ''
    with open('data.txt') as data:
        content = data.read()
    return {'content': content,
            'date': datetime.now().strftime('%Y/%m/%d %H:%M:%S')}
    

if __name__ == "__main__":
    app.run(host="0.0.0.0",threaded=True, debug=False)