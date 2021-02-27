from demka_cli import DEMKACli

LOGIN = "mike@mail.ru"
PASSWORD = "436436"
FIRENDS_LIST = ["681470287766471aa58b26defd7813a1"]


def main():
    cli = DEMKACli(LOGIN, PASSWORD)
    cli.get_longpoll_server()
    cli.longpoll_listener()


if __name__ == "__main__":
    main()
