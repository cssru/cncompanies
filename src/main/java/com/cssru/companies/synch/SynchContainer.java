package com.cssru.companies.synch;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class SynchContainer<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long lastSynchTime;
    private int synchStatus;
    private List<ItemDescriptor> items;
    private List<T> objects;
    private List<ItemDescriptor> deletedItems;


    public SynchContainer() {
        lastSynchTime = 0L;
        synchStatus = SynchStatus.UNDEFINED;
    }

    // getters
    public List<ItemDescriptor> getItems() {
        return items;
    }

    public List<T> getObjects() {
        return objects;
    }

    public List<ItemDescriptor> getDeletedItems() {
        return deletedItems;
    }

    public long getLastSynchTime() {
        return lastSynchTime;
    }

    public int getSynchStatus() {
        return synchStatus;
    }

    // setters
    public void setItems(List<ItemDescriptor> items) {
        this.items = items;
    }

    public void setObjects(List<T> changedObjects) {
        this.objects = changedObjects;
    }

    public void setDeletedItems(List<ItemDescriptor> deletedItems) {
        this.deletedItems = deletedItems;
    }

    public void setLastSynchTime(long lastSynchTime) {
        this.lastSynchTime = lastSynchTime;
    }

    public void setSynchStatus(int synchStatus) {
        this.synchStatus = synchStatus;
    }

    // add methods
    public void addItem(ItemDescriptor item) {
        if (items == null) items = new LinkedList<ItemDescriptor>();
        items.add(item);
    }

    public void addDeletedItem(ItemDescriptor item) {
        if (deletedItems == null) deletedItems = new LinkedList<ItemDescriptor>();
        deletedItems.add(item);
    }

    public void addObject(T object) {
        if (objects == null) objects = new LinkedList<T>();
        objects.add(object);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("lastSynchTime:").append(lastSynchTime).append("\n");
        sb.append("Items:\n");
        for (ItemDescriptor item : items) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("Objects:\n");
        for (T object : objects) {
            sb.append(object.toString()).append("\n");
        }
        sb.append("Deleted items:\n");
        for (ItemDescriptor deletedItem : deletedItems) {
            sb.append(deletedItem.toString()).append("\n");
        }

        return sb.toString();
    }
}
