# HTTP 1.1 Server

The server runs on port 8081 and supports `GET, POST` and `OPTIONS` methods. Could be tested via Postman, any browser 
and simply in terminal with `curl` command. Also server supports reading of `png` images. All info is logged and shown 
in console.

**Note** In case of request any other method except three above return 405 status code and corresponding message.

* GET (curl http://localhost:8081/filename.ext)

    **Note** Returns default `index.html` page in case no file specified. In case file not found 404 status code and 
    corresponding message.
    
* POST (curl -i -X POST http://localhost:8081/filename.ext)
 
    **Note** By default has `file.txt` as request body and returns content of this file in response body.

* OPTIONS (curl -i -X OPTIONS http://localhost:8081/filename.ext)

    **Note** Returns empty body and headers which contains information about server.