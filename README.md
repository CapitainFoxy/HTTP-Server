<h1 align="center">HTTP Server</h1>

![kep](public/java-400.jpg)

Hello! This is a simple HTTP Java server that I wrote out of boredom. If you want to try it, start the server and then use this commands: 
```
curl http://localhost:1111
```

You should get this message back:
```
Hello from the Server!
```




```
curl http://localhost:1111/json

```

You should get this message back:
```
{"message": "Hello, this is a JSON response!"}
```



```
curl http://localhost:1111/index.html
```

You should get this message back:
```
HTTP/1.1 404 Not Found
```



This is your default server! Enjoy this! ;)
