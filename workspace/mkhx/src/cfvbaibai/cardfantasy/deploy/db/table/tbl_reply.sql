IF dbo.fn_util_table_exists('tbl_reply') = 0
BEGIN
	CREATE TABLE tbl_reply
	(
	    i_reply_to      INTEGER         NOT NULL,
	    nvc_sender_id   NVARCHAR(32)    NOT NULL,
	    dt_created      DATETIME2       NOT NULL DEFAULT GETUTCDATE(),
	    nvc_content     NVARCHAR(MAX)   NOT NULL
	);
	CREATE CLUSTERED INDEX CI_tbl_post ON tbl_reply (dt_created, i_reply_to);
END