   
-- ********************************************
--      Import from command line
-- ********************************************

    <importPath>/mysqlimport --fields-terminated-by=, --verbose --local -u<user> -p<password> apcffl_phoenix <csvFile>

        <importPath> = /usr/local/mysql/bin/
        <user>       = root
        <password>   = Pa55word!
        <csvFile>    = /Users/daniel.kamp/Documents/Other/APCFFL/Phoenix/db/data/USER_GROUP.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix ./data/APPLICATION_ACTIVITY.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix ./data/BOWL.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix ./data/CONFERENCE.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix ./data/GROUP_ACTIVITY_MAP.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix ./data/USER.csv

     /usr/local/mysql/bin/mysqlimport --fields-terminated-by=, --verbose --local -uroot -pPa55word! apcffl_phoenix ./data/WEEK.csv

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

  insert into apcffl_phoenix.USER (USER_ID, USER_NAME, PASSWORD, USER_GROUP_ID)
    values (10, 'brett.labombarda', 'Pa55word!', 1);

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

  -- League

  insert into apcffl_phoenix.LEAGUE (LEAGUE_ID, LEAGUE_NAME, NUMBER_OF_TEAMS, NUMBER_OF_DIVISIONS)
     values (1, 'The APCFFL', 8, 2);

  insert into apcffl_phoenix.LEAGUE (LEAGUE_ID, LEAGUE_NAME,  NUMBER_OF_TEAMS, NUMBER_OF_DIVISIONS)
     values (2, 'Test League', 8, 2);

  -- Division

  insert into apcffl_phoenix.DIVISION (DIVISION_ID, DIVISION_NAME, LEAGUE_ID)
     values (1, 'Natural Grass Division', 1);

  insert into apcffl_phoenix.DIVISION (DIVISION_ID, DIVISION_NAME, LEAGUE_ID)
     values (2, 'Artificial Turf Division', 1);

  insert into apcffl_phoenix.DIVISION (DIVISION_ID, DIVISION_NAME, LEAGUE_ID)
     values (3, 'Test Division 1.1', 2);

  insert into apcffl_phoenix.DIVISION (DIVISION_ID, DIVISION_NAME, LEAGUE_ID)
     values (4, 'Test Division 1.2', 2);

  -- ----------------------------------------------------------------
  -- Schools
  -- ----------------------------------------------------------------

  -- AAC

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Cincinnati', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Connecticut', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('East Carolina', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('South Florida', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Temple', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Central Florida', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Houston', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Memphis', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Navy', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('SMU', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Tulane', 1);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Tulsa', 1);

  -- ACC

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Boston College', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Clemson', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Florida State', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Louisville', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('NC State', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Syracuse', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Wake Forest', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Duke', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Georgia Tech', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Miami FL', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('North Carolina', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Pittsburgh', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Virginia', 2);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Virginia Tech', 2);

  -- Big 10

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Indiana', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Maryland', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Michigan', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Michigan State', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Ohio State', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Penn State', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Rutgers', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Illinois', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Iowa', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Minnesota', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Nebraska', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Northwestern', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Purdue', 4);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Wisconsin', 4);

  -- Big 12

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Baylor', 3);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Iowa State', 3);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Kansas', 3);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Kansas State', 3);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Oklahoma', 3);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Oklahoma State', 3);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('TCU', 3);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Texas', 3);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Texas Tech', 3);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('West Virginia', 3);

  -- C-USA

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Charlotte', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Florida Atlantic', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Florida International', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Marshall', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Middle Tennessee', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Old Dominion', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Western Kentucky', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Louisiana Tech', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('North Texas', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Rice', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('So Mississippi', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('UAB', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('UTSA', 5);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('UTEP', 5);

  -- Independents

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Army', 6);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('BYU', 6);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Liberty', 6);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Massachusetts', 6);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('New Mexico State', 6);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Notre Dame', 6);

  -- MAC

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Akron', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Bowling Green', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Buffalo', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Kent State', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Miami OH', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Ohio', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Ball State', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Central Michigan', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Eastern Michigan', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Northern Illinois', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Toledo', 7);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Western Michigan', 7);

  -- Mountain West (MW)

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Air Force', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Boise State', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Colorado State', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('New Mexico', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Utah State', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Wyoming', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Fresno State', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Hawaii', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Nevada', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('San Diego State', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('San Jose State', 8);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('UNLV', 8);

  -- PAC 12

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('California', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Oregon', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Oregon State', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Stanford', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Washington', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Wasington State', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Arizona', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Arizona State', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Colorado', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('UCLA', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('USC', 9);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Utah', 9);

  -- SEC

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Florida', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Georgia', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Kentucky', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Missouri', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('South Carolina', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Tennessee', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Vanderbilt', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Alabama', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Arkansas', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Auburn', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('LSU', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Mississippi', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Mississippi State', 10);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Texas A&M', 10);

  -- Sum

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Appalachian State', 11);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Coastal Carolina', 11);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Georgia Southern', 11);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Georgia State', 11);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Troy', 11);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Arkansas State', 11);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Louisiana Lafayette', 11);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Louisiana Monroe', 11);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('South Alabama', 11);

  insert into apcffl_phoenix.SCHOOL (SCHOOL_NAME, CONFERENCE_ID)
    values ('Texas State', 11);

