# FinancialNewsSearchEngine

## Configuration

Before running the web app, you first need to download [`apache-nutch-2.3.1`](http://www.apache.org/dyn/closer.lua/nutch/2.3.1/apache-nutch-2.3.1-src.tar.gz), [`hbase-0.98.8-hadoop2`](https://archive.apache.org/dist/hbase/hbase-0.98.8/) and [`solr-4.10.3`](http://archive.apache.org/dist/lucene/solr/4.10.3/) (I encourage you to download all of the three programs to the folder where this README resides, i.e., at the same level as the folder `app/`).  It's possible that other versions of the above mentioned software also work together without problems of compatibility, but these versions are the [suggested ones](https://nutch.apache.org/#21-january-2016-nutch-231-release) to work together by the Nutch's creators. I decided to pick the lastest version of Nutch (2.X), which is still distributed only as source code which can be compiled, because I was interested in having some flexibility in choosing a separate database to store the crawled data, which Nutch 2.X provides quite nicely.

Once you have those 3 programs, you should replace the files that you find in [`configuration/`](configuration) for each of the programs above with their corresponding original version. If you don't do this, "you will probably not be able to run anything"... Note that I've left the structure of the folders the same as the original, but I kept only the required folders and files.

## Useful tutorials

These are tutorials that I encourage you to follow before proceeding with the next section.


- [https://wiki.apache.org/nutch/NutchTutorial](https://wiki.apache.org/nutch/NutchTutorial) (for Nutch 1.X)


- [https://wiki.apache.org/nutch/Nutch2Tutorial](https://wiki.apache.org/nutch/Nutch2Tutorial) (for Nutch 2.X)

- [http://hbase.apache.org/book.html#quickstart](http://hbase.apache.org/book.html#quickstart)


## Commands

### Nutch, HBase and Solr 

If you have already replaced the files I've mentioned in the section "configuration", then, in theory, you're able to build nutch from the source code. Before that, make sure you have [`ant`](http://ant.apache.org/) installed on your operating system. If you're on a Mac OS X and you have `Homebrew` installed, you can simply do `brew install ant`.

Once you have `ant` installed, you can build nutch with the following commands:

    ant clean
    ant runtime
    
This should take less than 1 minute. Once that's finished, you need to start `hbase`. Enter inside the folder `hbase-0.98.8-hadoop2` and type:

    bin/start-hbase.sh

Once that's done, you should also start the search platform `solr`. To do that, go inside the folder you downloaded and unzipped `solr-4.10.3` and type the following:

    bin/solr start -c -dir example
    
You should see a message similar to:

>Waiting to see Solr listening on port 8983 [/]  
Started Solr server on port 8983 (pid=38649). Happy searching!

You can keep track of this `pid=38649` and eventually kill the solr process when you're done with the searching with the following command:

    kill 38649

After that you can finally _crawl_ data (starting from the seeds/URLs that you should have copied to the nutch folder) and _inject_ it into the database. To do it, run the following commands (in order):

1. Inject the seeds (i.e., initial URLs) into the database

        runtime/local/bin/nutch inject seeds


2. Selects the top 1000 URLs

        runtime/local/bin/nutch generate -topN 1000

3. Downloads the documents corresponding to the selected URLs

        runtime/local/bin/nutch fetch -all


4. Parses the downloaded documents

        runtime/local/bin/nutch parse -all

5. Updates the database with the new parsed documents

        runtime/local/bin/nutch updatedb -all


Once that's finished, you can pass the crawled that to Solr to be indexed with the following command:

    runtime/local/bin/nutch solrindex http://localhost:8983/solr -all

After all these steps, if you haven't encountered any problems, you can go to the next section and run the web app!


### Web app

All of the following commands and explanations assume that you're on a unix-like system and that you have `Java` and `maven` installed. Currently I'm using `Java 1.8.0_40` and `Apache Maven 3.3.9`.

If you want to run this app from _Intellij IDEA_, you could have problems because of the resources managed by _wro4j_ are not downloaded.  I've not tried to run this app from `Eclipse`, but apparently there's a plug-in for Eclipse for _wro4j_, so I guess you can safely run the app from Eclipse. Anyway, I advise you to use the following commands from the terminal to run the app, which must be run from inside the folder [`app/`](./app/).

#### Clean and install the app

    mvn clean install
    
    
#### Run the SpringBoot app

    mvn spring-boot:run
    
#### Access the app from the browser

After the execution of the command `mvn spring-boot:run`, go to your favourite browser and type the following URL:

    localhost:3000
        
## Scripts

I've created a script [`build.sh`](./build) which basically automates the tasks described in the sections "configuration" and "commands". If you have any problems running it, do not hesitate to contact me.
        
## Problems

This section describes some problems that I've faced while setting up Nutch, HBase, Solr, etc.


### java.lang.ClassNotFoundException: org.apache.gora.hbase.store.HBaseStore
    
 - [http://stackoverflow.com/a/25830910/3924118](http://stackoverflow.com/a/25830910/3924118)
    
## Authors

&copy; Nelson Brochado, 2016.
    
