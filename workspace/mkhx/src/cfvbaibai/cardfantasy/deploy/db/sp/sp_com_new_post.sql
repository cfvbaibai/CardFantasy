IF dbo.fn_util_sp_exists('sp_com_new_post') = 1 DROP PROCEDURE sp_com_new_post;
GO

CREATE PROCEDURE sp_com_new_post
(
    @nvc_sender_id  NVARCHAR(32),
    @nvc_content    NVARCHAR(MAX),
    @i_new_id       INTEGER         OUTPUT
) AS
    DECLARE @i_max_id INTEGER;
    BEGIN TRANSACTION;
        SET @i_max_id = (SELECT MAX(i_id) FROM tbl_post);
        IF @i_max_id IS NULL BEGIN
            SET @i_new_id = 1
        END ELSE BEGIN
            SET @i_new_id = @i_max_id + 1;
        END

        INSERT INTO tbl_post (i_id, nvc_sender_id, nvc_content)
        VALUES (@i_new_id, @nvc_sender_id, @nvc_content);
    COMMIT TRANSACTION;
GO

GRANT EXECUTE ON [sp_com_new_post] TO [ss_svc]
GO