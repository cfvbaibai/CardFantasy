@ECHO OFF
SETLOCAL

SET "SCRIPT_DIR=%~dp0%"

SET ENV=DEV

IF "%1" == "PROD" SET ENV=PROD
IF "%1" == "IDE" SET ENV=IDE
SHIFT

IF "%ENV%" == "PROD" GOTO :ProdEnvSetup
IF "%ENV%" == "DEV"  GOTO :DevEnvSetup
IF "%ENV%" == "IDE"  GOTO :IdeEnvSetup

:DevEnvSetup
:IdeEnvSetup
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

IF "%ENV%" == "IDE" CALL :InitSqlCmdForIde

CALL :CallSql "%SCRIPT_DIR%\util\fn_util_table_exists.sql"
CALL :CallSql "%SCRIPT_DIR%\util\fn_util_sp_exists.sql"

IF "%ENV%" == "PROD" GOTO :EndResetDB
CALL :CallSql "%SCRIPT_DIR%\reset_db.sql"
:EndResetDB

CALL :CallSql "%SCRIPT_DIR%\table\tbl_post.sql"
CALL :CallSql "%SCRIPT_DIR%\table\tbl_reply.sql"
CALL :CallSql "%SCRIPT_DIR%\sp\sp_com_new_post.sql"
CALL :CallSql "%SCRIPT_DIR%\sp\sp_com_new_reply.sql"
CALL :CallSql "%SCRIPT_DIR%\sp\sp_com_get_threads.sql"
CALL :CallSql "%SCRIPT_DIR%\sp\sp_com_get_thread_count.sql"

IF "%ENV%" == "PROD" GOTO :EndInitData
CALL :CallSql "%SCRIPT_DIR%\init_data.sql"
:EndInitData

:END
ENDLOCAL
@ECHO ON
@GOTO :EOF

:InitSqlCmdForIde
"%BIN_PATH%" -f 65001 >nul
GOTO :EOF

:CallSql
ECHO **
ECHO ** Executing SQL file: %1
ECHO **
"%BIN_PATH%" -S %SERVER_INSTANCE% -U ss_admin -P "%SQL_PASSWORD%" -d card_fantasy -i %1
GOTO :EOF