package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        List<String> valuesList = new ArrayList<String>(Arrays.asList("blank","apples","oranges","mangoes","bananas","pineapples","car","table","jackfruit","laptop","iPhone"));
     
        
        List<DistributedCacheService> server = new ArrayList<DistributedCacheService>();
        server.add(new DistributedCacheService("http://localhost:3000"));
        server.add(new DistributedCacheService("http://localhost:3001"));
        server.add(new DistributedCacheService("http://localhost:3002"));
        
        // one of the back end servers is removed from the (middle of the) pool
        //servers.remove(1);

       /* bucket = Hashing.consistentHash(Hashing.md5().hashString("jamtaykay?"), servers.size());
        System.out.println("2. Bucket is-->:"+bucket);
        System.out.println("Second time routed to: " + servers.get(bucket));*/
       
        System.out.println("----------------------------------------------------");
        for(int i=1; i<=10; i++)	{
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), server.size());
        	server.get(bucket).put(i, valuesList.get(i));
        	System.out.println("The key value pair " + i +"-" + valuesList.get(i)+ " is assigned to server " + bucket);
        }
        System.out.println("----------------------------------------------------");
        for(int getkey=1; getkey<=10; getkey++)	{
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(getkey)), server.size());
        	System.out.println("The key value pair " + getkey +"-" + server.get(bucket).get(getkey)+ " is received to server " + bucket);
        	
        }
        System.out.println("----------------------------------------------------");
       /* cache1.put(2, "foo");
        System.out.println("put(2 => foo)");

        String value = cache1.get(2);
        System.out.println("get(2) => " + value);*/

        server.remove(2);

        for(int getkey=1; getkey<=10; getkey++)	{
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(getkey)), server.size());
        	System.out.println("The key value pair " + getkey +"-" + server.get(bucket).get(getkey)+ " is received to server " + bucket);
        }
        System.out.println("Existing Cache Client...");
    }

}
