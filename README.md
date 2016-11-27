# FinancialNewsSearchEngine

## Commands

All of the following commands and explanations assume that you're on a unix-like system and that you have `Java` and `maven` installed. Currently I'm using `Java 1.8.0_40` and `Apache Maven 3.3.9`.

If you want to run this app from _Intellij IDEA_, you could have problems because of the resources managed by _wro4j_ are not downloaded.  I've not tried to run this app from `Eclipse`, but apparently there's a plug-in for Eclipse for _wro4j_, so I guess you can safely run the app from Eclipse. Anyway, I advise you to use the following commands from the terminal.

All the following commands must be run from inside the folder [`app/`](./app/).

### Clean and install the app

    mvn clean install
    
    
### Run the SpringBoot app

    mvn spring-boot:run
    
### Access the app from the browser

After the execution of the command `mvn spring-boot:run`, go to your favourite browser and type the following URL:

    localhost:3000
    
## Authors

&copy; Nelson Brochado, 2016.
    
