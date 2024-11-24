@echo off
setlocal enabledelayedexpansion

REM Detectar si Conda ya está instalado
where conda >nul 2>nul
if %errorlevel% equ 0 (
    echo Conda ya esta instalado.
    goto activate_env
) else (
    echo Conda no esta instalado. Procediendo con la instalacion...
)

REM Descargar e instalar Miniconda
curl https://repo.anaconda.com/miniconda/Miniconda3-latest-Windows-x86_64.exe -o miniconda.exe
if %errorlevel% neq 0 (
    echo Error al descargar Miniconda. Verifica tu conexión a internet.
    exit /b 1
)

start /wait "" .\miniconda.exe /S /D=%USERPROFILE%\Miniconda3
if %errorlevel% neq 0 (
    echo Error al instalar Miniconda.
    del miniconda.exe
    exit /b 1
)


echo Miniconda instalado exitosamente.
REM Agregar Miniconda al PATH temporalmente para esta sesión
set "PATH=%USERPROFILE%\Miniconda3;%USERPROFILE%\Miniconda3\Scripts;%USERPROFILE%\Miniconda3\Library\bin;%PATH%"



REM Verificar la instalación de Conda
where conda >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: No se pudo encontrar Conda después de la instalacion.
    exit /b 1
)

:activate_env
echo Creando el entorno Conda...
conda env create -f environment.yml
if %errorlevel% neq 0 (
    echo Error al crear el entorno Conda.
    exit /b 1
)

echo Activando el entorno Conda...
call conda activate uranus_env
if %errorlevel% neq 0 (
    echo Error al activar el entorno Conda.
    exit /b 1
)

REM Continuar con los comandos de configuración de la aplicación
echo Configurando la aplicacion...
REM Aquí puedes agregar comandos específicos para tu aplicación, por ejemplo:
cd uranus\
python manage.py migrate
python manage.py createsuperuser
python install.py
cd ..\java\UranusCB-V2.0\
mvn clean package
chmod +x launch.sh
cd ..\uranus\
python manage.py runserver

echo Configuración completa. Servidor iniciado exitosamente.
exit /b 0

