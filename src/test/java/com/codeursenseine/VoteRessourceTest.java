
package com.codeursenseine;

import com.codeursenseine.model.Talk;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.sql.*;
import java.time.LocalTime;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VoteRessourceTest {

    @Mock
    DateHelper dateHelper=new DateHelper();

    Service service;

    VoteResource voteResource;

    Connection connection;

    public static final Operation CREATE_TABLES =
            sequenceOf(sql("CREATE TABLE IF NOT EXISTS `vote` (\n" +
                    "  `ID` bigint(20) NOT NULL auto_increment,\n" +
                    "  `VOTEDATETIME` datetime NOT NULL,\n" +
                    "  `TALKID` varchar(255) NOT NULL,\n" +
                    "  `VOTE` int(11)  NOT NULL,\n" +
                    "  PRIMARY KEY  (`ID`)\n" +
                    ") "));

    public static final Operation DELETE_ALL =
            deleteAllFrom("VOTE");


    @Before
    public void setUp() throws ClassNotFoundException, SQLException  {

        if (connection==null) {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/cesvote", "sa", "");
        }
        service=new Service(connection,dateHelper);
        voteResource= new VoteResource(service);
        DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:h2:~/cesvote", "sa", ""), sequenceOf(CREATE_TABLES,DELETE_ALL));
        dbSetup.launch();
    }

    @Test
    public void should_return_4_talks() {
        //Given
        when(dateHelper.getCurrentTime()).thenReturn(LocalTime.of(10, 35, 0, 0));
        //When
        List<Talk> talks=voteResource.getTalks();
        //Then
        assertThat(talks).isNotEmpty();
        assertThat(talks).hasSize(4);
    }

    @Test
    public void should_return_1_talks() {
        //Given
        when(dateHelper.getCurrentTime()).thenReturn(LocalTime.of(9, 35, 0, 0));
        //When
        List<Talk> talks=voteResource.getTalks();
        //Then
        assertThat(talks).isNotEmpty();
        assertThat(talks).hasSize(1);
    }

    @Test
    public void should_return_0_talks() {
        //Given
        when(dateHelper.getCurrentTime()).thenReturn(LocalTime.of(18, 35, 0, 0));
        //When
        List<Talk> talks=voteResource.getTalks();
        //Then
        assertThat(talks).isEmpty();
    }

    @Test
    public void should_add_vote() throws SQLException {
        //Given
        //When
        voteResource.addVote("123",1);
        //Then
        Statement st=service.getConnection().createStatement();
        ResultSet rs=st.executeQuery("SELECT * FROM VOTE WHERE TALKID='123';");
        assertThat(rs.first()).isTrue();
        assertThat(rs.getInt("VOTE")).isEqualTo(1);
        assertThat(rs.getDate("VOTEDATETIME")).isNotNull();
        assertThat(rs.isLast()).isTrue();

    }



}
