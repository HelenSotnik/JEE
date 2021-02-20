package com.hillel.demo.client.someapi;

import com.hillel.demo.core.application.dto.PageDto;
import com.hillel.demo.core.domain.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "someapi",url = "${client.someapi.url}")
public interface SomeClient {
    @RequestMapping(value ="/users",method = RequestMethod.GET)
    List<User> getAllUsers();
}