# Getting Started

### How to run
* After cloning, please, switch to project folder.
* build image:
```
docker build -t task .
```
* After run next command
```
docker run -p 8080:8080 -t task
```
* Go to http://localhost:8080

###
1. endpoint to get OTP http://localhost:8080/password/{email}
2. endpoint to generate token http://localhost:8080/password/token
3. all CRUD operations based on http://localhost:8080/user
