package com.ha.template;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ha.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateEx {
	
	@Autowired
	RedisTemplate<String, String> template;

	@Test
	public void Address_insert_Hash() {
		Address addr = new Address();
		addr.setAddr("test");
		addr.setId("1");
		addr.setPostnum(505054);
		
		template.opsForHash().put(
				Address.class.getSimpleName(), 
				addr.getId(), 
				addr);
		
		Address obj = (Address) template.opsForHash().get(
				Address.class.getSimpleName(), 
				addr.getId());
		
		System.out.println(obj.toString());
	}
	
	@Test
	public void Address_insert_list() throws JsonProcessingException {
		for(int i = 3; i < 5; i++) {
			Address adr = new Address();
			adr.setId(String.valueOf(i));
			adr.setAddr("juso,"+i);
			adr.setPostnum(i);
			
			ObjectMapper mapper = new ObjectMapper();
			String str = mapper.writeValueAsString(adr);
			template.opsForList().leftPush(
					Address.class.getSimpleName()+"list", 
					str);
		}
	}
	
	@Test
	public void Address_get_list() throws JsonParseException, JsonMappingException, IOException {
		for(int i = 0; i < 4; i++) {
			String str = template.opsForList().rightPop(
					Address.class.getSimpleName()+"list");
			
			ObjectMapper mapper = new ObjectMapper();
			Address obj = mapper.readValue(str, Address.class);
			System.out.println(obj);	
		}
	}
}
