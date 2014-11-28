create DATABASE cesvote;

CREATE TABLE IF NOT EXISTS `VOTE` (
  `ID` bigint(20) NOT NULL auto_increment,
  `VOTEDATETIME` datetime NOT NULL,
  `TALKID` varchar(255) NOT NULL,
  `VOTE` int(11)  NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDb;
