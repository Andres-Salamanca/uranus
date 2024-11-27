# uranusjave

## Uranus

### Introducción  
Este proyecto utiliza herramientas para la configuración automática de su entorno en Linux, macOS y Windows. A continuación, se detallan los pasos para ejecutar los scripts y preparar el sistema.
---
### Documentación
Para más detalles sobre la instalación y configuración en macOS, consulte el archivo **`Guía de Instalación en macOS.docx`**.  

Este documento ofrece instrucciones específicas y pasos detallados para solucionar posibles problemas durante la configuración en macOS.

---

### Archivos incluidos

#### **1. `install_brew_and_tools.sh`**
- **Propósito**: Instala Homebrew y herramientas necesarias en sistemas macOS.
- **Uso**: 
  ```bash
  bash install_brew_and_tools.sh
  ```
- Ejecutar este script antes de configurar el entorno en macOS.

#### **2. `setup.sh`**
- **Propósito**: Configura el entorno para Linux.
- **Uso**:
  ```bash
  bash setup.sh
  ```
- Asegúrese de que `conda` esté correctamente instalado o será instalado automáticamente.

#### **3. `setupMac.sh`**
- **Propósito**: Configura el entorno para macOS.
- **Uso**:
  ```bash
  bash setupMac.sh
  ```
- Diseñado específicamente para macOS con soporte para Conda.

#### **4. `setup.bat`**
- **Propósito**: Configura el entorno en sistemas Windows.
- **Uso**:
  - Abrir una terminal CMD y ejecutar:
    ```cmd
    setup.bat
    ```

---


### Notas adicionales
1. Asegúrese de tener permisos de ejecución en los scripts:
   ```bash
   chmod +x <nombre_del_script>
   ```
2. Si algún comando falla, verifique los requisitos previos como:
   - Tener instalado `bash` (para Linux/macOS).
   - Tener permisos de administrador en Windows.

### Soporte
Si encuentra problemas, no dude en contactarnos o revisar la documentación oficial del proyecto.





Proyecto Cambio Cognitivo Pontificia Universis Javeriana - Departamento de Psicología.

[Install]
apt-get install curl
curl -O https://repo.anaconda.com/archive/Anaconda3-2018.12-Linux-x86_64.sh
bash Anaconda3-2018.12-Linux-x86_64.sh
conda install django
conda install -c chen mod_wsgi
mod_wsgi-express module-config


apt-get install python3
/usr/bin/python3


[Solucion]
https://stackoverflow.com/questions/46759730/django-mod-wsgi-fatal-python-error-py-initialize-no-module-named-encodings


[Cosas]
/root/.bashrc
/root/anaconda3/etc/profile.d/conda.sh
LoadModule wsgi_module "/root/anaconda3/lib/python3.6/site-packages/mod_wsgi/server/mod_wsgi-py36.cpython-36m-x86_64-linux-gnu.so"
WSGIPythonHome "/root/anaconda3"


[Conf]

    # Admin email, Server Name (domain name) and any aliases
    ServerAdmin fjroldan09@gmail.com
    ServerAlias uranus.javeriana.edu.co

    Alias /static/ /var/www/app/uranus/task/static
    <Directory /var/www/app/uranus/task/static>
        Require all granted
    </Directory>

    WSGIDaemonProcess uranus python-path=/var/www/app/uranus python-home=/usr
    WSGIProcessGroup uranus
    WSGIScriptAlias / /var/www/app/uranus/uranus/wsgi.py

    <Directory "/var/www/app/uranus/uranus">
        <Files wsgi.py>
            Require all granted
        </Files>
    </Directory>

    ErrorLog /var/www/app/apache/error.log
    CustomLog /var/www/app/apache/access.log combined

    SSLEngine on
    SSLCertificateFile /var/www/app/ssl/server.crt
    SSLCertificateKeyFile /var/www/app/ssl/server.key
    #SSLCACertificateFile /var/www/app/ssl/intermediate.crt

a2enmod ssl
a2enmod rewrite

# Redirect to https
    RewriteEngine On
    RewriteCond %{SERVER_PORT} !^443$
    RewriteRule ^(.*)$ https://%{HTTP_HOST}$1 [R=301,L]



https://pawelzny.com/server/django/2018/02/26/the-most-complete-apache2-config-for-wsgi-django-and-drf/


