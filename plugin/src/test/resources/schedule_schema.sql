DROP ALL OBJECTS;

create table "EMPLOYEE" (
	"NORMAL_HOURS" DECIMAL (4,1) NOT NULL,
	"EMAIL" VARCHAR (255) NOT NULL,
	"EMPLOYEE_ID" INTEGER NOT NULL,
	"FIRST_NAME" VARCHAR (255) NOT NULL,
	"LAST_NAME" VARCHAR (255) NOT NULL,
	"USER_ID" INTEGER NOT NULL,
	"NORMAL_HOURLY_COST" DECIMAL (19,4) NOT NULL,
	"OVERTIME_HOURLY_COST" DECIMAL (19,4) NOT NULL
);

create table "GROUP" (
	"GROUPNAME" VARCHAR (255) NOT NULL,
	"GROUP_ID" INTEGER NOT NULL
);

create table "GROUP_USER" (
	"GROUP_ID" INTEGER NOT NULL,
	"USER_ID" INTEGER NOT NULL
);

create table "USER" (
	"PASSWORD" VARCHAR (255) NOT NULL,
	"USERNAME" VARCHAR (255) NOT NULL,
	"USER_ID" INTEGER NOT NULL
);

create table "PROJECT" (
	"NAME" VARCHAR (255) NOT NULL,
	"PROJECT_GROUP_ID" INTEGER NOT NULL,
	"PROJECT_ID" INTEGER NOT NULL
);

create table "PROJECT_GROUP" (
	"NAME" VARCHAR (255) NOT NULL,
	"PROJECT_GROUP_ID" INTEGER NOT NULL
);

create table "BOOKING" (
	"BOOKING_ID" INTEGER NOT NULL,
	"DESCRIPTION" VARCHAR (255) NOT NULL,
	"DAY_ID" INTEGER NOT NULL,
	"PROJECT_ID" INTEGER NOT NULL,
	"OWNER" INTEGER NOT NULL,
	"HOURS" DECIMAL (4,1) NOT NULL
);

create table "DAY" (
    "DAY_ID" INTEGER NOT NULL,
	"DATE" DATE NOT NULL,
	"EMPLOYEE_ID" INTEGER NOT NULL,
	"NORMAL_HOURS" DECIMAL (4,1) NOT NULL,
	"OVERTIME_HOURS" DECIMAL (4,1) NOT NULL,
	"NORMAL_HOURLY_COST" DECIMAL (19,4) NOT NULL,
	"OVERTIME_HOURLY_COST" DECIMAL (19,4) NOT NULL,
);

-- PRIMARY KEYS

alter table "EMPLOYEE"
    add constraint "PK_EMPLOYEE" primary key ("EMPLOYEE_ID");

alter table "GROUP"
    add constraint "PK_GROUP" primary key ("GROUP_ID");

alter table "GROUP_USER"
    add constraint "PK_GROUP_USER" primary key ("GROUP_ID","USER_ID");

alter table "USER"
    add constraint "PK_USER" primary key ("USER_ID");
    
alter table "PROJECT_GROUP"
    add constraint "PK_PROJECT_GROUP" primary key ("PROJECT_GROUP_ID");

alter table "PROJECT"
    add constraint "PK_PROJECT" primary key ("PROJECT_ID");

alter table "BOOKING"
    add constraint "PK_BOOKING" primary key ("BOOKING_ID");

alter table "DAY"
    add constraint "PK_DAY" primary key ("DAY_ID");

-- UNIQUE KEYS

alter table "EMPLOYEE"
    add constraint "UK_EMPLOYEE_EMAIL_UNIQUE" unique ("EMAIL");

alter table "EMPLOYEE"
    add constraint "UK_EMPLOYEE_USER_ID_UNIQUE" unique ("USER_ID");

alter table "GROUP"
    add constraint "UK_GROUP_GROUPNAME_UNIQUE" unique ("GROUPNAME");

alter table "USER"
    add constraint "UK_USER_USERNAME_UNIQUE" unique ("USERNAME");
    
alter table "PROJECT_GROUP"
    add constraint "UK_PROJECT_GROUP_NAME_UNIQUE" unique ("NAME");

alter table "PROJECT"
    add constraint "UK_PROJECT_PROJECT_GROUP_NAME_UNIQUE" unique ("PROJECT_GROUP_ID", "NAME");    

alter table "DAY"
    add constraint "UK_DAY_DATE_EMPLOYEE_UNIQUE" unique ("EMPLOYEE_ID", "DATE");     

-- FOREIGN KEYS

alter table "EMPLOYEE"
    add constraint "EMPLOYEE_USER" foreign key ("USER_ID") references "USER"("USER_ID");

alter table "GROUP_USER"
    add constraint "GROUP_USER_GROUP" foreign key ("GROUP_ID") references "GROUP"("GROUP_ID");

alter table "GROUP_USER"
    add constraint "GROUP_USER_USER" foreign key ("USER_ID") references "USER"("USER_ID");

alter table "PROJECT"
    add constraint "PROJECT_PROJECT_GROUP" foreign key ("PROJECT_GROUP_ID") references "PROJECT_GROUP"("PROJECT_GROUP_ID");

alter table "DAY"
    add constraint "DAY_EMPLOYEE" foreign key ("EMPLOYEE_ID") references "EMPLOYEE"("EMPLOYEE_ID");

alter table "BOOKING"
    add constraint "BOOKING_DAY" foreign key ("DAY_ID") references "DAY"("DAY_ID");

alter table "BOOKING"
    add constraint "BOOKING_PROJECT" foreign key ("PROJECT_ID") references "PROJECT"("PROJECT_ID");

