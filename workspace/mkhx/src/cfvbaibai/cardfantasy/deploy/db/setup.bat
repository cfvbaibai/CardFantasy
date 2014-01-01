@ECHO OFF
SETLOCAL

SET "SCRIPT_DIR=%~dp0%"

SET CONN_ENV=DEV
SET RESET_SQLCMD_CODEPAGE=0
SET RESET_DB=0
SET INIT_DB=0

IF "%1" == "PROD" (
    SET CONN_ENV=PROD
    IF "%2" == "" GOTO :Usage
)
IF "%1" == "IDE" (
	SET RESET_SQLCMD_CODEPAGE=1
	SET RESET_DB=1
	SET INIT_DB=1
)
IF "%1" == "IDE_SIM_PROD" (
	SET RESET_SQLCMD_CODEPAGE=1
)
SHIFT

IF "%CONN_ENV%" == "PROD" GOTO :ProdEnvSetup
IF "%CONN_ENV%" == "DEV"  GOTO :DevEnvSetup

:DevEnvSetup
SET SQL_USER=ss_admin
SET SQL_PASSWORD=xtxtxtxt
SET SERVER_INSTANCE=LOCALHOST\MSSQLSERVER_DEV
GOTO :EndEnvSetup

:ProdEnvSetup
SET SQL_USER=ss_admin
SET SQL_PASSWORD=%1
SET SERVER_INSTANCE=LOCALHOST
GOTO :EndEnvSetup

:EndEnvSetup 
 
SET "BIN_PATH=C:\Program Files\Microsoft SQL Server\110\Tools\Binn\SQLCMD.exe"

IF "%RESET_SQLCMD_CODEPAGE%" == "1" "%BIN_PATH%" -f 65001 >nul

CALL :CallSql "%SCRIPT_DIR%\util\fn_util_table_exists.sql"
CALL :CallSql "%SCRIPT_DIR%\util\fn_util_sp_exists.sql"

IF "%RESET_DB%" == "1" CALL :CallSql "%SCRIPT_DIR%\reset_db.sql"

CALL :CallSql "%SCRIPT_DIR%\table\tbl_post.sql"
CALL :CallSql "%SCRIPT_DIR%\table\tbl_reply.sql"
CALL :CallSql "%SCRIPT_DIR%\table\tbl_stat_boss_battle.sql"
CALL :CallSql "%SCRIPT_DIR%\sp\sp_com_new_post.sql"
CALL :CallSql "%SCRIPT_DIR%\sp\sp_com_new_reply.sql"
CALL :CallSql "%SCRIPT_DIR%\sp\sp_com_get_threads.sql"
CALL :CallSql "%SCRIPT_DIR%\sp\sp_com_get_thread_count.sql"
CALL :CallSql "%SCRIPT_DIR%\sp\sp_stat_new_boss_battle_entry.sql"

IF "%INIT_DB%" == "1" CALL :CallSql "%SCRIPT_DIR%\init_data.sql"

:END
ENDLOCAL
@ECHO ON
@GOTO :EOF

:CallSql
ECHO **
ECHO ** Executing SQL file: %1
ECHO **
"%BIN_PATH%" -S %SERVER_INSTANCE% -U ss_admin -P "%SQL_PASSWORD%" -d card_fantasy -i %1
GOTO :EOF

:Usage
ECHO **
ECHO ** Usage: setup PROD ^<password^>
ECHO **
GOTO :End