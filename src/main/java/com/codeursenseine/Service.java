package com.codeursenseine;

import com.codeursenseine.model.Talk;
import com.codeursenseine.model.ces.Conference;
import com.codeursenseine.model.ces.Programme;
import com.codeursenseine.model.ces.Talks;
import com.codeursenseine.model.ces.Tracks;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by youen on 18/11/2014.
 */
public class Service {

    Connection connection;

    DateHelper dateHelper=new DateHelper();

    public Service() {
        String dbUrl = "jdbc:mysql://127.0.0.1/cesvote";
        String dbClass = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "";
        try {
            Class.forName(dbClass);
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Service(Connection connection,DateHelper dateHelper) {
        this.connection=connection;
        this.dateHelper=dateHelper;
    }

    public List<Talk> getTalks() {
        List<Talk> out=new ArrayList<>();
        Conference conf=new Gson().fromJson(HttpRequest.get("http://www.codeursenseine.com/2014/programme.json").body(),new TypeToken<Conference>(){}.getType());
        Iterator<Tracks> it=conf.programme.jours.get(0).tracks.iterator();
        while(it.hasNext()) {
            Tracks track=it.next();
            Talks talks=track.talks.get(0);
            LocalTime tracktime;
            if (talks.time.length()==4) {
                tracktime = LocalTime.parse("0"+talks.time + ":00");
            } else {
                tracktime = LocalTime.parse(talks.time + ":00");
            }
            LocalTime nowtime=dateHelper.getCurrentTime();

            if (nowtime.isAfter(tracktime.plus(30, ChronoUnit.MINUTES)) && nowtime.isBefore(tracktime.plus(60, ChronoUnit.MINUTES))) {
                Talk talk=new Talk();
                talk.talkid=talks.id;
                talk.room=talks.room;
                talk.talkTitle=talks.title;
                out.add(talk);
            }
        }

        return out;
    }

    public void addVote(String talkid, int vote) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO VOTE(VOTEDATETIME,TALKID,VOTE) VALUES ('" + LocalDateTime.now().toString() + "','" + talkid + "'," + vote +")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        return connection;
    }
}
