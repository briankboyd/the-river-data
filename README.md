# the-river-data 

This is a Gradle project, to get started I installed an Eclipse plugin to assist with getting started. The Buildship gradle project https://projects.eclipse.org/projects/tools.buildship. Use the following url Eclipse -> Help -> Install New Software... Buildship - http://download.eclipse.org/buildship/updates/e45/releases

To generate the top300.csv file right click the Top300.groovy file and Run As Groovy Script.

To import the top300.csv file into a database(postgres support) run the following snippets in pgadmin3\terminal to create a database and table.
```PLpgSQL
CREATE DATABASE theriver
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_United States.1252'
       LC_CTYPE = 'English_United States.1252'
       CONNECTION LIMIT = -1
;

CREATE TABLE Top300(
 OVERALL_PLAYER_RANK_ID INT PRIMARY KEY NOT NULL
,NAME VARCHAR(150) NOT NULL
,POSITION VARCHAR(3) NOT NULL
,TEAM VARCHAR(5) NOT NULL
,BYE INT NOT NULL
,POSTION_RANK INT NOT NULL
)
;

--On the terminal fire up the psql cli and run the copy command to import the top300.csv data into the Top300 table.

--Log in as a superuser
psql -U postgres
--switch the connection to the Top300 database
\c Top300
--remove all rows and copy command to import the csv file, make sure to update the path to the file
truncate Top300; \copy top300 FROM 'C:\Users\boyd\Documents\projects\the-river-data\top300.csv' DELIMITER ',' CSV;
```
