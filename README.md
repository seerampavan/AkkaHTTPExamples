# AKKA HTTP services with secured(with custom directive) and non secured REST services, along with testcases


## Start the services by calling class - com.akka.utils.ServerUtil
    
    curl -H "Content-Type: application/json" -X POST http://localhost:9000/item -d "{\"id\":\"id_1\", \"name\":\"name_$index\", \"value\":1, \"orderId\":\"orderId_1\"}"
    curl -H "Content-Type: application/json" -X GET http://localhost:9000/item 
    curl -H "Content-Type: application/json" -X PUT http://localhost:9000/item -d "{\"id\":\"id_1\", \"name\":\"name_1_updated\", \"value\":200, \"orderId\":\"orderId_1_updated\"}"
    curl -H "Content-Type: application/json" -X DELETE http://localhost:9000/item -d "{\"id\":\"id_1\", \"name\":\"name_1_updated\", \"value\":200, \"orderId\":\"orderId_1_updated\"}"
    curl -H "Content-Type: application/json" -X GET http://localhost:9000/item 
    
## Non secured service output 
    pavan@pavan:~$ curl -H "Content-Type: application/json" -X POST http://localhost:9000/item -d "{\"id\":\"id_1\", \"name\":\"name_$index\", \"value\":1, \"orderId\":\"orderId_1\"}"
    {
      "id": "id_1",
      "name": "name_1",
      "value": 1,
      "orderId": "orderId_1"
    }
    pavan@pavan:~$ curl -H "Content-Type: application/json" -X GET http://localhost:9000/item 
    {
      "items": [{
        "id": "id_1",
        "name": "name_1",
        "value": 1,
        "orderId": "orderId_1"
      }]
    }
    pavan@pavan:~$ curl -H "Content-Type: application/json" -X PUT http://localhost:9000/item -d "{\"id\":\"id_1\", \"name\":\"name_1_updated\", \"vaue\":200, \"orderId\":\"orderId_1_updated\"}"
    {
      "id": "id_1",
      "name": "name_1_updated",
      "value": 200,
      "orderId": "orderId_1_updated"
    }
    pavan@pavan:~$ curl -H "Content-Type: application/json" -X DELETE http://localhost:9000/item -d "{\"id\":\"id_1\", \"name\":\"name_1_updated\", \value\":200, \"orderId\":\"orderId_1_updated\"}"
    {
      "items": []
    }
    pavan@pavan:~$ curl -H "Content-Type: application/json" -X GET http://localhost:9000/item 
    {
      "items": []
    }
    
