import random
import time
from faker import Faker
from demka_cli import DEMKACli

LOGIN = "demka@mail.ru"
PASSWORD = "3845"
FIRENDS_LIST = ["acee13e52aa94e1692320dc2aec8ce1f"]
ROOM_ID = "9b9561a77c5444528ec558291f470592"


def main():
    #DEMKACli.registration("alexey", "alexey@mail.ru", "alexey")
    fake = Faker("ru_RU")
    cli = DEMKACli(LOGIN, PASSWORD)
    cli.create_room(FIRENDS_LIST, "САМАЯ ВАЖНАЯ КОМНАТА")
    #cli.current_room = ROOM_ID

    while True:
        word = f"{fake.word()} {fake.word()} {fake.word()}"
        print(f"Отправили сообщение '{word}' в комнату {cli.current_room}..")
        cli.write_message(word)
        #cli.search("geor")
        #cli.search()
        #cli.search("mi")
        time.sleep(random.randint(1, 5))

        #for room in cli.get_user_rooms():
        #    print(room["_id"])


if __name__ == "__main__":
    main()
