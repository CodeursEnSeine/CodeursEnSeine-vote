package com.codeursenseine;

import com.codeursenseine.model.Talk;
import com.codeursenseine.model.Vote;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by youen on 18/11/2014.
 */
public class VoteResource {

    private Service service;

    public VoteResource() {
        service=new Service(true);
    }

    public VoteResource(Service service) {
        this.service=service;
    }

    @Get("/talks")
    public List<Talk> getTalks() {
       return service.getTalks();
    }

    @Post("/vote")
    public void addVote(Vote vote) throws SQLException {
        service.addVote(vote);
    }
}

