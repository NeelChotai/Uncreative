package com.uncreative.game;

import java.util.ArrayList;

public class Inventory {
    public ArrayList<Item> items;
    private Integer size;

    public Inventory(Integer size)
    {
        this.items = new ArrayList<Item>();
        this.size = size;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size)
    {
        if(size < this.getNoOfItems())
        {
            throw new IllegalArgumentException("The number of items won't fit inside the new size!");
        }
        Inventory newinv = new Inventory(size);
        newinv.items.addAll(this.items);
        this.items = newinv.items;
        this.size = newinv.size;
    }

    public void addItem(Item item)
    {
        if(this.getNoOfItems() >= this.getSize())
        {
            throw new IndexOutOfBoundsException("The inventory is already full!");
        }
        else
        {
            this.items.add(item);
        }
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public Integer getNoOfItems() {
        return this.items.size();
    }

    public Boolean isFull() {
        return (getNoOfItems() == getSize());
    }
}
