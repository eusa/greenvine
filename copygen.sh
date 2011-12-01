echo "Removing old files and copying generated source from generator-impl/target/greenvine/"
rm -rf generated/src/
rm -rf generated/target/
cp -R generator-impl/target/greenvine/src/ generated
chmod -R 777 generated/*
