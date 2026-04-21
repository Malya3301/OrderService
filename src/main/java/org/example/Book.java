package org.example;

public record Book(String name, Integer price) {
    private int MakeASale(int tempPrice){
        return tempPrice * 8;
    }

    public int costWithX8(){
        return MakeASale(this.price);
    }
}
