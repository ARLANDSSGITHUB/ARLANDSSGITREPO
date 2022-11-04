package com.dss.feign;

import com.dss.entity.ActorEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="actor-service", url="localhost:9006/api/actor")
public interface ActorFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/findById/{actorId}", consumes = "application/json", produces="application/json")
    public ActorEntity findActorById(@PathVariable("actorId") int actorId);
}
