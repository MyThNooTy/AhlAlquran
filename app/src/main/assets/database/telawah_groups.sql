BEGIN TRANSACTION;
DROP TABLE IF EXISTS "telawah_groups";
CREATE TABLE "telawah_groups" ("ID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "gname" VARCHAR, "gadmin" INTEGER, "gtype" INTEGER, "rtype" INTEGER, "sdate" DOUBLE, "edate" DOUBLE, "ndays" VARCHAR, "sfrom" DOUBLE DEFAULT 0, "sto" DOUBLE DEFAULT 0, "pagescount" INTEGER DEFAULT 0, "status" INTEGER DEFAULT 1, "agetype" INTEGER DEFAULT 1, "apiid" INTEGER DEFAULT 0);
COMMIT;