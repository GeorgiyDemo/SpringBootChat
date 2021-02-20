import uuid


def random_string() -> str:
    """Генерация рандомной строки"""
    return str(uuid.uuid4()).replace("-", "")
