BEGIN TRANSACTION;
DROP TABLE IF EXISTS "setting";
CREATE TABLE setting ("ID" INTEGER PRIMARY KEY,"NAME" TEXT,"NUMBER" INTEGER DEFAULT  0,"EMAIL" TEXT,AGE INTEGER DEFAULT 0,B_DATE TEXT,ACTIVE INTEGER DEFAULT 0 ,NOTI INTEGER DEFAULT 0 ,SEX INTEGER  DEFAULT 0, APIID INTEGER  DEFAULT 0, "LANG" TEXT DEFAULT ar);
COMMIT;