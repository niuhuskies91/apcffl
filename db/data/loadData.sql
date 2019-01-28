   
-- ********************************************
--      Import from command line
-- ********************************************

    <importPath>/mysqlimport --fields-terminated-by=, --verbose --local -u<user> -p<password> apcffl_phoenix <csvFile>

        <importPath> = /usr/local/mysql/bin/
        <user>       = root
        <password>   = Pa55word!
        <csvFile>    = /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/USER_GROUP.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/APPLICATION_ACTIVITY.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/BOWL.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/CONFERENCE.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/DIVISION.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/GROUP_ACTIVITY_MAP.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/LEAGUE.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/USER.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/WEEK.csv

-- ********************************************
--      Import from command line
-- ********************************************

  -- User Groups

  insert into apcffl_phoenix.USER_GROUP values (1, 'ADMINISTRATOR');
  insert into apcffl_phoenix.USER_GROUP values (2, 'TEAM_OWNER');
  insert into apcffl_phoenix.USER_GROUP values (3, 'GUEST');