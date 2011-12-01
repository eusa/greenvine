drop all objects;

-- Drop and recreate schema
create schema "DBO";
create schema "TEST";

create table "DBO"."TBL_CONTRACT" (
	"EMPLOYEE_ID" int not null, 
	"TERMS" varchar(4000) not null, 
	constraint "DBO"."PK_CONTRACT" primary key ("EMPLOYEE_ID")
);

create table "DBO"."TBL_USER" (
	"USER_ID" int not null, 
	"USERNAME" varchar(255) not null, 
	"PASSWORD" varchar(255) not null, 
	constraint "DBO"."PK_USER" primary key ("USER_ID")
);

create table "DBO"."TBL_EMPLOYEE" (
	"EMPLOYEE_ID" int not null, 
	"FK_MANAGER_ID" int , 
	"FK_USER_ID" int not null, 
	"FIRST_NAME" varchar(255) not null, 
	"LAST_NAME" varchar(255) not null, 
	"EMAIL" varchar(255) not null, 
	"HOURLY_COST" decimal(19,4) not null, 
	"DAILY_WORKING_HOURS" decimal(4,1) not null, 
	constraint "DBO"."PK_EMPLOYEE" primary key ("EMPLOYEE_ID")
);

create table "DBO"."TBL_GROUP" (
	"GROUP_ID" int not null, 
	"GROUPNAME" varchar(255) not null, 
	constraint "DBO"."PK_GROUP" primary key ("GROUP_ID")
);

create table "DBO"."TBL_DESK" (
	"DESK_ID" int not null, 
	"CODE" varchar(5) not null, 
	constraint "DBO"."PK_DESK" primary key ("DESK_ID")
);

create table "DBO"."TBL_MAILBOX" (
	"MAILBOX_ID" int not null, 
	"FK_EMAIL" varchar(255) not null, 
	"NAME" varchar(255) not null, 
	constraint "DBO"."PK_MAILBOX" primary key ("MAILBOX_ID")
);

create table "DBO"."TBL_UMBRELLA" (
	"UMBRELLA_ID" int not null, 
	"FK_EMPLOYEE_ID" int not null, 
	"COLOUR" varchar(4000) not null, 
	constraint "DBO"."PK_UMBRELLA" primary key ("UMBRELLA_ID")
);

create table "DBO"."TBL_STAND" (
	"STAND_ID" int not null, 
	"DESCRIPTION" varchar(4000) not null, 
	constraint "PK_STAND" primary key ("STAND_ID")
);

create table "DBO"."TBL_GROUP_USER" (
	"FK_USER_ID" int not null, 
	"FK_GROUP_ID" int not null, 
	constraint "DBO"."PK_GROUP_USER" primary key ("FK_USER_ID","FK_GROUP_ID")
);

create table "DBO"."TBL_DESK_EMPLOYEE" (
	"FK_EMPLOYEE_ID" int not null, 
	"FK_DESK_ID" int not null, 
	constraint "DBO"."PK_DESK_EMPLOYEE" primary key ("FK_EMPLOYEE_ID","FK_DESK_ID")
);

create table "DBO"."TBL_STAND_UMBRELLA" (
	"FK_UMBRELLA_ID" int not null, 
	"FK_STAND_ID" int not null, 
	constraint "DBO"."PK_STAND_UMBRELLA" primary key ("FK_UMBRELLA_ID","FK_STAND_ID")
);

create table "TEST"."TBL_TIMESHEET" (
	"FK_EMPLOYEE_ID" int not null, 
	"DATE" date not null, 
	"HOURS" decimal(4,1) not null, 
	constraint "TEST"."PK_TIMESHEET" primary key ("FK_EMPLOYEE_ID","DATE")
);

create table "TEST"."TBL_ACTIVITY" (
	"ACTIVITY_ID" int not null, 
	"FK_EMPLOYEE_ID" int not null, 
	"FK_DATE" date not null, 
	"DESCRIPTION" varchar(255) not null, 
	"HOURS" decimal(4,1) not null, 
	constraint "TEST"."PK_ACTIVITY" primary key ("ACTIVITY_ID")
);

create table "TEST"."TBL_VEHICLE" (
	"REG_NUMBER" varchar(7) not null, 
	"MODEL" varchar(400) not null, 
	constraint "TEST"."PK_VEHICLE" primary key ("REG_NUMBER")
);

create table "TEST"."TBL_DAY_PASS" (
	"FK_REG_NUMBER" varchar(7) not null, 
	"DATE" date not null, 
	"VALUE" decimal(4,1) not null, 
	constraint "TEST"."PK_DAY_PASS" primary key ("FK_REG_NUMBER","DATE")
);


alter table "DBO"."TBL_USER"
    add constraint "DBO"."UNIQUE_TBL_USER_USERNAME" unique("USERNAME");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_EMPLOYEE_EMAIL" unique("EMAIL");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_EMPLOYEE_FIRST_NAME_LAST_NAME" unique("FIRST_NAME","LAST_NAME");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_EMPLOYEE_FK_USER_ID" unique("FK_USER_ID");

alter table "DBO"."TBL_GROUP"
    add constraint "DBO"."UNIQUE_TBL_GROUP_GROUPNAME" unique("GROUPNAME");

alter table "DBO"."TBL_MAILBOX"
    add constraint "DBO"."UNIQUE_TBL_MAILBOX_FK_EMAIL" unique("FK_EMAIL");

alter table "DBO"."TBL_DESK_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_DESK_EMPLOYEE_FK_EMPLOYEE_ID" unique("FK_EMPLOYEE_ID");

alter table "DBO"."TBL_DESK_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_DESK_EMPLOYEE_FK_DESK_ID" unique("FK_DESK_ID");

alter table "DBO"."TBL_STAND_UMBRELLA"
    add constraint "DBO"."UNIQUE_TBL_STAND_UMBRELLA_FK_UMBRELLA_ID" unique("FK_UMBRELLA_ID");

alter table "DBO"."TBL_CONTRACT"
    add constraint "DBO"."FK_TBL_CONTRACT_EMPLOYEE_ID_TBL_EMPLOYEE_EMPLOYEE_ID" foreign key ("EMPLOYEE_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."FK_TBL_EMPLOYEE_FK_MANAGER_ID_TBL_EMPLOYEE_EMPLOYEE_ID" foreign key ("FK_MANAGER_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."FK_TBL_EMPLOYEE_FK_USER_ID_TBL_USER_USER_ID" foreign key ("FK_USER_ID") references "DBO"."TBL_USER"("USER_ID");

alter table "DBO"."TBL_MAILBOX"
    add constraint "DBO"."FK_TBL_MAILBOX_FK_EMAIL_TBL_EMPLOYEE_EMAIL" foreign key ("FK_EMAIL") references "DBO"."TBL_EMPLOYEE"("EMAIL");

alter table "DBO"."TBL_UMBRELLA"
    add constraint "DBO"."FK_TBL_UMBRELLA_FK_EMPLOYEE_ID_TBL_EMPLOYEE_EMPLOYEE_ID" foreign key ("FK_EMPLOYEE_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_GROUP_USER"
    add constraint "DBO"."FK_TBL_GROUP_USER_FK_USER_ID_TBL_USER_USER_ID" foreign key ("FK_USER_ID") references "DBO"."TBL_USER"("USER_ID");

alter table "DBO"."TBL_GROUP_USER"
    add constraint "DBO"."FK_TBL_GROUP_USER_FK_GROUP_ID_TBL_GROUP_GROUP_ID" foreign key ("FK_GROUP_ID") references "DBO"."TBL_GROUP"("GROUP_ID");

alter table "DBO"."TBL_DESK_EMPLOYEE"
    add constraint "DBO"."FK_TBL_DESK_EMPLOYEE_FK_EMPLOYEE_ID_TBL_EMPLOYEE_EMPLOYEE_ID" foreign key ("FK_EMPLOYEE_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_DESK_EMPLOYEE"
    add constraint "DBO"."FK_TBL_DESK_EMPLOYEE_FK_DESK_ID_TBL_DESK_DESK_ID" foreign key ("FK_DESK_ID") references "DBO"."TBL_DESK"("DESK_ID");

alter table "DBO"."TBL_STAND_UMBRELLA"
    add constraint "DBO"."FK_TBL_STAND_UMBRELLA_FK_UMBRELLA_ID_TBL_UMBRELLA_UMBRELLA_ID" foreign key ("FK_UMBRELLA_ID") references "DBO"."TBL_UMBRELLA"("UMBRELLA_ID");

alter table "DBO"."TBL_STAND_UMBRELLA"
    add constraint "DBO"."FK_TBL_STAND_UMBRELLA_FK_STAND_ID_TBL_STAND_STAND_ID" foreign key ("FK_STAND_ID") references "DBO"."TBL_STAND"("STAND_ID");


alter table "TEST"."TBL_TIMESHEET"
    add constraint "TEST"."FK_TBL_TIMESHEET_FK_EMPLOYEE_ID_TBL_EMPLOYEE_EMPLOYEE_ID" foreign key ("FK_EMPLOYEE_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");
    
alter table "TEST"."TBL_ACTIVITY"
    add constraint "TEST"."FK_TBL_ACTIVITY_FK_EMPLOYEE_ID_FK_DATE_TBL_TIMESHEET_FK_EMPLOYEE_ID_DATE" foreign key ("FK_EMPLOYEE_ID","FK_DATE") references "TEST"."TBL_TIMESHEET"("FK_EMPLOYEE_ID","DATE");

alter table "TEST"."TBL_DAY_PASS"
    add constraint "TEST"."FK_TBL_DAY_PASS_FK_REG_NUMBER_TBL_VEHICLE_REG_NUMBER" foreign key ("FK_REG_NUMBER") references "TEST"."TBL_VEHICLE"("REG_NUMBER");
    