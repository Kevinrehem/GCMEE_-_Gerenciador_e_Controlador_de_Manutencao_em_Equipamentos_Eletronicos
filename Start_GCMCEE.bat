@echo off
title Lançador de Aplicação Full-Stack

echo ===================================
echo  INICIANDO BACKEND SPRING BOOT...
echo ===================================
REM Abre uma nova janela para o Spring Boot
START "Spring Backend" cmd /k "cd backend && java -jar target/onboardingsites-0.0.1-SNAPSHOT.jar"

echo ===================================
echo  INICIANDO FRONTEND REACT...
echo ===================================
REM Abre uma nova janela para o React
START "React Frontend" cmd /k "cd frontend && npm run dev"

echo.
echo Processos iniciados em novas janelas!

REM Fecha este script
exit