import random
import time
from faker import Faker
from demka_cli import DEMKACli

LOGIN = "mike@mail.ru"
PASSWORD = "436436"
FIRENDS_LIST = ["681470287766471aa58b26defd7813a1"]
ROOM_ID = "9b9561a77c5444528ec558291f470592"


def main():
    DEMKACli.registration("alexey", "alexey@mail.ru", "alexey")
    fake = Faker("ru_RU")
    cli = DEMKACli(LOGIN, PASSWORD)
    # cli.create_room(FIRENDS_LIST)
    cli.current_room = ROOM_ID

    while True:
        #word = f"{fake.word()} {fake.word()} {fake.word()}"
        #print(f"Отправили сообщение '{word}' в комнату {cli.current_room}..")
        #cli.write_message(word)
        cli.search("geor")
        cli.search()
        cli.search("mi")
        time.sleep(random.randint(1, 5))

        #for room in cli.get_user_rooms():
        #    print(room["_id"])


if __name__ == "__main__":
    main()
