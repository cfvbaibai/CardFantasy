IF dbo.fn_util_table_exists('tbl_post') = 1 DROP TABLE tbl_post;
IF dbo.fn_util_table_exists('tbl_reply') = 1 DROP TABLE tbl_reply;
-- We have PROD replica in this DB. Do not delete it.
-- IF dbo.fn_util_table_exists('tbl_stat_boss_battle') = 1 DROP TABLE tbl_stat_boss_battle;
