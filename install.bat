@echo on
@echo =============================================================
@echo $                                                           $
@echo $               NM    					                  $
@echo $                                                           $
@echo $                                                           $
@echo $                                                           $
@echo $  NM  All Right Reserved                                   $
@echo $  Copyright (C) 2019-2050                                  $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title All install
@color 0e

call mvn clean install -Dmaven.test.skip=true

pause
