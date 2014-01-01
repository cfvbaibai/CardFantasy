IF dbo.fn_util_table_exists('tbl_post') = 0
BEGIN
	CREATE TABLE tbl_post
	(
	    i_id            INTEGER         NOT NULL,
	    nvc_sender_id   NVARCHAR(32)    NOT NULL,
	    dt_created      DATETIME2       NOT NULL DEFAULT GETUTCDATE(),
	    nvc_content     NVARCHAR(MAX)   NOT NULL
	);
	CREATE CLUSTERED INDEX CI_dt_created ON tbl_post (dt_created);
	CREATE UNIQUE NONCLUSTERED INDEX IX_i_id ON tbl_post (i_id);
END