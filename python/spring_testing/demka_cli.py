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
        room = requests.get(
            f"{self.URL}/createRoom?creator_id={self.user_id}&users={','.join(friends_list)}&name={name}&user_key={self.user_key}"
        ).json()
        print(room)
        if not room["result"]:
            raise ValueError("Не удалось создать комнату")

        self.current_room = room["body"]["_id"]
        room_name = room["body"]["name"]
        print(f"Успешно создали комнату {room_name} с id {self.current_room}")

    def write_message(self, message_text: str):
        """Отправка сообщения в комнату"""
        url = quote(
            f"{self.URL}/writeMessage?user_from={self.user_id}&text={message_text}&room_id={self.current_room}&user_key={self.user_key}",
            safe="/:?=&",
        )
        message_result = requests.get(url).json()
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
            f"{self.URL}/getLongPollServer?user_id={self.user_id}&user_key={self.user_key}"
        ).json()
        print(poll)
        if not poll["result"]:
            raise ValueError("Не удалось получить начальные данные для лонгпула")

        credentials = poll["body"]
        self.longpoll_ts = credentials["ts"]
        self.longpoll_sub_url = credentials["url"]
        self.longpoll_key = credentials["key"]
        print("Успешно выставили начальные данные для пулинга")

    def get_user_rooms(self):
        """Получение списка комнат пользователя"""
        rooms = requests.get(
            f"{self.URL}/getUserRooms?user_id={self.user_id}&user_key={self.user_key}"
        ).json()
        print(rooms)
        if not rooms["result"]:
            raise ValueError(
                f"Не удалось получить список комнат для пользователя {self.user_id}"
            )
        return rooms["body"]

    def longpoll_listener(self):
        """Слушатель лонгпула"""
        while True:
            print("Ждем обновлений кароч..")
            long_request = requests.get(
                f"{self.URL}/{self.longpoll_sub_url}?key={self.longpoll_key}&ts={self.longpoll_ts}"
            ).json()
            print(long_request)
            if not long_request["result"]:
                raise ValueError("Что-то не так с лонгпулом")

            self.longpoll_ts = long_request["body"]["ts"]
            new_messages_list = long_request["body"]["updates"]
            for message in new_messages_list:
                print(f"[USER #{message['user_from']}] -> {message['text']}")
            time.sleep(1)

    def search(self, search_str: str = None):
        """Поиск пользователей"""
        if search_str is not None:
            url = quote(
                f"{self.URL}/search?user_key={self.user_key}&search_str={search_str}",
                safe="/:?=&",
            )
        else:
            url = quote(f"{self.URL}/search?user_key={self.user_key}", safe="/:?=&")
        message_result = requests.get(url).json()
        print(message_result)
        if not message_result["result"]:
            raise ValueError(f"Не удалось получить список пользователей")


if __name__ == "__main__":
    print(
        "Вы наверное подумали что я клиент, но я не клиент я класс не запускайте меня"
    )
