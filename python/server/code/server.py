import os
import time
import json

from flask import Flask, request
from flask_restful import Resource, Api
from mongodb import MongoDB

from models import User, Room, Message, LongPoll

app = Flask(__name__)
api = Api(app)

api.app.config['RESTFUL_JSON'] = {'ensure_ascii': False}

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

    current_dict = result[0].to_mongo()
    current_dict.pop("login", None)
    current_dict.pop("password", None)
    return {"result": True, "body": current_dict}


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
    current_dict = new_user.to_mongo()
    current_dict.pop("login", None)
    current_dict.pop("password", None)
    return {"result": True, "body": current_dict}


@app.route("/createRoom", methods=["GET"])
def create_room():
    """
    Создание комнаты для общения пользователей
    - Принимает список id пользователей
    """
    creator_id = request.args.get("creator_id")
    users = request.args.get("users")
    user_key = request.args.get("user_key")
    room_name = request.args.get("name")

    # Фильтрация на поля
    if any([x is None for x in (creator_id, users, user_key)]):
        return {"result": False, "description": "no enough values"}

    # Проверка на авторизацию
    if mongo.get_users({"key": user_key}) == 0:
        return {"result": False, "description": "invalid auth key"}

    # Фильтрация на существование пользователей
    users_list = users.split(",")
    if any([mongo.get_users({"_id": user}) == 0 for user in users_list]):
        return {"result": False, "description": "some users does not exist"}

    # Если создателя нет в списке пользователей комнаты, то добавляем его
    if creator_id not in users_list:
        users_list.append(creator_id)

    new_room = Room(creator_id, users_list, room_name)
    mongo.set_rooms([new_room])
    return {"result": True, "body": new_room.to_mongo()}


@app.route("/getUserRooms", methods=["GET"])
def get_user_rooms():
    """Получение списка комнат, в которых состоит пользователь"""
    user_id = request.args.get("user_id")
    user_key = request.args.get("user_key")

    # Фильтрация на поля
    if any([x is None for x in (user_id, user_key)]):
        return {"result": False, "description": "no enough values"}

    # Проверка на пользователя
    if mongo.get_users({"_id": user_id, "key": user_key}) == 0:
        return {"result": False, "description": "invalid auth key"}

    rooms_list = [room.to_mongo() for room in mongo.get_rooms({"users": user_id})]

    return {"result": True, "body": rooms_list}


@app.route("/getRoomInfo", methods=["GET"])
def get_room_info():
    """Получение """
    room_id = request.args.get("room_id")
    user_key = request.args.get("user_key")

    # Фильтрация на поля
    if any([x is None for x in (room_id, user_key)]):
        return {"result": False, "description": "no enough values"}

    # Проверка на существование ключа
    if mongo.get_users({"key": user_key}) == 0:
        return {"result": False, "description": "invalid auth key"}

    rooms_list = [room for room in mongo.get_rooms({"_id": room_id})]

    #Если комната с указанным id не найдена
    if len(rooms_list) == 0:
        return {"result" : False, "body" : "room not found"}

    room = rooms_list[0]
    return {"result": True, "body": room.to_mongo()}

@app.route("/getRoomMessagesHistory", methods=["GET"])
def get_room_messages_history():
    """Получение истории сообщений оппределенной комнаты"""
    room_id = request.args.get("room_id")
    user_key = request.args.get("user_key")

    # Фильтрация на поля
    if any([x is None for x in (room_id, user_key)]):
        return {"result": False, "description": "no enough values"}

    # Проверка на пользователя
    if mongo.get_users({"key": user_key}) == 0:
        return {"result": False, "description": "invalid auth key"}

    messages_list = [message.to_mongo() for message in mongo.get_messages({"room_id": room_id})]

    return {"result": True, "body": messages_list}

@app.route("/search", methods=["GET"])
def search():
    """Поиск пользователя в системе (для последующего чата)"""
    user_key = request.args.get("user_key")
    search_str = request.args.get("search_str")

    if user_key is None:
        return {"result": False, "description": "no enough values"}

    # Проверка на пользователя
    if mongo.get_users({"key": user_key}) == 0:
        return {"result": False, "description": "invalid auth key"}

    return {"result": True, "body": "SOME_INFO"}


@app.route("/writeMessage", methods=["GET"])
def write_message():
    """
    Отправка сообщения в опеределенную комнату room_id
    """

    user_from = request.args.get("user_from")
    user_key = request.args.get("user_key")
    text = request.args.get("text")
    room_id = request.args.get("room_id")

    # Фильтрация на поля
    if any([x is None for x in (user_from, text, room_id, user_key)]):
        return {"result": False, "description": "no enough values"}

    # Проверка на пользователя
    if mongo.get_users({"_id": user_from, "key": user_key}) == 0:
        return {"result": False, "description": "invalid auth key"}

    # Фильтрация на комнату
    if mongo.get_rooms({"_id": room_id}) == 0:
        return {"result": False, "description": "invalid room id"}

    message = Message(user_from, text, room_id)
    mongo.set_messages([message])
    return {"result": True, "body": message.to_mongo()}


@app.route("/getLongPollServer", methods=["GET"])
def get_longpoll_server():
    """
    Получение данных для лонгпула по определенному user_id и его user_key

    Отдает следующие данные:
    key - ключ лонгпула
    ts - время последнего полученного объекта пользователя
    server - URL, куда стучаться
    """
    user_id = request.args.get("user_id")
    user_key = request.args.get("user_key")

    # Фильтрация на поля
    if any([x is None for x in (user_id, user_key)]):
        return {"result": False, "description": "no enough values"}

    # Проверка на пользователя
    if mongo.get_users({"_id": user_id, "key": user_key}) == 0:
        return {"result": False, "description": "invalid auth key"}

    ts = mongo.get_user_last_message_date(user_id)
    longpoll = LongPoll(user_id, ts)
    # Добавляем в список пулов
    polls_list.append(longpoll)
    # Записываем в БД
    mongo.set_longpolls([longpoll])
    return {"result": True, "body": longpoll.to_mongo()}


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

    # Проверка на существование пула
    current_polls = [poll for poll in polls_list if poll.url == f"LongPoll/{server}"]
    if len(current_polls) != 1:
        return {"result": False, "description": "poll does not exist"}
    current_poll = current_polls[0]

    # Проверка на корректность ключа
    if current_poll.key != key:
        return {"result": False, "description": "invalid key"}

    new_ts = None
    result = None
    while True:
        result = mongo.get_new_messages(current_poll.user_id, ts)
        if len(result) != 0:
            new_ts = result[0]["time_created"]
            break
        time.sleep(0.5)
    return {"result": True, "body": {"updates": result, "ts": new_ts}}


if __name__ == "__main__":
    app.run(host="0.0.0.0", threaded=True, debug=False)
