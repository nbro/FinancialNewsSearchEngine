# FinancialNewsSearchEngine


## Introduction

This is a very simple search engine web application which is somehow "specialized" in searching for financial news on the web. It's still very rudimental and far from retrieving good results, because I didn't have much time to develop and therefore to improve it. But my goal is to continue the development (especially of the missing but important features that I will mention next) of this search engine as soon as I've a little bit of time.

So far it only tries to find "quite blindly" (i.e. without a certain level of "intelligence") matches (with respect to the user's query) in the _content_ of the webpages stored in the database (_Hbase_) without actually looking at the title or the URL or other characteristics of the webdocuments, such as anchor texts, language or format of the web document, etc. This is because I didn't have much time to learn how to use the API that allows the app to connect to the searching platform (i.e., Solr) and therefore how to perform more complex queries for more "expected" results.

It also doesn't allow you to search for exact matches or expressions, like 

> what's the meaning of "easy easy lemon squeezy?"
 
On ther other hand, you can try to write 

> easy easy lemon squeezy

and if there's a document in the database whose contents contain that sentence, it should be able to retrieve it, although it would not be able to distinguish which document is the best for the user's needs, which (in general) can vary a lot, of course, i.e., this would be not easy to do even if I had a lot more (and even infinite?) time, lets say!

More specifically, the actual algorithm right now being used for searching is very simple and dumb: given a set of words or terms ($w_1, w_2, ... , w_n$) representing a query $q_n$, it tries to find all webdocuments [previously downloaded (and parsed) by a so-called "[crawler](https://en.wikipedia.org/wiki/Web_crawler)" (in this case it was [Nutch](http://nutch.apache.org/)) and indexed (i.e., put simply, organized in a way for easy retrieval and ranking using specialized data structures such as "[inverted indices](https://en.wikipedia.org/wiki/Inverted_index)") by another program called [Solr](http://lucene.apache.org/solr/) (which under the hood uses another program called [Lucene](https://lucene.apache.org/) for indexing and searching)] which contain strings for which one or more of $w_1, w_2, ... , w_n$ are either a substring, partial or exact match (e.g., if "eco" was a term $w_i$ in the query $q_n$, then documents with words "economic" or "economy" would also be retrieved), ignoring any case sensitivity, i.e., "hello" would be the same as "hELLo".

I've not customized the indexing process of Solr (because of lack of time, of course). Moreover I didn't go much into the details of how actually Solr works, which approaches or data structures it uses, etc.

So, regarding the searching process there's much to improve in this application! Check the `TODOs` section below for more details of features I would like to add to this SE.

Regarding the presentation of the results, a nice feature that's already implemented is the highlighting of the matches in the contents of the webdocuments in the result snippets (not in the title or in the URL). Other useful features that could improve the user experience are "query suggestion" and "query correction", which I also want to implement!
Right now the UI is very simple, but this was also my goal, even though, for example, I could have added other optional features which are very related to financial news, like displaying stock markets, etc.


### Why Nutch, HBase and Solr?

I've decided to use Nutch version 2 because I was interested in having some flexibility in the choice of the data storage and to have some abstraction with respect to the crawling phase. Thus I also had to make a choice of the database: I chose Hbase mostly for it's strong connection with Nutch 2. These choice of course brough some tedious consequences, because I had to build Nutch from the source code.

Instead of using _Lucene_  directly and having full freedom to design my app I decided to opt for the more "concrete" search platform _Solr_, which is built on top of Lucene and can run on the most used and famous Java servlet containers, such as _Jetty_ or _Tomcat_. More specifically, since I decided to use _SpringBoot_ as the web framework to develop the backend of my search web app (on top of Tomcat), I used the _Spring Data Solr_, which is a tool/library that provides already a few nice features to query _Solr_ and thus provides another level of abstraction with respect to _Solr_, even though it's of course quite connected to it. These are one of the most interesting SpringBoot's features which ease a lot the query phase to a database, so why shouldn't I use it?

Regarding the configuration of Nutch and Solr, in particular the configuration of the _schema_, e.g. the configuration of the fields for each crawled and indexed document, I decided not mess much with it and to basically copy the `apache-nutch-2.3.1/conf/schema.xml` file to the only Solr's core or collection (since they must be the same or at least compatible, lets say) that I'm using, that is `solr-4.10.3/example/solr/collection1/conf/`.

I used Angular JS and Bootstrap to develop the simple front-end interface, because  I wanted to get more into Angular JS and because I had already some knowledge of Bootstrap.

## Setup

All of the following commands and explanations assume that you're on a unix-like system and that you have `Java` and `maven` installed. Currently I'm using `Java 1.8.0_40` and `Apache Maven 3.3.9`. If you're on Windows and you're stuck, feel free to <a href="mailto:nelson.brochado@outlook.com">contact me</a>.

Software you will _absolutely_ need to download is:

1. [`apache-nutch-2.3.1`](http://www.apache.org/dyn/closer.lua/nutch/2.3.1/apache-nutch-2.3.1-src.tar.gz) (source distribution, since at the time of this writing there's still no binary one)

2. [`hbase-0.98.8-hadoop2-bin.tar.gz`](https://archive.apache.org/dist/hbase/hbase-0.98.8/)

3. [`solr-4.10.3.zip`](http://archive.apache.org/dist/lucene/solr/4.10.3/)

4. [`ant`](http://ant.apache.org/)

Other software you may _eventually_ need, if you use the script [`./build.sh`](./build.sh):

1. [`wget`](https://www.gnu.org/software/wget/)

2. [`tar`](https://www.gnu.org/software/tar/)

3. [`unzip`](http://www.info-zip.org/)

4. [`tor`](https://www.torproject.org/)

5. [`torsocks`](https://github.com/dgoulet/torsocks)

6. [`pkill`](https://www.freebsd.org/cgi/man.cgi?query=pkill&sektion=1)

7. [`kill`](https://www.freebsd.org/cgi/man.cgi?query=kill&sektion=1)

8. [`lsof`](https://linux.die.net/man/8/lsof)

9. [`awk`](https://en.wikipedia.org/wiki/AWK)

10. [`grep`](https://www.freebsd.org/cgi/man.cgi?query=grep&sektion=&n=1)

11. [`xargs`](https://www.freebsd.org/cgi/man.cgi?query=xargs&apropos=0&sektion=0&manpath=FreeBSD+10.3-RELEASE+and+Ports&arch=default&format=html)

Notice that you may have already some/most of these programs installed on your system. 

If you're on a Mac OS X, you can install most of these programs using for example the package manager _Homebrew_ or _port_. On Linux the package manager may be different but the process should be the same.

_I've created a script [`build.sh`](./build.sh) which basically automates the tasks described in the sections `configuration` and `commands`. This script is still not robust, but in theory could avoid you to read the sections I've just mentioned and simply acess the web app at [http://127.0.0.1:3000](http://127.0.0.1:3000).

Before that though you still need to add the following lines to your `/etc/hosts` file (as explained in the first tutorial mentioned below):

    ##
    # Host Database
    #
    # localhost is used to configure the loopback interface
    # when the system is booting.  Do not change this entry.
    ##
    127.0.0.1       localhost.localdomain localhost LMC-032857
    ::1             ip6-localhost ip6-loopback
    fe80::1%lo0     ip6-localhost ip6-loopback

And you should also export the home folder of your JavaVM. Check the mentioned tutorial for more info.

After that, you can run the script as follows:

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

Please, do the following, first of all:

Update your `/etc/hosts` file with:

    ##
    # Host Database
    #
    # localhost is used to configure the loopback interface
    # when the system is booting.  Do not change this entry.
    ##
    127.0.0.1       localhost.localdomain localhost LMC-032857
    ::1             ip6-localhost ip6-loopback
    fe80::1%lo0     ip6-localhost ip6-loopback
    
and export the home folder of your JavaVM. Check the first tutorial above, if you have any problems in doing one of these.


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


## Errors and Exceptions

This section describes some errors and exceptions that I've faced while setting up Nutch, HBase, Solr, etc. In general, I found the documentation lacking of many details and updates. I encountered many problems especially when trying to combine the tools because explanations are outdated or misleading. 

### java.lang.ClassNotFoundException: org.apache.gora.hbase.store.HBaseStore
    
 - [http://stackoverflow.com/a/25830910/3924118](http://stackoverflow.com/a/25830910/3924118)


### indexer.IndexingJob - SolrIndexerJob: java.lang.RuntimeException: job failed: name=apache-nutch-2.3.1.jar, jobid=job_local441025530_0001

This `java.lang.RuntimeException` exception can be raised for various reasons, so the only way to really understand the problem is to go into the compiled version of Nutch (specifically in [`apache-nutch-2.3.1/runtime/local/logs/`](apache-nutch-2.3.1/runtime/local/logs/), open the current log file and check for the corresponding error.

> java.lang.Exception: org.apache.solr.client.solrj.impl.HttpSolrServer$RemoteSolrException: ERROR: [doc=com.barchart.www:https/options] multiple values encountered for non multiValued field meta_keywords: 
[equity option quotes,options quotes,option prices,options chain,option chains,options trading,
, options trading strategy,options trading strategies,calls,puts,call and put options,options screener,equity options,option bid ask,open interest,implied volatility,option greeks,option expiration date,stock options quotes,stock options, stock options trading,call option quotes,put option quotes,Stock Options news, Stock Options articles]
	at org.apache.hadoop.mapred.LocalJobRunner$Job.runTasks(LocalJobRunner.java:462)
	at org.apache.hadoop.mapred.LocalJobRunner$Job.run(LocalJobRunner.java:522)
Caused by: org.apache.solr.client.solrj.impl.HttpSolrServer$RemoteSolrException: **ERROR: [doc=com.barchart.www:https/options] multiple values encountered for non multiValued field meta_keywords**: [equity option quotes, options quotes, option prices, options chain, option chains, options trading, options trading strategy, options trading strategies, calls, puts, call and put options, options screener, equity options, option bid ask, open interest, implied volatility, option greeks, option expiration date, stock options quotes, stock options, stock options trading, call option quotes, put option quotes, Stock Options news, Stock Options articles]
	at org.apache.solr.client.solrj.impl.HttpSolrServer.request(HttpSolrServer.java:491)
	at org.apache.solr.client.solrj.impl.HttpSolrServer.request(HttpSolrServer.java:197)
	at org.apache.solr.client.solrj.request.AbstractUpdateRequest.process(AbstractUpdateRequest.java:117)
	at org.apache.solr.client.solrj.SolrServer.add(SolrServer.java:68)
	at org.apache.solr.client.solrj.SolrServer.add(SolrServer.java:54)
	at org.apache.nutch.indexwriter.solr.SolrIndexWriter.write(SolrIndexWriter.java:84)
	at org.apache.nutch.indexer.IndexWriters.write(IndexWriters.java:84)
	at org.apache.nutch.indexer.IndexerOutputFormat$1.write(IndexerOutputFormat.java:48)
	at org.apache.nutch.indexer.IndexerOutputFormat$1.write(IndexerOutputFormat.java:43)
	at org.apache.hadoop.mapred.MapTask$NewDirectOutputCollector.write(MapTask.java:635)
	at org.apache.hadoop.mapreduce.task.TaskInputOutputContextImpl.write(TaskInputOutputContextImpl.java:89)
	at org.apache.hadoop.mapreduce.lib.map.WrappedMapper$Context.write(WrappedMapper.java:112)
	at org.apache.nutch.indexer.IndexingJob$IndexerMapper.map(IndexingJob.java:120)
	at org.apache.nutch.indexer.IndexingJob$IndexerMapper.map(IndexingJob.java:69)
	at org.apache.hadoop.mapreduce.Mapper.run(Mapper.java:145)
	at org.apache.hadoop.mapred.MapTask.runNewMapper(MapTask.java:764)
	at org.apache.hadoop.mapred.MapTask.run(MapTask.java:340)
	at org.apache.hadoop.mapred.LocalJobRunner$Job$MapTaskRunnable.run(LocalJobRunner.java:243)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
2016-11-29 13:34:02,501 ERROR indexer.IndexingJob - SolrIndexerJob: java.lang.RuntimeException: job failed: name=apache-nutch-2.3.1.jar, jobid=job_local441025530_0001
	at org.apache.nutch.util.NutchJob.waitForCompletion(NutchJob.java:120)
	at org.apache.nutch.indexer.IndexingJob.run(IndexingJob.java:154)
	at org.apache.nutch.indexer.IndexingJob.index(IndexingJob.java:176)
	at org.apache.nutch.indexer.IndexingJob.run(IndexingJob.java:202)
	at org.apache.hadoop.util.ToolRunner.run(ToolRunner.java:70)
	at org.apache.nutch.indexer.IndexingJob.main(IndexingJob.java:211)

### Infinite wait when injecting seeds (either with script or manually)

This problem is probably due to a connection problem to the database _hbase_ or because the database was not started correctly... My advice is to restart whatever you were doing (i.e., call again `start`). The process should delete the database and create another one...


## TODOs

- Query suggestion

- Query correction

- Possibility to query for exact expression 

- Customize the searching process to be more "financial news-oriented".

- Possibility to sort documents by title or url (for example)

- ...

    
## Authors

&copy; Nelson Brochado, 2016.
    
