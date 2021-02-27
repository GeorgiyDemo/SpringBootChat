import random
import time
from faker import Faker
from demka_cli import DEMKACli

LOGIN = "demka@mail.ru"
PASSWORD = "3845"
FIRENDS_LIST = ["681470287766471aa58b26defd7813a1"]


def main():
    fake = Faker("ru_RU")
    cli = DEMKACli(LOGIN, PASSWORD)
    cli.create_room(FIRENDS_LIST)

    while True:
        word = f"{fake.word()} {fake.word()} {fake.word()}"
        print(f"Отправили сообщение '{word}' в комнату {cli.current_room}..")
        cli.write_message(word)
        time.sleep(random.randint(1, 5))

        # for room in cli.get_user_rooms():
        #    print(room["_id"])


if __name__ == "__main__":
    main()
