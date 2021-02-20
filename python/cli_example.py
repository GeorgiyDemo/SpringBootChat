import requests

URL = "http://127.0.0.1:5000"
r = requests.get(URL+"/auth?login=demka@mail.ru&password=3845")
print(r.text)

#r = requests.get(URL+"/register?name=georgiydemo&login=demka@mail.ru&password=3845")
#print(r.text)

r = requests.get(URL+"/auth?login=demka@mail.ru&password=3845")
print(r.text)

#r = requests.get(URL+"/createRoom?creator_id=362d020584264aea8d560088365177b6&users=bb6c68c9d3e04424beb3abffd43f49ba,9ab8f5de4af747888b2144a65d4b953a,362d020584264aea8d560088365177b6")
#print(r.text)

result = requests.get(URL+"/getUserRooms?user_id=9ab8f5de4af747888b2144a65d4b953a").json()
for item in result:
    print(item)