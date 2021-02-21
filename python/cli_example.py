import requests

URL = "http://127.0.0.1:5000"
r = requests.get(URL + "/auth?login=demka@mail.ru&password=3844")
print(r.text)

#r = requests.get(URL+"/register?name=georgiydemo&login=demka@mail.ru&password=3845")
print(r.text)
#r = requests.get(URL+"/register?name=mike&login=mike@mail.ru&password=436436")
print(r.text)

r = requests.get(URL + "/auth?login=demka@mail.ru&password=3845")
print(r.text)
r = requests.get(URL + "/auth?login=mike@mail.ru&password=436436")
print(r.text)

#r = requests.get(URL+"/createRoom?creator_id=903ccfc0d939478bbcd31e8f95ca87ad&users=cfd618b0cbf14d728b8403d3d3398127")
#print(r.text)

#r = requests.get(URL+"/writeMessage?user_from=903ccfc0d939478bbcd31e8f95ca87ad&text=MEOW1&room_id=2ffeef75d7d04213ba67fec8d6c6f158")
#r = requests.get(URL+"/writeMessage?user_from=681470287766471aa58b26defd7813a1&text=MEOW2&room_id=2ffeef75d7d04213ba67fec8d6c6f158")
#result = requests.get(
#    URL + "/getUserRooms?user_id=9ab8f5de4af747888b2144a65d4b953a"
#).json()
#for item in result:
#    print(item)

r = requests.get(URL+"/getLongPollServer?user_id=681470287766471aa58b26defd7813a1")
print(r.text)
poll = r.json()

ts, url, key = poll["ts"], poll["url"], poll["key"]
print(ts)
print(url)
print(key)
