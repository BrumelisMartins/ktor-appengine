# DB structure


![image](https://user-images.githubusercontent.com/45454489/150199085-288f03cb-2ea7-44cf-b6d9-5ee8d39168f7.png)

# The database is a postgres DB created with KTOR. It is hosted on google cloud with google App Engine and consists of several API calls. Authorization is by Username and password in the header.

# It is used to store data and for the bots to communicate between themselves, there are multiple types of users, for example a Mule would be collecting the ingame currency from all active bot workers. When a workder has a certain amount of resources it would send a task to the API. The mule would be checking sitting idle, checking after a set perioud if it needs to do any collections.
