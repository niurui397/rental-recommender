Steps to run the project:

Prerequisite:
1. maven
2. python
3. nodeJS
3. install scikit-learn through: pip install -U scikit-learn

Optional:
Create mysql database to store the dataset and trained coefficients.
Run the "createDatabaseAndTable.sql" scripts in your mysql server to create database rental_data and table 'RENTAL_DATA' and 'COEFFICIENTS'. The application assumes your local mysql is using port 3306 and username is 'root', password is 'root'

Step 1 -- Build the REST web service
(1) In the folder where all files sits, run "mvn clean install", this is going to download all dependencies including Spring Boot for REST
(2) run "mvn package && java -jar target\rental-recommendation-service-0.0.1.jar". This is going to creat the jar file and start the REST server. (http://localhost:8080). The REST endpoint for getting recommendation is /recommendation, with parameters "bedroomCount", "bathroomCount" and "squareFeet"

Note: dataset will be loaded into mysql database during server startup.

Step 2 -- run "python recommender.py" to train the dataset using linear regression. The result is stored in 'coefficients.txt' and will be picked up by the application in the first time when the coefficients is not saved in mysql table "COEFFICIENTS". Later request will go to database to look for trained coefficients.

Step 3 -- Start client
(1) cd into "site" folder, run "npm install http-server -g" to install a simple HTTP server
(2) run "http-server", you will see the following

Starting up http-server, serving ./
Available on:
  http://10.2.76.169:8081
  http://10.2.113.39:8081
  http://192.168.56.1:8081
  http://192.168.99.1:8081
  http://172.28.128.1:8081
  http://127.0.0.1:8081

(3) Go to your browser and go to link http://127.0.0.1:8081/RentalRecomendation.html, you will see the webpage. (Of course, you can open RentalRecomendation.html in the browser directly without starting this simple HTTP server)

Note: The running example are included in "screenshots" folder


