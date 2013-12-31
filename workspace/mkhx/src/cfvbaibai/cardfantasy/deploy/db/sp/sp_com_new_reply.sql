IF dbo.fn_util_sp_exists('sp_com_new_reply') = 1 DROP PROCEDURE sp_com_new_reply;
GO

CREATE PROCEDURE sp_com_new_reply
(
	@i_reply_to		INTEGER,
	@nvc_sender_id	NVARCHAR(32),
	@nvc_content	NVARCHAR(MAX)
) AS
BEGIN
	INSERT INTO tbl_reply (i_reply_to, nvc_sender_id, nvc_content)
	VALUES (@i_reply_to, @nvc_sender_id, @nvc_content);
END;
GO

GRANT EXECUTE ON [sp_com_new_reply] TO [ss_svc]
GO