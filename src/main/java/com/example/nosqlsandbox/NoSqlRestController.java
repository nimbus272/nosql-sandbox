package com.example.nosqlsandbox;

import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.nosqlsandbox.model.MongoRequest;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@RestController
public class NoSqlRestController {

    MongoClient client = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase database = client.getDatabase("mongodb-sandbox");
    MongoCollection<Document> collection = database.getCollection("testing");
    Gson gson = new Gson();

    @GetMapping(value = "/getAll")
    public String accessMongo() {

        StringBuilder builder = new StringBuilder();
        MongoCursor<Document> dbResult = collection.find().iterator();

        try {

            builder.append("[");
            while (dbResult.hasNext()) {

                builder.append(dbResult.next().toJson());
                if (dbResult.hasNext()) {
                    builder.append(",");
                }
            }
            builder.append("]");
        } finally {
            dbResult.close();
        }

        return builder.toString();

    }

    @PostMapping(value = "/insert")
    public String insertDocument(@RequestBody MongoRequest request) {

        Document document = Document.parse(gson.toJson(request));

        collection.insertOne(document);

        return "done";
    }

    @GetMapping(value = "/deleteAll")
    public String deleteAll() {
        collection.deleteMany(new Document());

        return "done";
    }

}
