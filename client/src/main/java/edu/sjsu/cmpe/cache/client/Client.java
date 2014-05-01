package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        List<String> valuesList = new ArrayList<String>(Arrays.asList("blank","apples","oranges","mangoes","bananas","pineapples","car","table","jackfruit","laptop","iPhone"));
        
        List<DistributedCacheService> serverList = new ArrayList<DistributedCacheService>();
        serverList.add(new DistributedCacheService("http://localhost:3000"));
        serverList.add(new DistributedCacheService("http://localhost:3001"));
        serverList.add(new DistributedCacheService("http://localhost:3002"));
        
        System.out.println("--------------------- Put Values to Server -------------------------------");
        for(int i=1; i<=10; i++)	{
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), serverList.size());
        	serverList.get(bucket).put(i, valuesList.get(i));
        	System.out.println("The key value pair " + i +"-" + valuesList.get(i)+ " is assigned to server " + bucket);
        }
        System.out.println("---------------------- Retrieve Values from Server ------------------------------");
        for(int getkey=1; getkey<=10; getkey++)	{
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(getkey)), serverList.size());
        	System.out.println("The key value pair " + getkey +"-" + serverList.get(bucket).get(getkey)+ " is received to server " + bucket);
        	
        }

        System.out.println("Existing Cache Client...");
    }

}
