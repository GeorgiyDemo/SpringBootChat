from demka_cli import DEMKACli

LOGIN = "mike@mail.ru"
PASSWORD = "436436"
FIRENDS_LIST = ["903ccfc0d939478bbcd31e8f95ca87ad"]


def main():
    cli = DEMKACli(LOGIN, PASSWORD)
    cli.get_longpoll_server()
    cli.longpoll_listener()


if __name__ == "__main__":
    main()
