IF EXISTS ( SELECT  1
            FROM    INFORMATION_SCHEMA.ROUTINES
            WHERE   specific_name = 'fn_util_sp_exists' AND routine_type = 'FUNCTION' ) 
    DROP FUNCTION fn_util_sp_exists
GO 

CREATE FUNCTION fn_util_sp_exists
(
    @vc_sp_name VARCHAR(200)
)
RETURNS BIT
AS
BEGIN
    IF EXISTS ( SELECT 1 FROM INFORMATION_SCHEMA.ROUTINES
                WHERE specific_name = @vc_sp_name
                AND routine_type = 'PROCEDURE')
        RETURN 1;
    RETURN 0;
END;

GO