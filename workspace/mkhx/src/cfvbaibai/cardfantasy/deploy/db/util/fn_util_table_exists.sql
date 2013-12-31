IF EXISTS ( SELECT  1
            FROM    INFORMATION_SCHEMA.ROUTINES
            WHERE   specific_name = 'fn_util_table_exists' AND routine_type = 'FUNCTION' ) 
    DROP FUNCTION fn_util_table_exists
GO 

CREATE FUNCTION fn_util_table_exists
(
    @vc_tbl_name VARCHAR(200)
)
RETURNS BIT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = @vc_tbl_name) RETURN 1;
    RETURN 0;
END;

GO