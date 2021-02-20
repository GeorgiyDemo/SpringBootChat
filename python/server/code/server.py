import os
import time
import json

from flask import Flask, request
from flask_restful import Resource, Api
from mongodb import MongoDB

from models import User, Room
app = Flask(__name__)
api = Api(app)

mongo = MongoDB("SpringBootChat", os.environ.get("CONNECTION_STRING"))

@app.route("/auth", methods=["GET"])
def auth():
    """Авторизации пользователя"""
    login = request.args.get("login")
    password = request.args.get("password")

    #Фильтрация на поля
    if any([x is None for x in (login, password)]):
        return {"result" : False, "description" : "no enough values"}

    result = mongo.get_users({"login" : login, "password" : password})
    return {"result" : True} if len(result) != 0 else {"result" : False}

@app.route("/register", methods=["GET"])
def register():
    """Регистрация пользователя"""
    #Получение переданных параметров
    name = request.args.get("name")
    login = request.args.get("login")
    password = request.args.get("password")
    #Создание нового пользователя
    new_user = User(name, login, password)
    #Запись в БД
    mongo.set_users([new_user])
    return {"result" : True}

@app.route("/createRoom", methods=["GET"])
def create_room():
    """
    Создание комнаты для общения пользователей
    - Принимает список id пользователей
    """
    creator_id = request.args.get("creator_id")
    name = request.args.get("name")
    users = request.args.get("users")
    #Фильтрация на поля
    if any([x is None for x in (creator_id, users)]):
        return {"result" : False, "description" : "no enough values"}
    #Фильтрация на существование пользователей
    users_list = users.split(",")
    if any([mongo.get_users({"_id" : user}) == 0 for user in users_list]):
        return {"result" : False, "description" : "some users does not exist"}

    new_room = Room(creator_id, users_list, name)
    mongo.set_rooms([new_room])

    return {"result" : True}

@app.route("/getUserRooms", methods=["GET"])
def get_user_rooms():
    """Получение списка комнат, в которых состоит пользователь"""
    user_id = request.args.get("user_id")
    if user_id is None:
        return {"result" : False, "description" : "no enough values"}
    result = mongo.get_rooms({"users" : user_id})
    return json.dumps([room.to_mongo() for room in result],ensure_ascii=False)

#TODO
@app.route("/search", methods=["GET"])
def search():
    """Поиск пользователя в системе (для последующего чата)"""
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
    """
    Получение данных для лонгпула по определенному user_id

    Отдает следующие данные:
    key - ключ пользователя
    ts - последний полученный объект пользователя
    server - URL, куда стучаться
    """

    #Проверка, что существует такой ключ и последний объект
    return "URL"

@app.route("/LongPoll/<server>", methods=["GET"])
def longpoll(server):
    print(f"Перешли по server = {server}")

    key = request.form.get("key")
    ts = request.form.get("ts")

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