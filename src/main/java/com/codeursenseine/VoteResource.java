package com.codeursenseine;

import com.codeursenseine.model.Talk;
import net.codestory.http.annotations.Get;

import java.util.List;

/**
 * Created by youen on 18/11/2014.
 */
public class VoteResource {

    private Service service;

    public VoteResource() {
        service=new Service();
    }

    public VoteResource(Service service) {
        this.service=service;
    }

    @Get("/talks")
    public List<Talk> getTalks() {
       return service.getTalks();
    }

    @Get("/vote")
    public void addVote(String talkid,int vote) {
        service.addVote(talkid,vote);
    }
}

