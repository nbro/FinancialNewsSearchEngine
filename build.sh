#!/usr/bin/env bash

# this bash script was thought to run on OS X, but it can be modified easily to run also on any other unix-like system!

# colors used when printing
GREEN=$(tput setaf 2)
RED=$(tput setaf 1)
NORMAL=$(tput sgr0)
YELLOW=$(tput setaf 3)


programs_required_exist() {
	command -v mvn >/dev/null 2>&1 || { echo >&2 "'maven' not installed. please, install it before proceeding."; exit 1; }
	command -v wget >/dev/null 2>&1 || { echo >&2 "'wget' not installed. please, install it before proceeding."; exit 1; }
	command -v tar >/dev/null 2>&1 || { echo >&2 "'tar' not installed. please, install it before proceeding."; exit 1; }
	command -v unzip >/dev/null 2>&1 || { echo >&2 "'unzip' not installed. please, install it before proceeding."; exit 1; }
	command -v tor >/dev/null 2>&1 || { echo >&2 "'tor' not installed. please, install it before proceeding."; exit 1; }
	command -v torsocks >/dev/null 2>&1 || { echo >&2 "'torsocks' not installed. please, install it before proceeding."; exit 1; }
	command -v pkill >/dev/null 2>&1 || { echo >&2 "'pkill' not installed. please, install it before proceeding."; exit 1; }
}


configuration_exists() {
	printf "${YELLOW}checking existance of configuration directories...${NORMAL}\n"

	if [ ! -d "configuration/apache-nutch-2.3.1/conf" ]; then
		printf "${RED}'Directory 'configuration/apache-nutch-2.3.1/conf' should exist with 3 files!${NORMAL}\n";
		exit 1;
	fi

	if [ ! -d "configuration/apache-nutch-2.3.1/ivy" ]; then
		printf "${RED}'Directory 'configuration/apache-nutch-2.3.1/ivy' should exist with 1 file!${NORMAL}\n";
		exit 1;
	fi

	if [ ! -d "configuration/apache-nutch-2.3.1/seeds" ]; then
		printf "${RED}'Directory 'configuration/apache-nutch-2.3.1/seeds' should exist with 1 file!${NORMAL}\n";
		exit 1;
	fi

	if [ ! -d "configuration/hbase-0.98.8-hadoop2/conf" ]; then
		printf "${RED}Directory 'configuration/hbase-0.98.8-hadoop2/conf' should exist with 1 file!${NORMAL}\n";
		exit 1;
	fi

	printf "${GREEN}ok. ${RED}note that this only checked the existance of the directories not the specific required files.${NORMAL}\n\n";
}


stop_tor{
	printf "${YELLOW}stopping tor network...${NORMAL}\n";
	sudo pkill -f tor	
}


start_tor {
	stop_tor
	printf "${YELLOW}starting tor network...${NORMAL}\n";
	echo -ne '\n' | tor &	
}


can_download() {
	printf "${YELLOW}checking if programs to download are available...${NORMAL}\n";

	torify wget -q --spider http://www.pirbot.com/mirrors/apache/nutch/2.3.1/apache-nutch-2.3.1-src.tar.gz
	if [ $? -ne 0 ]; then
	    printf "${RED}http://www.pirbot.com/mirrors/apache/nutch/2.3.1/apache-nutch-2.3.1-src.tar.gz not available according to 'wget'.\n${NORMAL}";
	    printf "${RED}you have a few options:\n\t 1. contact the provider of this script and tell him about this problem.\n\t 2. download all files and configure the project manually (procedure which is potentially described in the README file).\n\t 3. try to find an alternative URL for the corresponding file to be downloaded and modify this script accordingly.${NORMAL}\n";
	    exit 1;
	fi

	torify wget -q --spider https://archive.apache.org/dist/hbase/hbase-0.98.8/hbase-0.98.8-hadoop2-bin.tar.gz
	if [ $? -ne 0 ]; then
	    printf "${RED}https://archive.apache.org/dist/hbase/hbase-0.98.8/hbase-0.98.8-hadoop2-bin.tar.gz.\n${NORMAL}";
	    printf "${RED}you have a few options:\n\t 1. contact the provider of this script and tell him about this problem.\n\t 2. download all files and configure the project manually (procedure which is potentially described in the README file).\n\t 3. try to find an alternative URL for the corresponding file to be downloaded and modify this script accordingly.${NORMAL}\n";
	    exit 1;
	fi

	torify wget -q --spider https://archive.apache.org/dist/lucene/solr/4.10.3/solr-4.10.3.zip
	if [ $? -ne 0 ]; then
	    printf "${RED}https://archive.apache.org/dist/lucene/solr/4.10.3/solr-4.10.3.zip\n${NORMAL}";
	    printf "${RED}you have a few options:\n\t 1. contact the provider of this script and tell him about this problem.\n\t 2. download all files and configure the project manually (procedure which is potentially described in the README file).\n\t 3. try to find an alternative URL for the corresponding file to be downloaded and modify this script accordingly.${NORMAL}\n";
	    exit 1;
	fi

	printf "${GREEN}ok.${NORMAL}\n\n";
}


download() {
	printf "${YELLOW}starting to donwload...${NORMAL}\n"
	
	rm -rf apache-nutch*
	rm -rf hbase*
	rm -rf solr*
	
	torify wget http://www.pirbot.com/mirrors/apache/nutch/2.3.1/apache-nutch-2.3.1-src.tar.gz
	torify wget https://archive.apache.org/dist/hbase/hbase-0.98.8/hbase-0.98.8-hadoop2-bin.tar.gz
	torify wget https://archive.apache.org/dist/lucene/solr/4.10.3/solr-4.10.3.zip
	
	stop_tor

	printf "${GREEN}done.${NORMAL}\n"
}


decompress() {
	printf "${YELLOW}starting to decompress files...${NORMAL}\n";
	
	tar -xvzf apache-nutch-2.3.1-src.tar.gz
	tar -xvzf hbase-0.98.8-hadoop2-bin.tar.gz
	unzip -a solr-4.10.3.zip
	
	rm -rf apache-nutch-2.3.1-src.tar.gz
	rm -rf hbase-0.98.8-hadoop2-bin.tar.gz
	rm -rf solr-4.10.3.zip

	printf "${GREEN}done.${NORMAL}\n\n";
}


configure() {
	printf "${YELLOW}starting to copy configuration files to the corresponding downloaded folders...${NORMAL}\n";
	
	cp -f configuration/apache-nutch-2.3.1/conf/gora.properties apache-nutch-2.3.1/conf/gora.properties 
	cp -f configuration/apache-nutch-2.3.1/conf/nutch-site.xml apache-nutch-2.3.1/conf/nutch-site.xml
	cp -f configuration/apache-nutch-2.3.1/conf/schema.xml apache-nutch-2.3.1/conf/schema.xml 
	cp -f configuration/apache-nutch-2.3.1/ivy/ivy.xml apache-nutch-2.3.1/ivy/ivy.xml
	cp -rf configuration/apache-nutch-2.3.1/seeds/ apache-nutch-2.3.1/seeds/
	cp -f configuration/hbase-0.98.8-hadoop2/conf/hbase-site.xml hbase-0.98.8-hadoop2/conf/hbase-site.xml
	# copying the Nutch's schema to be the solr schema
	cp -f configuration/apache-nutch-2.3.1/conf/schema.xml solr-4.10.3/example/solr/collection1/conf/schema.xml
	
	rm -rf solr-4.10.3/example/solr/collection1/data/*

	printf "${GREEN}done.${NORMAL}\n\n";
}


solr() {
	printf "${YELLOW}starting solr...${NORMAL}\n";
	cd solr-4.10.3

	if [ ! -f "bin/solr" ]; then
		printf "${RED}'File 'bin/solr' should exist!${NORMAL}\n";
		exit 1;
	fi

	bin/solr start -c -dir example

	cd ..
	printf "${GREEN}done.${NORMAL}\n\n";
}


nutch() {
	printf "${YELLOW}starting nutch...${NORMAL}\n";

	command -v ant >/dev/null 2>&1 || { echo >&2 "'ant' not installed. please, install it before proceeding."; exit 1; }

	cd apache-nutch-2.3.1

	# these 2 commands should be run only when the configurations file have been replaced
	printf "${YELLOW}building nutch...${NORMAL}\n";
	ant clean
	ant runtime
	printf "${GREEN}done.${NORMAL}\n\n";

	if [ ! -f "runtime/local/bin/nutch" ]; then
		printf "${RED}'File 'runtime/local/bin/nutch' should exist!${NORMAL}\n";
		exit 1;
	fi

	printf "${YELLOW}injecting seeds...${NORMAL}\n";
	# trun the following 6 commands only after HBase is running
	runtime/local/bin/nutch inject seeds
	printf "${GREEN}done.${NORMAL}\n\n";

	printf "${YELLOW}generating top 1000...${NORMAL}\n";  # -topN 100000
	runtime/local/bin/nutch generate -topN 1000
	printf "${GREEN}done.${NORMAL}\n\n";

	printf "${YELLOW}fetching all webdocuments...${NORMAL}\n";
	runtime/local/bin/nutch fetch -all
	printf "${GREEN}done.${NORMAL}\n\n";

	printf "${YELLOW}parsing all fetched webdocuments...${NORMAL}\n";
	runtime/local/bin/nutch parse -all
	printf "${GREEN}done.${NORMAL}\n\n";

	printf "${YELLOW}updating database all...${NORMAL}\n";
	runtime/local/bin/nutch updatedb -all
	printf "${GREEN}done.${NORMAL}\n\n";

	printf "${YELLOW}runtime/local/bin/nutch solrindex http://localhost:8983/solr/ -all${NORMAL}\n";
	runtime/local/bin/nutch solrindex http://localhost:8983/solr/ -all	
	printf "${GREEN}done.${NORMAL}\n\n";

	cd ..
	printf "${GREEN}done.${NORMAL}\n\n";
}


hbase() {
	printf "${YELLOW}starting hbase...${NORMAL}\n";
	cd hbase-0.98.8-hadoop2

	if [ ! -f "bin/start-hbase.sh" ]; then
		printf "${RED}'File 'bin/start-hbase.sh' should exist!${NORMAL}\n";
		exit 1;
	fi

	bin/start-hbase.sh

	cd ..
	printf "${GREEN}done.${NORMAL}\n\n";
}

run_webapp() {
	cd app
	mvn clean install
	mvn spring-boot:run
	cd ..
}


start() {
	programs_required_exist

	configuration_exists

	can_download

	download	

	decompress

	configure

	solr

	hbase

	nutch

	run_webapp
}

