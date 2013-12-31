IF dbo.fn_util_sp_exists('sp_com_get_threads') = 1 DROP PROCEDURE sp_com_get_threads;
GO

CREATE PROCEDURE sp_com_get_threads (
	@i_begin INTEGER,
	@i_end INTEGER
) AS
BEGIN
	SELECT
		i_id,
		nvc_sender_id,
		dt_created,
		nvc_content
	FROM tbl_post
	ORDER BY dt_created DESC
	OFFSET (@i_begin) ROWS FETCH NEXT (@i_end - @i_begin) ROWS ONLY;

	WITH post(i_id) AS (
		SELECT i_id FROM tbl_post
		ORDER BY dt_created DESC
		OFFSET (@i_begin) ROWS FETCH NEXT (@i_end - @i_begin) ROWS ONLY
	)
	SELECT * FROM tbl_reply INNER JOIN post ON tbl_reply.i_reply_to = post.i_id;
END
GO

GRANT EXECUTE ON [sp_com_get_threads] TO [ss_svc]
GO
