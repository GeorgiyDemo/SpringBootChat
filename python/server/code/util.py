import uuid
import datetime

def random_string() -> str:
    """Генерация рандомной строки"""
    return str(uuid.uuid4()).replace("-", "")


def current_time() -> int:
    return int(datetime.datetime.utcnow().timestamp())
