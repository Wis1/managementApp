CREATE TABLE projects (
                      "id" bigint generated by default as identity not null primary key ,
                      "uuid" uuid unique not null,
                      "name" varchar(255) unique not null,
                      "description" varchar(255),
                      "start_project" timestamp without time zone,
                      "end_project" timestamp without time zone,
                      "budget_project" decimal not null
);