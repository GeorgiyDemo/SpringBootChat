import requests
from typing import List
import time
from urllib.parse import quote
from faker import Faker

LOGIN  = "demka@mail.ru"
PASSWORD = "3845"
FIREND_ID = "681470287766471aa58b26defd7813a1"

class DEMKACli:

    URL = "http://127.0.0.1:5000"

    @staticmethod
    def registration(name, login, password):
        r = requests.get(f"{DEMKACli.URL}/register?name={name}&login={login}&password={password}")
        print(r.text)

    def __init__(self, login: str, password : str) -> None:
        
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
        r = requests.get(f"{self.URL}/auth?login={LOGIN}&password={PASSWORD}").json()
        print(r)
        if not r["result"]:
            raise ValueError("Не удалось авторизоваться")

        self.user_id = r["user_id"]
        self.user_key = r["key"]
        self.username = r["name"]

    def create_room(self, friends_list : List, name : str = "RandomRoom"):

        room = requests.get(f"{self.URL}/createRoom?creator_id={self.user_id}&users={','.join(friends_list)}&name={name}").json()
        print(room)
        if not room["result"]:
            raise ValueError("Не удалось создать комнату")
        
        self.current_room = room["room_id"]
        room_name = room["name"]
        print(f"Успешно создали комнату {room_name} с id {self.current_room}")

    def write_message(self, message_text : str):
        """Отправка сообщения"""
        url = quote(f"{self.URL}/writeMessage?user_from={self.user_id}&text={message_text}&room_id={self.current_room}",safe='/:?=&')
        message_result = requests.get(url).json()
        if not message_result["result"]:
            raise ValueError(f"Не удалось отправить сообщение '{message_text}' в комнату {self.current_room}")

    def get_longpoll_server(self):
        """Получение лонгпула"""
        poll = requests.get(f"{self.URL}/getLongPollServer?user_id={self.user_id}").json()
        print(poll)
        if not poll["result"]:
            raise ValueError("Не удалось получить начальные данные для лонгпула")

        self.longpoll_ts = poll["ts"]
        self.longpoll_sub_url = poll["url"]
        self.longpoll_key = poll["key"]

        print("Успешно выставили начальные данные для пулинга")

    
    def longpoll_listener(self):
        """Слушатель кароч"""
        while True:
            print("Ждем обновлений кароч..")
            long_request = requests.get(f"{self.URL}/{self.longpoll_sub_url}?key={self.longpoll_key}&ts={self.longpoll_ts}").json()
            print(long_request)
            time.sleep(1)

def main():
    fake = Faker("ru_RU")
    cli = DEMKACli(LOGIN,PASSWORD)
    cli.create_room(FIREND_ID)

    while True:
        word = fake.word()
        print(f"Отправили сообщение {word} в комнату {cli.current_room}..")
        cli.write_message(word)
        time.sleep(10)

main()