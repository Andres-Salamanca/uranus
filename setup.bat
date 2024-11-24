@echo off
setlocal enabledelayedexpansion

REM Detectar si Conda ya está instalado
where conda >nul 2>nul
if %errorlevel% equ 0 (
    echo Conda ya esta instalado.
    goto activate_env
) else (
    echo Conda no esta instalado. Por favor instalar https://docs.anaconda.com/miniconda/install/
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
chmod +x launch.cmd
cd ..\uranus\
python manage.py runserver

echo Configuracion completa. Servidor iniciado exitosamente.
exit /b 0

