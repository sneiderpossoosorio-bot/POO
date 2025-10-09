@echo off
echo Descargando dependencias de Apache PDFBox para generar PDFs...

mkdir lib 2>nul

echo Descargando Apache PDFBox 2.0.29...
curl -L -o lib/pdfbox-2.0.29.jar "https://repo1.maven.org/maven2/org/apache/pdfbox/pdfbox/2.0.29/pdfbox-2.0.29.jar"

echo Descargando FontBox 2.0.29...
curl -L -o lib/fontbox-2.0.29.jar "https://repo1.maven.org/maven2/org/apache/pdfbox/fontbox/2.0.29/fontbox-2.0.29.jar"

echo Descargando Apache Commons Logging 1.2...
curl -L -o lib/commons-logging-1.2.jar "https://repo1.maven.org/maven2/commons-logging/commons-logging/1.2/commons-logging-1.2.jar"

echo.
echo Dependencias de Apache PDFBox descargadas en la carpeta 'lib'
echo Para compilar con PDF usa: javac -cp "lib/*;." src/main/java/com/mycompany/appnequi/*.java -d target/classes
echo Para ejecutar con PDF usa: java -cp "target/classes;lib/*" com.mycompany.appnequi.AppNequi
echo.
echo Apache PDFBox es una libreria open source mas ligera que iText
echo Genera PDFs profesionales con excelente calidad
echo.
pause
