import random
import time
from faker import Faker
from demka_cli import DEMKACli

LOGIN = "mike@mail.ru"
PASSWORD = "436436"
FIRENDS_LIST = ["903ccfc0d939478bbcd31e8f95ca87ad"]
ROOM_ID = "9b9561a77c5444528ec558291f470592"


def main():
    fake = Faker("ru_RU")
    cli = DEMKACli(LOGIN, PASSWORD)
    cli.create_room(FIRENDS_LIST)
    # cli.current_room = ROOM_ID

    while True:
        word = f"{fake.word()} {fake.word()} {fake.word()}"
        print(f"Отправили сообщение '{word}' в комнату {cli.current_room}..")
        cli.write_message(word)
        time.sleep(random.randint(1, 5))

        # for room in cli.get_user_rooms():
        #    print(room["_id"])


if __name__ == "__main__":
    main()
