package com.todonotes.utils;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

@Service
public class RedisService {

	private static RedisClient redisClient ;
	
	public RedisService() {
		redisClient = RedisClient.create(RedisURI.Builder.redis("localhost", 6379).build());
	}
	
	public static void saveToken(String id, String token) {
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisAsyncCommands<String, String> asyncCommands = connection.async();
		asyncCommands.set(id, token);
	}
	
	public static String getSavedToken(String id) throws InterruptedException, ExecutionException {
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisAsyncCommands<String, String> asyncCommands = connection.async();
		return asyncCommands.get(id).get();
	}
	
	public static void delSavedToken(String id) {
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisAsyncCommands<String, String> asyncCommands = connection.async();
		asyncCommands.del(id);
	}
}
