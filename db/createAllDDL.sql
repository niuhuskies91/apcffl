/*
     Create database schema

  - This outlines the create DDL for the APCFFL schema.
  - The DBMS used is MySQL

  */


create database apcffl_phoenix;


/* **************************************************************

       League tables

   ************************************************************** */


-- ================================================
--    League
-- ================================================

create table apcffl_phoenix.LEAGUE (
       LEAGUE_ID            bigint (15) not null auto_increment primary key,
       LEAGUE_NAME          varchar(40) not null,
       NUMBER_OF_TEAMS      smallint,
       NUMBER_OF_DIVISIONS  smallint
);

-- ================================================
--    Division
-- ================================================

create table apcffl_phoenix.DIVISION (
       DIVISION_ID          bigint (15) not null auto_increment primary key,
       DIVISION_NAME        varchar(40) not null,
       LEAGUE_ID            bigint (15)
);

alter table apcffl_phoenix.DIVISION
add foreign key fk_league_division(LEAGUE_ID)
references apcffl_phoenix.LEAGUE(LEAGUE_ID)
on delete no action
on update no action;


/* **************************************************************

       Security activity tables

   ************************************************************** */


-- ================================================
--    Application Activity
-- ================================================

create table apcffl_phoenix.APPLICATION_ACTIVITY (
       ACTIVITY_ID          bigint (15) not null auto_increment primary key,
       ACTIVITY_NAME        varchar(20) not null,
       ACTIVITY_DESC        varchar(100)
);

-- ================================================
--    User Group
-- ================================================

create table apcffl_phoenix.USER_GROUP (
       USER_GROUP_ID        bigint (15) not null auto_increment primary key,
       USER_GROUP_NAME      varchar(20) not null
);

-- ================================================
--    Group Activity Map  (association table)
-- ================================================

create table apcffl_phoenix.GROUP_ACTIVITY_MAP (
       GROUP_ACTIVITY_ID    bigint (15) not null auto_increment primary key,
       USER_GROUP_ID        bigint (15) not null,
       ACTIVITY_ID          bigint (15) not null
);

alter table apcffl_phoenix.GROUP_ACTIVITY_MAP
add foreign key fk_user_group_activity(USER_GROUP_ID)
references apcffl_phoenix.USER_GROUP(USER_GROUP_ID)
on delete cascade
on update cascade;

alter table apcffl_phoenix.GROUP_ACTIVITY_MAP
add foreign key fk_activity_user_group(ACTIVITY_ID)
references apcffl_phoenix.APPLICATION_ACTIVITY(ACTIVITY_ID)
on delete cascade
on update cascade;

-- ================================================
--    User
-- ================================================

create table apcffl_phoenix.USER (
       USER_ID              bigint (15) not null auto_increment primary key,
       USER_NAME            varchar(40) not null,
       PASSWORD             varchar(20) not null,
       USER_GROUP_ID        bigint (15) not null
);

alter table apcffl_phoenix.USER
add foreign key fk_user_group_user(USER_GROUP_ID)
references apcffl_phoenix.USER_GROUP(USER_GROUP_ID)
on delete no action
on update cascade;

-- ================================================
--    Activity Log
-- ================================================

create table apcffl_phoenix.ACTIVITY_LOG (
       USER_ID              bigint (15) not null,
       ACTIVITY_ID          bigint (15) not null,
       TX_TIMESTAMP         datetime not null,
       TX_COMPLETED         boolean not null default 0,
       primary key(USER_ID, ACTIVITY_ID, TX_TIMESTAMP)
);

alter table apcffl_phoenix.ACTIVITY_LOG
add foreign key fk_user_activity(USER_ID)
references apcffl_phoenix.USER(USER_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.ACTIVITY_LOG
add foreign key fk_activity_user(ACTIVITY_ID)
references apcffl_phoenix.APPLICATION_ACTIVITY(ACTIVITY_ID)
on delete no action
on update no action;

-- ================================================
--    Owner
-- ================================================

create table apcffl_phoenix.OWNER (
       OWNER_ID             bigint (15) not null auto_increment primary key,
       LEAGUE_ID            bigint (15) not null,
       FIRST_NAME           varchar(20) not null,
       LAST_NAME            varchar(25) not null,
       EMAIL1               varchar(60) not null,
       EMAIL2               varchar(60),
       EMAIL3               varchar(60),
       ACTIVE_FLAG          boolean not null default 0,
       USER_ID              bigint (15) not null,
       CREATE_DATE          datetime,
       UPDATE_DATE          datetime
);

alter table apcffl_phoenix.OWNER
add foreign key fk_user_owner(USER_ID)
references apcffl_phoenix.USER(USER_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.OWNER
add foreign key fk_league_owner(LEAGUE_ID)
references apcffl_phoenix.LEAGUE(LEAGUE_ID)
on delete no action
on update no action;


/* **************************************************************

       Teams and rosters tables

   ************************************************************** */


-- ================================================
--    Conference
-- ================================================

create table apcffl_phoenix.CONFERENCE (
       CONFERENCE_ID        bigint (15) not null auto_increment primary key,
       CONFERENCE_ABBR      varchar(10) not null,
       CONFERENCE_NAME      varchar(50) not null,
       NCAA_DIVISION_TYPE   varchar(3)
);

-- ================================================
--    School
-- ================================================

create table apcffl_phoenix.SCHOOL (
       SCHOOL_ID            bigint (15) not null auto_increment primary key,
       SCHOOL_NAME          varchar(50) not null,
       CONFERENCE_ID        bigint (15) not null
);

alter table apcffl_phoenix.SCHOOL
add foreign key fk_conference_school(CONFERENCE_ID)
references apcffl_phoenix.CONFERENCE(CONFERENCE_ID)
on delete no action
on update no action;

-- ================================================
--    Team
-- ================================================

create table apcffl_phoenix.TEAM (
       TEAM_ID              bigint (15) not null auto_increment primary key,
       TEAM_NAME            varchar(50) not null,
       OWNER_ID             bigint (15) not null,
       DIVISION_ID          bigint (15) not null,
       CREATE_DATE          datetime,
       UPDATE_DATE          datetime
);

alter table apcffl_phoenix.TEAM
add foreign key fk_owner_team(OWNER_ID)
references apcffl_phoenix.OWNER(OWNER_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.TEAM
add foreign key fk_division_team(DIVISION_ID)
references apcffl_phoenix.DIVISION(DIVISION_ID)
on delete no action
on update no action;

-- ================================================
--    Team Roster
-- ================================================

create table apcffl_phoenix.TEAM_ROSTER (
       TEAM_ID              bigint (15) not null,
       SCHOOL_ID            bigint (15) not null,
       SCHOLARSHIP_POINTS   smallint,
       primary key(TEAM_ID, SCHOOL_ID)
);

alter table apcffl_phoenix.TEAM_ROSTER
add foreign key fk_team_roster(TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.TEAM_ROSTER
add foreign key fk_school_roster(SCHOOL_ID)
references apcffl_phoenix.SCHOOL(SCHOOL_ID)
on delete no action
on update no action;


/* **************************************************************

       Calendar tables

   ************************************************************** */


-- ================================================
--    Week
-- ================================================

create table apcffl_phoenix.WEEK (
       WEEK_ID              bigint (15) not null auto_increment primary key,
       WEEK_NAME            varchar(30) not null
);

-- ================================================
--    Season Week
-- ================================================

create table apcffl_phoenix.SEASON_WEEK (
       SEASON_WEEK_ID       bigint (15) not null auto_increment primary key,
       YEAR                 smallint not null,
       WEEK_ID              bigint (15) not null,
       WEEK_BEGIN_DATE      date,
       WEEK_END_DATE        date
);

alter table apcffl_phoenix.SEASON_WEEK
add foreign key fk_week_season_week(WEEK_ID)
references apcffl_phoenix.WEEK(WEEK_ID)
on delete no action
on update no action;

-- ================================================
--    Regular Season Schedule
-- ================================================

create table apcffl_phoenix.REGULAR_SEASON_SCHEDULE (
       SEASON_WEEK_ID            bigint (15) not null,
       HOME_TEAM_ID              bigint (15) not null,
       VISITING_TEAM_ID          bigint (15) not null,
       HOME_TEAM_TOTAL_SCORE     smallint not null default 0,
       VISITING_TEAM_TOTAL_SCORE smallint not null default 0,
       HFA_VALUE                 smallint not null default 0,
       primary key(SEASON_WEEK_ID, HOME_TEAM_ID, VISITING_TEAM_ID)
);

alter table apcffl_phoenix.REGULAR_SEASON_SCHEDULE
add foreign key fk_season_week_schedule(SEASON_WEEK_ID)
references apcffl_phoenix.SEASON_WEEK(SEASON_WEEK_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.REGULAR_SEASON_SCHEDULE
add foreign key fk_home_team_schedule(HOME_TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.REGULAR_SEASON_SCHEDULE
add foreign key fk_visiting_team_schedule(VISITING_TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

-- ================================================
--    Weekly lineup
-- ================================================

create table apcffl_phoenix.LINEUP_UNIT (
       TEAM_ID               bigint (15) not null,
       SEASON_WEEK_ID        bigint (15) not null,
       SCHOOL_ID             bigint (15) not null,
       OPPOSING_SCHOOL_ID    bigint (15) not null,
       SCHOOL_SCORE          smallint not null default 0,
       OPPOSING_SCHOOL_SCORE smallint not null default 0,
       TOTAL_SCORE           smallint not null default 0,
       LOCK_FLAG             boolean not null default 0,
       CREATE_DATE           datetime,
       UPDATE_DATE           datetime,
       primary key(TEAM_ID, SEASON_WEEK_ID, SCHOOL_ID)
);

alter table apcffl_phoenix.LINEUP_UNIT
add foreign key fk_team_lineup(TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.LINEUP_UNIT
add foreign key fk_week_lineup(SEASON_WEEK_ID)
references apcffl_phoenix.SEASON_WEEK(SEASON_WEEK_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.LINEUP_UNIT
add foreign key fk_school_lineup(SCHOOL_ID)
references apcffl_phoenix.SCHOOL(SCHOOL_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.LINEUP_UNIT
add foreign key fk_opposing_school_lineup(OPPOSING_SCHOOL_ID)
references apcffl_phoenix.SCHOOL(SCHOOL_ID)
on delete no action
on update no action;

-- ================================================
--    Standings
-- ================================================

create table apcffl_phoenix.STANDINGS (
       TEAM_ID              bigint (15) not null,
       YEAR                 smallint not null,
       WINS                 smallint not null default 0,
       LOSSES               smallint not null default 0,
       TIES                 smallint not null default 0,
       WINS_VS_LEAGUE       smallint not null default 0,
       LOSSES_VS_LEAGUE     smallint not null default 0,
       TIES_VS_LEAGUE       smallint not null default 0,
       WINS_VS_DIVISION     smallint not null default 0,
       LOSSES_VS_DIVISION   smallint not null default 0,
       TIES_VS_DIVISION     smallint not null default 0,
       GAMES_BACK           smallint not null default 0,
       RANKING              smallint not null default 1,
       primary key(TEAM_ID, YEAR)
);

alter table apcffl_phoenix.STANDINGS
add foreign key fk_team_standings(TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;


/* **************************************************************

       Bowl tables

   ************************************************************** */


-- ================================================
--    Bowl
-- ================================================

create table apcffl_phoenix.BOWL (
       BOWL_ID              bigint (15) not null auto_increment primary key,
       BOWL_NAME            varchar(25) not null,
       BOWL_RANK            smallint not null
);

-- ================================================
--    Bowl Matchup
-- ================================================

create table apcffl_phoenix.BOWL_MATCHUP (
       BOWL_ID                   bigint (15) not null,
       SEASON_WEEK_ID            bigint (15) not null,
       HOME_TEAM_ID              bigint (15) not null,
       VISITING_TEAM_ID          bigint (15) not null,
       HOME_TEAM_TOTAL_SCORE     smallint not null default 0,
       VISITING_TEAM_TOTAL_SCORE smallint not null default 0,
       primary key(BOWL_ID, SEASON_WEEK_ID)
);

alter table apcffl_phoenix.BOWL_MATCHUP
add foreign key fk_bowl_bowl_matchup(BOWL_ID)
references apcffl_phoenix.BOWL(BOWL_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.BOWL_MATCHUP
add foreign key fk_season_week_bowl_matchup(SEASON_WEEK_ID)
references apcffl_phoenix.SEASON_WEEK(SEASON_WEEK_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.BOWL_MATCHUP
add foreign key fk_home_team_bowl_matchup(HOME_TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.BOWL_MATCHUP
add foreign key fk_visiting_team_bowl_matchup(VISITING_TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

-- ================================================
--    Turf Bowl Lineup Unit
-- ================================================

create table apcffl_phoenix.TURF_BOWL_LINEUP_UNIT (
       SEASON_WEEK_ID        bigint (15) not null,
       SCHOOL_ID             bigint (15) not null,
       DIVISION_ID           bigint (15) not null,
       OPPOSING_SCHOOL_ID    bigint (15) not null,
       SCHOOL_SCORE          smallint not null default 0,
       OPPOSING_SCHOOL_SCORE smallint not null default 0,
       LOCK_FLAG             boolean not null default 0,
       CREATE_DATE           datetime,
       UPDATE_DATE           datetime,
       primary key(SEASON_WEEK_ID, SCHOOL_ID)
);

alter table apcffl_phoenix.TURF_BOWL_LINEUP_UNIT
add foreign key fk_season_week_turf_bowl(SEASON_WEEK_ID)
references apcffl_phoenix.SEASON_WEEK(SEASON_WEEK_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.TURF_BOWL_LINEUP_UNIT
add foreign key fk_school_turf_bowl(SCHOOL_ID)
references apcffl_phoenix.SCHOOL(SCHOOL_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.TURF_BOWL_LINEUP_UNIT
add foreign key fk_division_bowl_matchup(DIVISION_ID)
references apcffl_phoenix.DIVISION(DIVISION_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.TURF_BOWL_LINEUP_UNIT
add foreign key fk_opposing_school_turf_bowl(OPPOSING_SCHOOL_ID)
references apcffl_phoenix.SCHOOL(SCHOOL_ID)
on delete no action
on update no action;


/* **************************************************************

       Communications tables

   ************************************************************** */


-- ================================================
--    Announcements
-- ================================================

create table apcffl_phoenix.ANNOUNCEMENTS (
       ANNOUNCEMENT_ID      bigint (15) not null auto_increment primary key,
       CREATE_DATE          datetime not null,
       EXPIRE_DATE          datetime,
       MESSAGE              varchar(500) not null
);

-- ================================================
--    Message Board
-- ================================================

create table apcffl_phoenix.MESSAGE_BOARD (
       MESSAGE_ID           bigint (15) not null auto_increment primary key,
       OWNER_ID             bigint (15) not null,
       CREATE_DATE          datetime not null,
       ARCHIVE_DATE         datetime,
       MESSAGE              varchar(250) not null
);

alter table apcffl_phoenix.MESSAGE_BOARD
add foreign key fk_owner_message_board(OWNER_ID)
references apcffl_phoenix.OWNER(OWNER_ID)
on delete no action
on update no action;


/* **************************************************************

       Stastics

   ************************************************************** */

-- ================================================
--    Points scored / week team
-- ================================================

create table apcffl_phoenix.STATS_POINTS_SCORED_WEEK_TEAM (
       TEAM_ID              bigint (15) not null,
       WEEK_ID              bigint (15) not null,
       YEAR                 smallint not null,
       POINTS_SCORED        smallint not null default 0,
       primary key(TEAM_ID, WEEK_ID, YEAR)
);

alter table apcffl_phoenix.STATS_POINTS_SCORED_WEEK_TEAM
add foreign key fk_team_pts_score_week_team(TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.STATS_POINTS_SCORED_WEEK_TEAM
add foreign key fk_week_pts_score_week_team(WEEK_ID)
references apcffl_phoenix.WEEK(WEEK_ID)
on delete no action
on update no action;

-- ================================================
--    Margin of victory
-- ================================================

create table apcffl_phoenix.STATS_MARGIN_OF_VICTORY (
       TEAM_ID              bigint (15) not null,
       WEEK_ID              bigint (15) not null,
       YEAR                 smallint not null,
       POINT_MARGIN         smallint not null default 0,
       primary key(TEAM_ID, WEEK_ID, YEAR)
);

alter table apcffl_phoenix.STATS_MARGIN_OF_VICTORY
add foreign key fk_team_margin_victory(TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.STATS_MARGIN_OF_VICTORY
add foreign key fk_week_margin_victory(WEEK_ID)
references apcffl_phoenix.WEEK(WEEK_ID)
on delete no action
on update no action;

-- ================================================
--    Winning streak
-- ================================================

create table apcffl_phoenix.STATS_WINNING_STREAK (
       TEAM_ID              bigint (15) not null,
       START_WEEK_ID        bigint (15) not null,
       YEAR                 smallint not null,
       NUMBER_WEEKS         smallint not null default 1,
       ACTIVE_STREAK_YN     boolean not null default 0,
       primary key(TEAM_ID, START_WEEK_ID, YEAR)
);

alter table apcffl_phoenix.STATS_WINNING_STREAK
add foreign key fk_team_winning_streak(TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.STATS_WINNING_STREAK
add foreign key fk_week_winning_streak(START_WEEK_ID)
references apcffl_phoenix.WEEK(WEEK_ID)
on delete no action
on update no action;

-- ================================================
--    Losing streak
-- ================================================

create table apcffl_phoenix.STATS_LOSING_STREAK (
       TEAM_ID              bigint (15) not null,
       START_WEEK_ID        bigint (15) not null,
       YEAR                 smallint not null,
       NUMBER_WEEKS         smallint not null default 1,
       ACTIVE_STREAK_YN     boolean not null default 0,
       primary key(TEAM_ID, START_WEEK_ID, YEAR)
);

alter table apcffl_phoenix.STATS_LOSING_STREAK
add foreign key fk_team_losing_streak(TEAM_ID)
references apcffl_phoenix.TEAM(TEAM_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.STATS_LOSING_STREAK
add foreign key fk_week_losing_streak(START_WEEK_ID)
references apcffl_phoenix.WEEK(WEEK_ID)
on delete no action
on update no action;

-- ================================================
--    School points scored
-- ================================================

create table apcffl_phoenix.STATS_SCHOOL_POINTS_SCORED (
       SCHOOL_ID            bigint (15) not null,
       WEEK_ID              bigint (15) not null,
       YEAR                 smallint not null,
       POINTS_SCORED        smallint not null default 0,
       primary key(SCHOOL_ID, WEEK_ID, YEAR)
);

alter table apcffl_phoenix.STATS_SCHOOL_POINTS_SCORED
add foreign key fk_school_school_pts_scored(SCHOOL_ID)
references apcffl_phoenix.SCHOOL(SCHOOL_ID)
on delete no action
on update no action;

alter table apcffl_phoenix.STATS_SCHOOL_POINTS_SCORED
add foreign key fk_week_school_pts_scored(WEEK_ID)
references apcffl_phoenix.WEEK(WEEK_ID)
on delete no action
on update no action;



/*

CREATE TABLE BID (
       BID_ID               INTEGER NOT NULL,
       TEAM_ID              INTEGER NOT NULL,
       SEASON_WEEK_ID       INTEGER NOT NULL,
       CONTINGENT_PARENT_BID INTEGER,
       CONTINGENT_CHILD_BID INTEGER,
       LOCK_FLAG            CHAR(1) NOT NULL DEFAULT 'N',
       STATUS               CHAR(1),
       CREATE_DATE          TIMESTAMP,
       UPDATE_DATE          TIMESTAMP
);


ALTER TABLE BID
       ADD CONSTRAINT XPKBID PRIMARY KEY (BID_ID);


CREATE TABLE BID_DETAILS (
       SCHOOL_ID            INTEGER NOT NULL,
       BID_ID               INTEGER NOT NULL,
       BID_TYPE_CD          CHAR(1) NOT NULL,
       MIN_AMOUNT           INTEGER DEFAULT 0,
       MAX_AMOUNT           INTEGER DEFAULT 0
);


ALTER TABLE BID_DETAILS
       ADD CONSTRAINT XPKBID_DETAILS PRIMARY KEY (SCHOOL_ID, BID_ID);


CREATE TABLE TEAM_TRANSACTION_HISTORY (
       SCHOOL_ID            INTEGER NOT NULL,
       TEAM_ID              INTEGER NOT NULL,
       SEASON_WEEK_ID       INTEGER NOT NULL,
       TRANSACTION_TYPE     CHAR(1) NOT NULL
                                   CONSTRAINT School_Tx_Type
                                          CHECK (TRANSACTION_TYPE IN 
('A', 'D')),
       WINNING_BID_AMOUNT   SMALLINT DEFAULT 0
);


ALTER TABLE TEAM_TRANSACTION_HISTORY
       ADD CONSTRAINT XPKTEAM_TRANSACTIO PRIMARY KEY (SCHOOL_ID, 
              TEAM_ID, SEASON_WEEK_ID);

*/