package com.uncreative.game;

import com.sun.tools.classfile.ConstantPool;

public class Inventory {
    public Item[] items;
    private Integer size;

    public Inventory(Integer size)
    {
        this.items = new Item[size];
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
        int newpointer = 0;
        for(int i = 0; i<this.items.length; i++)
        {
            if(this.items[i] != null)
            {
                newinv.items[newpointer] = this.items[i];
                newpointer++;
            }
        }
        this.items = newinv.items;
        this.size = newinv.size;
    }

    public void addItem(Item item)
    {
        if(this.getNoOfItems() == this.getSize())
        {
            throw new IndexOutOfBoundsException("The inventory is already full!");
        }
        else
        {
            for(int i = 0; i < this.items.length; i++)
            {
                if(this.items[i] == null)
                {
                    this.items[i] = item;
                    return;
                }
            }
        }
    }

    public Integer getNoOfItems() {
        int count = 0;
        for(int i = 0; i < this.items.length; i++)
        {
            if(this.items[i] != null)
            {
                count++;
            }
        }
        return count;
    }
}
