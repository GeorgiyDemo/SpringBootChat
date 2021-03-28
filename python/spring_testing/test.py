import requests
URL = "http://127.0.0.1:8080/"

#Авторизация
r = requests.get(f"{URL}/user/auth?login=meow&password=meow").json()
print(r)
print(r["result"])
user_key = r["body"]["key"]
user_id = r["body"]["id"]

#Создание комнаты
r = requests.post(f"{URL}/room/create", json={"room_name" : "SUPER TEST ROOM", "users" : "903ccfc0d939478bbcd31e8f95ca87ad", "key" : user_key}).json()
print(r["result"])
room_id = r["body"]["id"]

#Получение истории сообщений комнаты
r = requests.get(f"{URL}/messages/get?key={user_key}&roomId={room_id}").json()
print(r["body"])

#Отправка сообщения в комнату
r = requests.post(f"{URL}/messages/send", json={"key" : user_key, "text" : "Тестовое сообщение", "room_id" : room_id}).json()
print(r["result"])

#Получение истории сообщений комнаты v2
r = requests.get(f"{URL}/messages/get?key={user_key}&roomId={room_id}").json()
print(r["body"])