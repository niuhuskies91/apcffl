   
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
--      Insert via SQL
-- ********************************************

  -- User Groups

  insert into apcffl_phoenix.USER_GROUP values (1, 'ADMINISTRATOR');
  insert into apcffl_phoenix.USER_GROUP values (2, 'TEAM_OWNER');
  insert into apcffl_phoenix.USER_GROUP values (3, 'GUEST');

  -- User

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (1, 'dan.kamp', 'Pa55word!', 1);

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (2, 'keith.haugen', 'Pa55word!', 2);

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (3, 'dan.kumm', 'Pa55word!', 2);

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (4, 'danielle.kamp', 'Pa55word!', 2);

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (5, 'kris.barry', 'Pa55word!', 2);

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (6, 'ami.falk', 'Pa55word!', 2);

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (7, 'guest', 'Pa55word!', 3);

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (8, 'BLIND.1', 'Pa55word!', 2);

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (9, 'BLIND.2', 'Pa55word!', 2);

  -- Owner

  insert into apcffl_phoenix.OWNER (OWNER_ID, LEAGUE_ID, FIRST_NAME, LAST_NAME, EMAIL1, ACTIVE_FLAG, USER_ID, CREATE_DATE) 
    values (1, 1, 'Dan', 'Kamp', 'niu_huskies91@yahoo.com', 1, 1, CURDATE());

  insert into apcffl_phoenix.OWNER (OWNER_ID, LEAGUE_ID, FIRST_NAME, LAST_NAME, EMAIL1, ACTIVE_FLAG, USER_ID, CREATE_DATE) 
    values (2, 1, 'Keith', 'Haugen', 'keith.haugen@motorolasolutions.com', 1, 2, CURDATE());

  insert into apcffl_phoenix.OWNER (OWNER_ID, LEAGUE_ID, FIRST_NAME, LAST_NAME, EMAIL1, ACTIVE_FLAG, USER_ID, CREATE_DATE) 
    values (3, 1, 'Dan', 'Kumm', 'dankumm@gmail.com', 1, 3, CURDATE());

  insert into apcffl_phoenix.OWNER (OWNER_ID, LEAGUE_ID, FIRST_NAME, LAST_NAME, EMAIL1, ACTIVE_FLAG, USER_ID, CREATE_DATE) 
    values (4, 1, 'Danielle', 'Kamp', 'danielle_kamp@yahoo.com', 1, 4, CURDATE());

  insert into apcffl_phoenix.OWNER (OWNER_ID, LEAGUE_ID, FIRST_NAME, LAST_NAME, EMAIL1, ACTIVE_FLAG, USER_ID, CREATE_DATE) 
    values (5, 1, 'Kris', 'Barry', 'kris@krisbarry.com', 1, 5, CURDATE());

  insert into apcffl_phoenix.OWNER (OWNER_ID, LEAGUE_ID, FIRST_NAME, LAST_NAME, EMAIL1, ACTIVE_FLAG, USER_ID, CREATE_DATE) 
    values (6, 1, 'Ami', 'Falk', 'ami_falk@yahoo.com', 1, 6, CURDATE());

  insert into apcffl_phoenix.OWNER (OWNER_ID, LEAGUE_ID, FIRST_NAME, LAST_NAME, EMAIL1, ACTIVE_FLAG, USER_ID, CREATE_DATE) 
    values (7, 1, 'Guest', 'Apcffl', 'apcffl.org@gmail.com', 1, 7, CURDATE());

  insert into apcffl_phoenix.OWNER (OWNER_ID, LEAGUE_ID, FIRST_NAME, LAST_NAME, EMAIL1, ACTIVE_FLAG, USER_ID, CREATE_DATE) 
    values (8, 1, 'Blind Artificial', 'Apcffl', 'apcffl.org@gmail.com', 1, 8, CURDATE());

  insert into apcffl_phoenix.OWNER (OWNER_ID, LEAGUE_ID, FIRST_NAME, LAST_NAME, EMAIL1, ACTIVE_FLAG, USER_ID, CREATE_DATE) 
    values (9, 1, 'Blind Natural', 'Apcffl', 'apcffl.org@gmail.com', 1, 9, CURDATE());

  -- Config

  insert into apcffl_phoenix.CONFIG (CONFIG_KEY, CONFIG_VALUE, CONFIG_DESC)
     values ('security.token.expiration', '21600000', 'Security expiration token is in milliseconds. Default (21600000)');

  insert into apcffl_phoenix.CONFIG (CONFIG_KEY, CONFIG_VALUE, CONFIG_DESC)
     values ('security.password.token.expiration', '300000', 'Password reset token expiration in milliseconds. Default (300000)');
