package com.example.thread.safe.collection;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.*;

public class CollectionTest {

    public static void main(String[] args){

        //thread-safe for ArrayList
        Vector<String> vector = new Vector<String>();
        vector.add("abc");
        vector.add("ggg");
        for(String s : vector){
            System.out.println(s);
        }

        //A thread-safe variant of ArrayList
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>();
        copyOnWriteArrayList.add("afdaf");
        copyOnWriteArrayList.add("ffff");
        for(String s: copyOnWriteArrayList){
            System.out.println(s);
        }

        //thread-safe for set
        CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<String>();
        copyOnWriteArraySet.add("124");
        copyOnWriteArraySet.add("afdj");
        for(String s: copyOnWriteArraySet){
            System.out.println(s);
        }

        //thread-safe for hash map
        Map<String,String> hashTable = new Hashtable<String,String>();
        hashTable.put("k1","abc");
        hashTable.put("k2","456");
        for(Map.Entry<String,String> entry : hashTable.entrySet()){
            System.out.println("key="+entry.getKey()+",value="+entry.getValue());
        }

        //thread-safe for hash map, for highly-concurrent
        Map<String,String> hashMap = new ConcurrentHashMap<String,String>();
        hashMap.put("k1","abc");
        hashMap.put("k2","456");
        for(Map.Entry<String,String> entry : hashMap.entrySet()){
            System.out.println("key="+entry.getKey()+",value="+entry.getValue());
        }

        //thread-safe for queue (first in first out)
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>();
        linkedBlockingQueue.add("124");
        linkedBlockingQueue.add("ere");
        for(String s: linkedBlockingQueue){
            System.out.println(s);
        }

        //thread-safe for deque (double-ended queue(雙向佇列))
        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>();
        linkedBlockingDeque.add("fdf");
        linkedBlockingDeque.addFirst("first element");
        linkedBlockingDeque.add("fdfd");
        linkedBlockingDeque.addLast("last element");
        for(String s: linkedBlockingDeque){
            System.out.println(s);
        }
    }
}
