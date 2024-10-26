--<ScriptOptions statementTerminator=";"/>

ALTER TABLE "user_management"."t_user" DROP CONSTRAINT "t_user_pkey";

DROP INDEX "user_management"."t_user_pkey";

DROP INDEX "user_management"."t_user_c_username_key";

DROP TABLE "user_management"."t_user";

CREATE TABLE "user_management"."t_user" (
		"id" SERIAL DEFAULT nextval('user_management.t_user_id_seq'::regclass) NOT NULL,
		"c_username" VARCHAR(2147483647) NOT NULL,
		"c_password" VARCHAR(2147483647)
	);

CREATE UNIQUE INDEX "user_management"."t_user_pkey" ON "user_management"."t_user" ("id" ASC);

CREATE UNIQUE INDEX "user_management"."t_user_c_username_key" ON "user_management"."t_user" ("c_username" ASC);

ALTER TABLE "user_management"."t_user" ADD CONSTRAINT "t_user_pkey" PRIMARY KEY ("id");

