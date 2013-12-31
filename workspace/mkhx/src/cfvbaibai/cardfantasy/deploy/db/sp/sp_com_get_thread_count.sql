IF dbo.fn_util_sp_exists('sp_com_get_thread_count') = 1 DROP PROCEDURE sp_com_get_thread_count;
GO

CREATE PROCEDURE sp_com_get_thread_count (
	@i_count INTEGER OUTPUT 
) AS
BEGIN
	SELECT @i_count = COUNT(i_id) FROM tbl_post;
END
GO

GRANT EXECUTE ON [sp_com_get_thread_count] TO [ss_svc]
GO
