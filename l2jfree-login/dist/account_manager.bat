@echo off
SET OLDCLASSPATH=%CLASSPATH%
call setenv.bat

@java -Djava.util.logging.config.file=console.cfg com.l2jfree.loginserver.tools.accountmanager.AccountManager

SET CLASSPATH=%OLDCLASSPATH%
@pause
