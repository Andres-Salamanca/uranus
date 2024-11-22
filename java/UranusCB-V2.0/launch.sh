echo "Current directory: $(pwd)"
echo "Attempting to cd to dist..."
ls -l
cd ../java/UranusCB-V2.0/
java -jar target/Uranus-V2.0-jar-with-dependencies.jar
