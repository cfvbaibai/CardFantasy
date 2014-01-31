Web Server: Tomcat 7.x
To deploy web server: simply export WAR file and deploy to webapps.

Database: MS SQL Server 2012
cfvbaibai.cardfantasy.deploy.db.setup.bat could be used to setup database. Usage:

setup.bat PROD

Please read source code for how it works.
setup.bat PROD assumes that
1. (local) instance exists as the PROD environment.
2. SA authentication is enabled.
3. ss_admin is created with the sysadmin role.
4. ss_cfsvc is created with the public role.
5. Please copy WebContent/WEB_INF/env_template.xml as env.xml and adjust the password.

setup.bat DEV assumes that
1. (local)\MSSQLSERVER_DEV instance exists as the DEV environment.
2. SA authentication is enabled.
3. ss_admin is created with the sysadmin role.
4. ss_cfsvc is created with the public role.
5. Please copy WebContent/WEB_INF/env_template.xml as env_dev.xml and adjust the password.