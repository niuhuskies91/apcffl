**********************************************************
****    DB ReadMe
**********************************************************

0. To get to MySQL command line:

    /usr/local/mysql/bin/mysql -uroot -p<password>

1. Database schema and table creation script
     /db/createAllDDL.sql

2. Data pre-load files
     /db/data/

3. Basic queries

    select * from apcffl_phoenix.APPLICATION_ACTIVITY;
    select * from apcffl_phoenix.CONFERENCE;
    select * from apcffl_phoenix.DIVISION;
    select * from apcffl_phoenix.LEAGUE;
    select * from apcffl_phoenix.OWNER;
    select * from apcffl_phoenix.USER;
    select * from apcffl_phoenix.USER_GROUP;
    select * from apcffl_phoenix.WEEK;

    select grp.USER_GROUP_NAME, act.ACTIVITY_NAME, act.ACTIVITY_DESC
    from apcffl_phoenix.GROUP_ACTIVITY_MAP grpMap
    inner join apcffl_phoenix.USER_GROUP grp on grp.USER_GROUP_ID = grpMap.USER_GROUP_ID
    inner join apcffl_phoenix.APPLICATION_ACTIVITY act on act.ACTIVITY_ID = grpMap.ACTIVITY_ID
    order by grp.USER_GROUP_NAME, act.ACTIVITY_NAME;

4. Load data files

      /db/data/loadData.sql

******.  http://www.espn.com/college-football/conferences  ******





