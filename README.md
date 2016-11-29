# FinancialNewsSearchEngine

All of the following commands and explanations assume that you're on a unix-like system and that you have `Java` and `maven` installed. Currently I'm using `Java 1.8.0_40` and `Apache Maven 3.3.9`. If you're on Windows and you're stuck, feel free to <a href="mailto:nelson.brochado@outlook.com">contact me</a>.

Software you will _absolutely_ need to download is:

1. [`apache-nutch-2.3.1`](http://www.apache.org/dyn/closer.lua/nutch/2.3.1/apache-nutch-2.3.1-src.tar.gz) (source distribution, since at the time of this writing there's still no binary one)

2. [`hbase-0.98.8-hadoop2-bin.tar.gz`](https://archive.apache.org/dist/hbase/hbase-0.98.8/)

3. [`solr-4.10.3.zip`](http://archive.apache.org/dist/lucene/solr/4.10.3/)

4. [`ant`](http://ant.apache.org/)

Other software you may _eventually_ need while reading this _READAME_ are:

1. [`wget`](https://www.gnu.org/software/wget/)

2. [`tar`](https://www.gnu.org/software/tar/)

3. [`unzip`](http://www.info-zip.org/)

4. [`tor`](https://www.torproject.org/)

5. [`torsocks`](https://github.com/dgoulet/torsocks)

6. [`pkill`](https://www.freebsd.org/cgi/man.cgi?query=pkill&sektion=1)

Notice that you may have already some of these programs installed on your system. 

If you're on a Mac OS X, you can install most of these programs using for example the package manager _Homebrew_. On Linux the package manager may be different but the process should be the same.

_I've created a script [`build.sh`](./build.sh) which basically automates the tasks described in the sections *configuration* and *commands*. This script is still not robuts but in theory could avoid you to read the sections I've just mentioned and simply acess the web app at [http://127.0.0.1:3000](http://127.0.0.1:3000) You can run the script as follows:_

    source ./build.sh
    start

_You can't simply do `./build.sh`  because there's no function that's called directly, instead you have the freedom and flexibility to choose the one that you need. For example, you may just want to rebuild nutch and pass the crawled data to solr again. In that case, simply call:_

    nutch
    
_provided that you have already executed `source ./build.sh`. Check the source code of `./build.sh` to know more about the specific functions you can call._  

## Tutorials

These are tutorials that I encourage you to follow before proceeding with the next section, if you are not familiar with Nutch, HBase and Solr.


- [https://wiki.apache.org/nutch/NutchTutorial](https://wiki.apache.org/nutch/NutchTutorial) (for Nutch 1.X)


- [https://wiki.apache.org/nutch/Nutch2Tutorial](https://wiki.apache.org/nutch/Nutch2Tutorial) (for Nutch 2.X)

- [http://hbase.apache.org/book.html#quickstart](http://hbase.apache.org/book.html#quickstart)

- [http://lucene.apache.org/solr/quickstart.html](http://lucene.apache.org/solr/quickstart.html)

## Help


- [http://stackoverflow.com/search?q=nutch](http://stackoverflow.com/search?q=nutch)

- [http://stackoverflow.com/search?q=solr](http://stackoverflow.com/search?q=solr)


## Configuration

Before running the web app, you first need to download [`apache-nutch-2.3.1`](http://www.apache.org/dyn/closer.lua/nutch/2.3.1/apache-nutch-2.3.1-src.tar.gz), [`hbase-0.98.8-hadoop2`](https://archive.apache.org/dist/hbase/hbase-0.98.8/) and [`solr-4.10.3`](http://archive.apache.org/dist/lucene/solr/4.10.3/) (I encourage you to download all of the three programs to the folder where this README resides, i.e., at the same level as the folder `app/`).  It's possible that other versions of the above mentioned software also work together without problems of compatibility, but these versions are the [suggested ones](https://nutch.apache.org/#21-january-2016-nutch-231-release) to work together by the Nutch's creators. I decided to pick the lastest version of Nutch (2.X), which is still distributed only as source code which can be compiled, because I was interested in having some flexibility in choosing a separate database to store the crawled data, which Nutch 2.X provides quite nicely.

Once you have those 3 programs, you should replace the files that you find in [`configuration/`](configuration) for each of the programs above with their corresponding original version. If you don't do this, "you will probably not be able to run anything"... Note that I've left the structure of the folders the same as the original, but I kept only the required folders and files.

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


Once that's finished, you can pass the crawled data to Solr to be indexed with the following command:

    runtime/local/bin/nutch solrindex http://localhost:8983/solr -all

After all these steps, if you haven't encountered any problems, you can go to the next section and run the web app!


### Web app

If you want to run this app from _Intellij IDEA_, you could have problems because of the resources managed by _wro4j_ are not downloaded.  I've not tried to run this app from `Eclipse`, but apparently there's a plug-in for Eclipse for _wro4j_, so I guess you can safely run the app from Eclipse. Anyway, I advise you to use the following commands from the terminal to run the app, which must be run from inside the folder [`app/`](./app/).

#### Clean and install the app

    mvn clean install
    
    
#### Run the SpringBoot app

    mvn spring-boot:run
    
#### Access the app from the browser

After the execution of the command `mvn spring-boot:run`, go to your favourite browser and type the following URL:

    localhost:3000
        
## Approach and Design

I've decided to use Nutch version 2 because, as I explained above, I was interested in having some flexibility in the choice of the data storage and to have some abstraction with respect to the crawling phase. Thus I also had to make a choice of the database: I chose Hbase mostly for it's strong connection with Nutch 2. These choice of course brough some tedious consequences, because I had to build Nutch from the source code.

Instead of using _Lucene_ directly and having full freedom to design my app I decided to opt for the more "concrete" search platform _Solr_, which is built on top of Lucene and can run on the most used and famous Java servlet containers, such as _Jetty_ or _Tomcat_. More specifically, since I decided to use _SpringBoot_ as the web framework to develop the backend of my search web app (on top of Tomcat), I used the _Spring Data Solr_, which is a tool/library that provides already a few nice features to query _Solr_ and thus provides another level of abstraction with respect to _Solr_, even though it's of course quite connected to it. These are one of the most interesting SpringBoot's features which ease a lot the query phase to a database, so why shouldn't I use it?

Regarding the configuration of Nutch and Solr, in particular the configuration of the _schema_, e.g. the configuration of the fields for each crawled and indexed document, I decided not mess much with it and to basically copy the `apache-nutch-2.3.1/conf/schema.xml` file to the only Solr's core or collection (since they must be the same or at least compatible, lets say) that I'm using, that is `solr-4.10.3/example/solr/collection1/conf/`.

I used Angular JS and Bootstrap to develop the simple front-end interface, because  I wanted to get more into Angular JS and because I had already some knowledge of Bootstrap.

## Problems

This section describes some problems that I've faced while setting up Nutch, HBase, Solr, etc. In general, I found the documentation lacking of many details and updates. I encountered many problems especially when trying to combine the tools because explanations are outdated or misleading. 

### java.lang.ClassNotFoundException: org.apache.gora.hbase.store.HBaseStore
    
 - [http://stackoverflow.com/a/25830910/3924118](http://stackoverflow.com/a/25830910/3924118)

    
## Authors

&copy; Nelson Brochado, 2016.
    
