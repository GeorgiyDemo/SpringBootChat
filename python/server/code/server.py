import os
import time
import json

from flask import Flask, request
from flask_restful import Resource, Api
from mongodb import MongoDB

from models import User, Room, Message, LongPoll

app = Flask(__name__)
api = Api(app)

mongo = MongoDB("SpringBootChat", os.environ.get("CONNECTION_STRING"))
polls_list = []

@app.route("/auth", methods=["GET"])
def auth():
    """Авторизации пользователя"""
    login = request.args.get("login")
    password = request.args.get("password")

    # Фильтрация на поля
    if any([x is None for x in (login, password)]):
        return {"result": False, "description": "no enough values"}

    result = mongo.get_users({"login": login, "password": password})
    if len(result) != 1:
        return {"result": False}
    
    current_user = result[0]
    return {"result": True, "user_id" : current_user.id, "name" : current_user.name, "key" : current_user.key}


@app.route("/register", methods=["GET"])
def register():
    """Регистрация пользователя"""
    # Получение переданных параметров
    name = request.args.get("name")
    login = request.args.get("login")
    password = request.args.get("password")
    # Создание нового пользователя
    new_user = User(name, login, password)
    # Запись в БД
    mongo.set_users([new_user])
    return {"result": True}


@app.route("/createRoom", methods=["GET"])
def create_room():
    """
    Создание комнаты для общения пользователей
    - Принимает список id пользователей
    """
    creator_id = request.args.get("creator_id")
    name = request.args.get("name")
    users = request.args.get("users")

    # Фильтрация на поля
    if any([x is None for x in (creator_id, users)]):
        return {"result": False, "description": "no enough values"}

    # Фильтрация на существование пользователей
    users_list = users.split(",")
    if any([mongo.get_users({"_id": user}) == 0 for user in users_list]):
        return {"result": False, "description": "some users does not exist"}

    #Если создателя нет в списке пользователей комнаты, то добавляем его
    if creator_id not in users_list:
        users_list.append(creator_id)

    new_room = Room(creator_id, users_list, name)
    mongo.set_rooms([new_room])
    return {"result": True, "room_id" : new_room.id, "name" : new_room.name}


@app.route("/getUserRooms", methods=["GET"])
def get_user_rooms():
    """Получение списка комнат, в которых состоит пользователь"""
    user_id = request.args.get("user_id")
    if user_id is None:
        return {"result": False, "description": "no enough values"}
    result = mongo.get_rooms({"users": user_id})
    return json.dumps([room.to_mongo() for room in result], ensure_ascii=False)


# TODO
@app.route("/search", methods=["GET"])
def search():
    """Поиск пользователя в системе (для последующего чата)"""
    return {"result": True}


# TODO
@app.route("/writeMessage", methods=["GET"])
def write_message():
    """
    Отправка сообщения в опеределенную комнату room_id
    """

    user_from = request.args.get("user_from")
    text = request.args.get("text")
    room_id = request.args.get("room_id")

    # Фильтрация на поля
    if any([x is None for x in (user_from, text, room_id)]):
        return {"result": False, "description": "no enough values"}

    # TODO Фильтрация на пользователя

    # TODO Фильтрация на комнату

    message = Message(user_from, text, room_id)
    mongo.set_messages([message])
    return {"result": True}


@app.route("/getLongPollServer", methods=["GET"])
def get_longpoll_server():
    """
    Получение данных для лонгпула по определенному user_id

    Отдает следующие данные:
    key - ключ пользователя
    ts - время последнего полученного объекта пользователя
    server - URL, куда стучаться
    """
    user_id = request.args.get("user_id")
    # Если нет переданного поля
    if user_id is None:
        return {"result": False, "description": "no enough values"}

    # Если пользователя не существует
    if mongo.get_users({"_id": user_id}) == 0:
        return {"result": False, "description": "user does not exist"}

    ts = mongo.get_user_last_message_date(user_id)
    longpoll = LongPoll(user_id,ts)
    #Добавляем в список пулов
    polls_list.append(longpoll)
    #Записываем в БД    
    mongo.set_longpolls([longpoll])
    
    return_dict = longpoll.to_mongo()
    return_dict.update({"result" : True})

    return return_dict


# АЛГОРИТМ
# 1) Получаем в get_longpoll_server UNIX-дату ts последнего сообщения, адресованного пользователю
# 2) Передаем ts в longpoll. Метод сравнивает даты. Если есть Сообщения с более новой датой - это новые сообщения
# 3) Метод longpoll возвращает обновленный ts и результат сообщений
@app.route("/LongPoll/<server>", methods=["GET"])
def longpoll(server):
    print(f"Перешли по server = {server}")
    
    key = request.args.get("key")
    ts = request.args.get("ts")

    # Фильтрация на поля
    if any([x is None for x in (key, ts)]):
        return {"result": False, "description": "no enough values"}

    #Проверка на существование пула
    current_polls = [poll for poll in polls_list if poll.url == f"LongPoll/{server}"]
    if len(current_polls) != 1:
        return {"result": False, "description": "poll does not exist"}
    current_poll = current_polls[0]

    #Проверка на корректность ключа
    if current_poll.key != key:
        return {"result": False, "description": "invalid key"}

    new_ts = None
    result = None
    while True:
        result = mongo.get_new_messages(current_poll.user_id, ts)
        if len(result) !=0:
            new_ts = result[0]["time_created"]
            break
        time.sleep(0.5)
    return {"result" : True, "body" : {"updates" : result, "ts" : new_ts}}

if __name__ == "__main__":
    app.run(host="0.0.0.0", threaded=True, debug=False)
