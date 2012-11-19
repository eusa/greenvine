drop all objects;

-- Drop and recreate schema
create schema "DBO";
create schema "TEST_SCHEMA";

create table "DBO"."TBL_CONTRACT" (
	"FK_EMPLOYEE_ID" int not null, 
	"TERMS" varchar(4000) not null, 
	constraint "DBO"."PK_CONTRACT" primary key ("FK_EMPLOYEE_ID")
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

create table "DBO"."TBL_EMPLOYEE_MENTOR" (
	"EMPLOYEE_MENTOR_ID" int not null, 
	"MENTOR_ID" int not null,
	"MENTOREE_ID" int not null, 
	constraint "DBO"."PK_EMPLOYEE_MENTOR" primary key ("EMPLOYEE_MENTOR_ID")
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

create table "TEST_SCHEMA"."TBL_TIMESHEET" (
	"FK_EMPLOYEE_ID" int not null, 
	"DATE" date not null, 
	"EXPECTED_HOURS" decimal(4,1) not null, 
	constraint "TEST_SCHEMA"."PK_TIMESHEET" primary key ("FK_EMPLOYEE_ID","DATE")
);

create table "TEST_SCHEMA"."TBL_ACTIVITY" (
	"ACTIVITY_ID" int not null, 
	"FK_EMPLOYEE_ID" int not null, 
	"FK_DATE" date not null, 
	"DESCRIPTION" varchar(255) not null, 
	"HOURS" decimal(4,1) not null, 
	constraint "TEST_SCHEMA"."PK_ACTIVITY" primary key ("ACTIVITY_ID")
);

/*
Test many-to-one part of compound key
*/
create table "TEST_SCHEMA"."TBL_VEHICLE" (
	"REG_NUMBER" char(7) not null, 
	"MODEL" varchar(400) not null, 
	constraint "TEST_SCHEMA"."PK_VEHICLE" primary key ("REG_NUMBER")
);

create table "TEST_SCHEMA"."TBL_PARKING_PERMIT" (
	"FK_REG_NUMBER" char(7) not null, 
	"DATE" date not null, 
	"VALUE" decimal(4,1) not null, 
	constraint "TEST_SCHEMA"."PK_PARKING_PERMIT" primary key ("FK_REG_NUMBER","DATE")
);

/*
Test derived primary key one-to-one
*/
create table "TEST_SCHEMA"."TBL_PARKING_PERMIT_PAYMENT" (
	"FK_REG_NUMBER" char(7) not null, 
	"FK_PARKING_PERMIT_DATE" date not null, 
	"PAYMENT_DATE" timestamp not null, 
	constraint "TEST_SCHEMA"."PK_PARKING_PERMIT_PAYMENT" primary key ("FK_REG_NUMBER","FK_PARKING_PERMIT_DATE")
);

/*
Test duplicate table name in different schema
*/
create table "TEST_SCHEMA"."TBL_USER" (
	"USERNAME" varchar(50) not null, 
	"PASSWORD" varchar(50) not null, 
	constraint "TEST_SCHEMA"."PK_USER" primary key ("USERNAME")
);

/*
Test multiple foreign keys between 2 tables
*/
create table "TEST_SCHEMA"."TBL_BUGS" (
    "BUG_ID" int not null,
	"OWNER" varchar(50) not null, 
	"REPORTER" varchar(50) not null,
	"TITLE" varchar(255) not null,
	"DESCRIPTION" varchar(4000) not null,
	"OPEN" bit not null,
	constraint "TEST_SCHEMA"."PK_BUGS" primary key ("BUG_ID")
);

/*
Test what happens when the same 
table is a dependency of multiple tables
(in this case, user)
*/
create table "TEST_SCHEMA"."TBL_COMMENTS" (
	"COMMENT_ID" int not null,
    "BUG_ID" int not null,
	"USERNAME" varchar(50) not null, 
	"COMMENT" varchar(4000) not null,
	constraint "TEST_SCHEMA"."PK_COMMENTS" primary key ("COMMENT_ID")
);

/*
Test one-to-one natural identity with simple key
*/
create table "TEST_SCHEMA"."TBL_PROFILE" (
    "PROFILE_ID" int not null,
	"FK_USERNAME" varchar(50) not null, 
	"SCREEN_NAME" varchar(100) not null, 
	constraint "TEST_SCHEMA"."PK_PROFILE" primary key ("PROFILE_ID")
);

/*
Test many-to-many self-relation
*/
create table "TEST_SCHEMA"."TBL_FRIEND" (
	"REQUESTER_ID" int not null, 
	"REQUESTEE_ID" int not null, 
	constraint "TEST_SCHEMA"."PK_FRIEND" primary key ("REQUESTER_ID", "REQUESTEE_ID")
);

/*
Test one-to-one self-relation
*/
create table "TEST_SCHEMA"."TBL_SPOUSE" (
	"SPOUSE_TO_ID" int not null, 
	"SPOUSE_FROM_ID" int not null, 
	constraint "TEST_SCHEMA"."PK_SPOUSE" primary key ("SPOUSE_TO_ID", "SPOUSE_FROM_ID")
);

/*
Constrained compound identity with simple properties only
*/
create table "TEST_SCHEMA"."TBL_PERSON" (
	"FIRST_NAME" varchar(50) not null, 
	"LAST_NAME" varchar(50) not null, 
	"BIRTHDAY" date not null,
	constraint "TEST_SCHEMA"."PK_PERSON" primary key ("FIRST_NAME", "LAST_NAME")
);

create table "TEST_SCHEMA"."TBL_CUSTOMER" (
	"FK_FIRST_NAME" varchar(50) not null, 
	"FK_LAST_NAME" varchar(50) not null, 
	"LOYALTY_POINTS" integer not null,
	constraint "TEST_SCHEMA"."PK_CUSTOMER" primary key ("FK_FIRST_NAME", "FK_LAST_NAME")
);

/*
Test one-to-one natural identity with compound key
*/
create table "TEST_SCHEMA"."TBL_PASSPORT" (
    "PASSPORT_NR" char(7) not null,
    "FK_FIRST_NAME" varchar(50) not null, 
	"FK_LAST_NAME" varchar(50) not null,
	"EXPIRY_DATE" date not null,
	constraint "TEST_SCHEMA"."PK_PASSPORT" primary key ("PASSPORT_NR") 
);

/*
Test component natural key with simple properties 
*/
create table "TEST_SCHEMA"."TBL_ADDRESS" (
    "ADDRESS_ID"  int not null,
    "HOUSE_NUMBER" char(5) not null, 
	"STREET_NAME" varchar(100) not null,
	"POST_CODE" char(10) not null,
	constraint "TEST_SCHEMA"."PK_ADDRESS" primary key ("ADDRESS_ID") 
);


/*
Test component natural key with many-to-one and simple properties 
*/
create table "TEST_SCHEMA"."TBL_CONSIGNMENT" (
    "CONSIGNMENT_ID"  int not null,
    "FK_FIRST_NAME" varchar(50) not null, 
	"FK_LAST_NAME" varchar(50) not null,
	"CONSIGNMENT_DATE" date not null,
--    "HOUSE_NUMBER" char(5) not null, 
	"FK_ADDRESS_ID"  int not null,
	constraint "TEST_SCHEMA"."PK_CONSIGNMENT" primary key ("CONSIGNMENT_ID") 
);

create table "TEST_SCHEMA"."TBL_TYPES" (
  "TYPE_1" INT not null,
  "TYPE_2" BOOLEAN not null,
  "TYPE_3" TINYINT not null,
  "TYPE_4" SMALLINT not null, 
  "TYPE_5" BIGINT not null,
  "TYPE_6" IDENTITY not null, 
  "TYPE_7" DECIMAL(10,2) not null, 
  "TYPE_8" DOUBLE not null,
  "TYPE_9" REAL not null, 
  "TYPE_10" TIME not null,
  "TYPE_12" DATE not null, 
  "TYPE_13" TIMESTAMP not null,
  "TYPE_14" BINARY(20) not null, 
  --"TYPE_15" OTHER not null, NB: CAN'T BE SUPPORTED YET
  "TYPE_16" VARCHAR(20) not null, 
  "TYPE_17" CHAR(20) not null, 
  "TYPE_18" BLOB(20) not null, 
  "TYPE_19" CLOB(20) not null,
  "TYPE_20" UUID(20) not null, 
  --"TYPE_21" ARRAY, NB: CAN'T BE SUPPORTED YET
  constraint "TEST_SCHEMA"."PK_TYPES" primary key ("TYPE_6") 
);

create table "TBL_NO_SCHEMA" (
  "NO_SCHEMA_ID" INT not null,
  "FLAG" BOOLEAN not null,
  "LABEL" VARCHAR(255) not null,
  "USERNAME" varchar(50) not null, 
  constraint "PK_NO_SCHEMA" primary key ("NO_SCHEMA_ID") 
);

/*
Create unique keys
*/
alter table "DBO"."TBL_USER"
    add constraint "DBO"."UNIQUE_TBL_USER_USERNAME" unique("USERNAME");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_EMPLOYEE_EMAIL" unique("EMAIL");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_EMPLOYEE_FIRST_NAME_LAST_NAME" unique("FIRST_NAME","LAST_NAME");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_EMPLOYEE_FK_USER_ID" unique("FK_USER_ID");
    
alter table "DBO"."TBL_EMPLOYEE_MENTOR"
    add constraint "DBO"."UNIQUE_EMPLOYEE_MENTOR_MENTOREE" unique("MENTOREE_ID");

alter table "DBO"."TBL_GROUP"
    add constraint "DBO"."UNIQUE_TBL_GROUP_GROUPNAME" unique("GROUPNAME");

alter table "DBO"."TBL_DESK_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_DESK_EMPLOYEE_FK_EMPLOYEE_ID" unique("FK_EMPLOYEE_ID");

alter table "DBO"."TBL_DESK_EMPLOYEE"
    add constraint "DBO"."UNIQUE_TBL_DESK_EMPLOYEE_FK_DESK_ID" unique("FK_DESK_ID");

alter table "DBO"."TBL_STAND_UMBRELLA"
    add constraint "DBO"."UNIQUE_TBL_STAND_UMBRELLA_FK_UMBRELLA_ID" unique("FK_UMBRELLA_ID");

alter table "TEST_SCHEMA"."TBL_PROFILE"
    add constraint "TEST_SCHEMA"."UNIQUE_PROFILE_USERNAME" unique("FK_USERNAME");

alter table "TEST_SCHEMA"."TBL_SPOUSE"
    add constraint "TEST_SCHEMA"."UNIQUE_SPOUSE_TO" unique("SPOUSE_TO_ID");

alter table "TEST_SCHEMA"."TBL_SPOUSE"
    add constraint "TEST_SCHEMA"."UNIQUE_SPOUSE_FROM" unique("SPOUSE_FROM_ID");    

alter table "TEST_SCHEMA"."TBL_PASSPORT"
    add constraint "TEST_SCHEMA"."UNIQUE_TBL_PASSPORT_FIRST_NAME_LAST_NAME" unique("FK_FIRST_NAME","FK_LAST_NAME");

alter table "TEST_SCHEMA"."TBL_ADDRESS"
    add constraint "TEST_SCHEMA"."UNIQUE_ADDRESS_HOUSE_NUMBER_STREET" unique("HOUSE_NUMBER","STREET_NAME");

alter table "TEST_SCHEMA"."TBL_CONSIGNMENT"
    add constraint "TEST_SCHEMA"."UNIQUE_CONSIGNMENT_CUSTOMER_DATE" unique("FK_FIRST_NAME","FK_LAST_NAME", "CONSIGNMENT_DATE");


/*
Create foreign keys
*/
alter table "DBO"."TBL_CONTRACT"
    add constraint "DBO"."FK_CONTRACT_EMPLOYEE" foreign key ("FK_EMPLOYEE_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."FK_EMPLOYEE_MANAGER" foreign key ("FK_MANAGER_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_EMPLOYEE"
    add constraint "DBO"."FK_EMPLOYEE_USER" foreign key ("FK_USER_ID") references "DBO"."TBL_USER"("USER_ID");

alter table "DBO"."TBL_EMPLOYEE_MENTOR"
    add constraint "DBO"."FK_EMPLOYEE_MENTOREE" foreign key ("MENTOREE_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_EMPLOYEE_MENTOR"
    add constraint "DBO"."FK_EMPLOYEE_MENTOR" foreign key ("MENTOR_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_UMBRELLA"
    add constraint "DBO"."FK_UMBRELLA_EMPLOYEE" foreign key ("FK_EMPLOYEE_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_GROUP_USER"
    add constraint "DBO"."FK_GROUP_USER_USER" foreign key ("FK_USER_ID") references "DBO"."TBL_USER"("USER_ID");

alter table "DBO"."TBL_GROUP_USER"
    add constraint "DBO"."FK_GROUP_USER_GROUP" foreign key ("FK_GROUP_ID") references "DBO"."TBL_GROUP"("GROUP_ID");

alter table "DBO"."TBL_DESK_EMPLOYEE"
    add constraint "DBO"."FK_DESK_EMPLOYEE_EMPLOYEE" foreign key ("FK_EMPLOYEE_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");

alter table "DBO"."TBL_DESK_EMPLOYEE"
    add constraint "DBO"."FK_DESK_EMPLOYEE_DESK" foreign key ("FK_DESK_ID") references "DBO"."TBL_DESK"("DESK_ID");

alter table "DBO"."TBL_STAND_UMBRELLA"
    add constraint "DBO"."FK_STAND_UMBRELLA_UMBRELLA" foreign key ("FK_UMBRELLA_ID") references "DBO"."TBL_UMBRELLA"("UMBRELLA_ID");

alter table "DBO"."TBL_STAND_UMBRELLA"
    add constraint "DBO"."FK_STAND_UMBRELLA_STAND" foreign key ("FK_STAND_ID") references "DBO"."TBL_STAND"("STAND_ID");

alter table "TEST_SCHEMA"."TBL_PROFILE"
    add constraint "TEST_SCHEMA"."FK_PROFILE_USER" foreign key ("FK_USERNAME") references "TEST_SCHEMA"."TBL_USER"("USERNAME");

alter table "TEST_SCHEMA"."TBL_FRIEND"
    add constraint "TEST_SCHEMA"."FK_FRIEND_REQUESTER" foreign key ("REQUESTER_ID") references "TEST_SCHEMA"."TBL_PROFILE"("PROFILE_ID");

alter table "TEST_SCHEMA"."TBL_FRIEND"
   add constraint "TEST_SCHEMA"."FK_FRIEND_REQUESTEE" foreign key ("REQUESTEE_ID") references "TEST_SCHEMA"."TBL_PROFILE"("PROFILE_ID");

alter table "TEST_SCHEMA"."TBL_SPOUSE"
    add constraint "TEST_SCHEMA"."FK_SPOUSE_TO" foreign key ("SPOUSE_TO_ID") references "TEST_SCHEMA"."TBL_PROFILE"("PROFILE_ID");

alter table "TEST_SCHEMA"."TBL_SPOUSE"
   add constraint "TEST_SCHEMA"."FK_SPOUSE_FROM" foreign key ("SPOUSE_FROM_ID") references "TEST_SCHEMA"."TBL_PROFILE"("PROFILE_ID");

 
alter table "TEST_SCHEMA"."TBL_TIMESHEET"
    add constraint "TEST_SCHEMA"."FK_TIMESHEET_EMPLOYEE" foreign key ("FK_EMPLOYEE_ID") references "DBO"."TBL_EMPLOYEE"("EMPLOYEE_ID");
    
alter table "TEST_SCHEMA"."TBL_ACTIVITY"
    add constraint "TEST_SCHEMA"."FK_ACTIVITY_TIMESHEET" foreign key ("FK_EMPLOYEE_ID","FK_DATE") references "TEST_SCHEMA"."TBL_TIMESHEET"("FK_EMPLOYEE_ID","DATE");

alter table "TEST_SCHEMA"."TBL_PARKING_PERMIT"
    add constraint "TEST_SCHEMA"."FK_PARKING_PERMIT_VEHICLE" foreign key ("FK_REG_NUMBER") references "TEST_SCHEMA"."TBL_VEHICLE"("REG_NUMBER");
 
alter table "TEST_SCHEMA"."TBL_PARKING_PERMIT_PAYMENT"
    add constraint "TEST_SCHEMA"."FK_PARKING_PERMIT_PAYMENT_PARKING_PERMIT" foreign key ("FK_REG_NUMBER", "FK_PARKING_PERMIT_DATE") references "TEST_SCHEMA"."TBL_PARKING_PERMIT"("FK_REG_NUMBER", "DATE");

alter table "TEST_SCHEMA"."TBL_CUSTOMER"
    add constraint "TEST_SCHEMA"."FK_CUSTOMER_PERSON" foreign key ("FK_FIRST_NAME", "FK_LAST_NAME") references "TEST_SCHEMA"."TBL_PERSON"("FIRST_NAME", "LAST_NAME");

alter table "TEST_SCHEMA"."TBL_PASSPORT"
    add constraint "TEST_SCHEMA"."FK_PASSPORT_PERSON" foreign key ("FK_FIRST_NAME", "FK_LAST_NAME") references "TEST_SCHEMA"."TBL_PERSON"("FIRST_NAME", "LAST_NAME");
   
alter table "TEST_SCHEMA"."TBL_CONSIGNMENT"
    add constraint "TEST_SCHEMA"."FK_CONSIGNMENT_CUSTOMER" foreign key ("FK_FIRST_NAME", "FK_LAST_NAME") references "TEST_SCHEMA"."TBL_CUSTOMER"("FK_FIRST_NAME", "FK_LAST_NAME");

alter table "TEST_SCHEMA"."TBL_CONSIGNMENT"
    add constraint "TEST_SCHEMA"."FK_CONSIGNMENT_ADDRESS" foreign key ("FK_ADDRESS_ID") references "TEST_SCHEMA"."TBL_ADDRESS"("ADDRESS_ID");
    
alter table "TEST_SCHEMA"."TBL_BUGS"
    add constraint "TEST_SCHEMA"."FK_BUGS_USER_OWNER" foreign key ("OWNER") references "TEST_SCHEMA"."TBL_USER"("USERNAME");
    
alter table "TEST_SCHEMA"."TBL_BUGS"
    add constraint "TEST_SCHEMA"."FK_BUGS_USER_REPORTER" foreign key ("REPORTER") references "TEST_SCHEMA"."TBL_USER"("USERNAME");       
 
alter table "TEST_SCHEMA"."TBL_COMMENTS"
    add constraint "TEST_SCHEMA"."FK_COMMENTS_BUGS" foreign key ("BUG_ID") references "TEST_SCHEMA"."TBL_BUGS"("BUG_ID");

alter table "TEST_SCHEMA"."TBL_COMMENTS"
    add constraint "TEST_SCHEMA"."FK_COMMENTS_USER" foreign key ("USERNAME") references "TEST_SCHEMA"."TBL_USER"("USERNAME");

alter table "TBL_NO_SCHEMA"
    add constraint "FK_NO_SCHEMA_USER" foreign key ("USERNAME") references "TEST_SCHEMA"."TBL_USER"("USERNAME");

