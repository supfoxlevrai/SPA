@echo off
echo === Compilations des fichiers Java... ===
javac -d bin -sourcepath src src/spa/client/*.java src/spa/server/*.java src/spa/model/*.java

echo === Generation des JARs... ===
jar cfe SpaServer.jar spa.server.SpaServerApp -C bin .
jar cfe SpaClient.jar spa.client.SpaClient -C bin .

echo === Mise a jour des dossiers de deploiement... ===
copy /Y SpaServer.jar src\SPA_LAUNCH\SERVER\

copy /Y SpaClient.jar src\SPA_LAUNCH\CLIENT\

copy /Y SpaServer.jar C:\php\AutoTools\script_java\spa\
copy /Y SpaClient.jar C:\php\AutoTools\script_java\spa\

echo === Termine avec succes ! ===
pause 