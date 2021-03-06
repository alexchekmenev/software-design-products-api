package ru.akirakozov.sd.refactoring.model;

import com.google.gson.JsonObject;

/**
 * Created by creed on 26.11.16.
 */
public class Product {
    private String name;
    private long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isValid() {
        return name != null && !name.isEmpty() && price > 0;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (name != null) {
            json.addProperty("name", name);
        }
        json.addProperty("price", price);
        return json;
    }
}
