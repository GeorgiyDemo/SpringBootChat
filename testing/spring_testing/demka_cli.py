import requests
from typing import List
import time
from urllib.parse import quote


class DEMKACli:

    URL = "http://127.0.0.1:8080"

    @staticmethod
    def registration(username, login, password):
        """Регистрация пользователя"""
        r = requests.post(
            f"{DEMKACli.URL}/user/register", json={"login" : login, "password" : password, "username" : username}
        )
        print(r.text)

    def __init__(self, login: str, password: str) -> None:

        self.username = None
        self.user_key = None
        self.user_id = None
        self.current_room = None

        self.longpoll_ts = None
        self.longpoll_sub_url = None
        self.longpoll_key = None

        self.auth(login, password)
        print(f"Авторизация пользователя {self.username} прошла успешно")

    def auth(self, login, passwd):
        """Авторизация пользователя"""
        r = requests.get(f"{self.URL}/user/auth?login={login}&password={passwd}").json()
        print(r)
        if not r["result"]:
            raise ValueError("Не удалось авторизоваться")

        body = r["body"]
        self.user_id = body["id"]
        self.user_key = body["key"]
        self.username = body["name"]

    def create_room(self, friends_list: List, name: str = "RandomRoom"):
        """Создание комнаты для общения"""
        room = requests.post(
            f"{self.URL}/room/create",json={"users": ','.join(friends_list), "roomName" : name, "key" : self.user_key }
        ).json()
        print(room)
        if not room["result"]:
            raise ValueError("Не удалось создать комнату")

        self.current_room = room["body"]["id"]
        room_name = room["body"]["name"]
        print(f"Успешно создали комнату {room_name} с id {self.current_room}")

    def write_message(self, message_text: str):
        """Отправка сообщения в комнату"""
        message_result = requests.post(f"{self.URL}/messages/send", json={"key" : self.user_key, "text" : message_text , "roomId" : self.current_room}).json()
        print(message_result)
        if not message_result["result"]:
            raise ValueError(
                f"Не удалось отправить сообщение '{message_text}' в комнату {self.current_room}"
            )
        print("writeMessage.body:")
        print(message_result["body"])

    def get_longpoll_server(self):
        """Получение лонгпула"""
        poll = requests.get(
            f"{self.URL}/longpoll/getServer?key={self.user_key}"
        )
        print(poll.status_code)
        poll = poll.json()
        print(poll)
        if not poll["result"]:
            raise ValueError("Не удалось получить начальные данные для лонгпула")

        credentials = poll["body"]
        self.longpoll_ts = credentials["ts"]
        self.longpoll_sub_url = credentials["url"]
        self.longpoll_key = credentials["key"]
        print("Успешно выставили начальные данные для пулинга")

    def longpoll_listener(self):
        """Слушатель лонгпула"""
        while True:
            print("Ждем обновлений кароч..")
            long_request = requests.get(
                f"{self.URL}/longpoll/updates/{self.longpoll_sub_url}?key={self.longpoll_key}&ts={self.longpoll_ts}"
            ).json()
            print(long_request)
            if not long_request["result"]:
                raise ValueError("Что-то не так с лонгпулом")

            self.longpoll_ts = long_request["body"]["ts"]
            new_messages_list = long_request["body"]["updates"]
            for message in new_messages_list:
                print(f"[USER #{message['userId']}] -> {message['text']}")
            time.sleep(1)

    def search(self, search_str: str = None):
        """Поиск пользователей"""
        if search_str is not None:
            url = quote(
                f"{self.URL}/user/search?key={self.user_key}&searchName={search_str}",
                safe="/:?=&",
            )
        else:
            url = quote(f"{self.URL}/user/search?key={self.user_key}", safe="/:?=&")

        print(url)
        message_result = requests.get(url).json()
        print(message_result)
        if not message_result["result"]:
            raise ValueError(f"Не удалось получить список пользователей")

    def get_user_rooms(self):
        """Получение списка комнат пользователя"""
        rooms = requests.get(
            f"{self.URL}/room/getByUser?key={self.user_key}"
        ).json()
        print(rooms)
        if not rooms["result"]:
            raise ValueError(
                f"Не удалось получить список комнат для пользователя {self.user_id}"
            )
        return rooms["body"]

if __name__ == "__main__":
    print(
        "Вы наверное подумали что я клиент, но я не клиент я класс не запускайте меня"
    )
