#!/usr/bin/env bash

# this bash script was thought to run on OS X, but it can be modified easily to run also on any other unix-like system!

# colors used when printing
GREEN=$(tput setaf 2)
RED=$(tput setaf 1)
NORMAL=$(tput sgr0)
YELLOW=$(tput setaf 3)


programs_required_exist() {
	printf "${YELLOW}checking existance of ${RED}required${YELLOW} programs: mvn, ant, wget, tar, unzip, tor, torsocks, pkill, kill, lsof, awk, grep and xargs${NORMAL}\n"
	command -v mvn >/dev/null 2>&1 || { echo >&2 "'maven' not installed. please, install it before proceeding."; exit 1; }
	command -v ant >/dev/null 2>&1 || { echo >&2 "'ant' not installed. please, install it before proceeding."; exit 1; }
	command -v wget >/dev/null 2>&1 || { echo >&2 "'wget' not installed. please, install it before proceeding."; exit 1; }
	command -v tar >/dev/null 2>&1 || { echo >&2 "'tar' not installed. please, install it before proceeding."; exit 1; }
	command -v unzip >/dev/null 2>&1 || { echo >&2 "'unzip' not installed. please, install it before proceeding."; exit 1; }
	command -v tor >/dev/null 2>&1 || { echo >&2 "'tor' not installed. please, install it before proceeding."; exit 1; }
	command -v torsocks >/dev/null 2>&1 || { echo >&2 "'torsocks' not installed. please, install it before proceeding."; exit 1; }
	command -v pkill >/dev/null 2>&1 || { echo >&2 "'pkill' not installed. please, install it before proceeding."; exit 1; }
	command -v kill >/dev/null 2>&1 || { echo >&2 "'kill' not installed. please, install it before proceeding."; exit 1; }
	command -v lsof >/dev/null 2>&1 || { echo >&2 "'lsof' not installed. please, install it before proceeding."; exit 1; }
	command -v awk >/dev/null 2>&1 || { echo >&2 "'awk' not installed. please, install it before proceeding."; exit 1; }
	command -v grep >/dev/null 2>&1 || { echo >&2 "'grep' not installed. please, install it before proceeding."; exit 1; }
	command -v xargs >/dev/null 2>&1 || { echo >&2 "'xargs' not installed. please, install it before proceeding."; exit 1; }
	printf "${GREEN}ok.${NORMAL}\n\n";
}


configuration_exists() {
	printf "${YELLOW}checking existance of configuration directories...${NORMAL}\n"

	if [ ! -f "configuration/apache-nutch-2.3.1/conf/gora.properties" ]; then
		printf "${RED}'File 'configuration/apache-nutch-2.3.1/conf/gora.properties' should exist!${NORMAL}\n";
		exit 1;
	fi

	if [ ! -f "configuration/apache-nutch-2.3.1/conf/nutch-site.xml" ]; then
		printf "${RED}'File 'configuration/apache-nutch-2.3.1/conf/nutch-site.xml' should exist!${NORMAL}\n";
		exit 1;
	fi

	if [ ! -f "configuration/apache-nutch-2.3.1/conf/schema.xml" ]; then
		printf "${RED}'File 'configuration/apache-nutch-2.3.1/conf/schema.xml' should exist!${NORMAL}\n";
		exit 1;
	fi

	if [ ! -f "configuration/apache-nutch-2.3.1/ivy/ivy.xml" ]; then
		printf "${RED}'File 'configuration/apache-nutch-2.3.1/ivy/ivy.xml' should exist!${NORMAL}\n";
		exit 1;
	fi

	if [ ! -d "configuration/apache-nutch-2.3.1/seeds" ]; then
		printf "${RED}'Directory 'configuration/apache-nutch-2.3.1/seeds' should exist!${NORMAL}\n";
		exit 1;
	fi

	if [ ! -f "configuration/hbase-0.98.8-hadoop2/conf/hbase-site.xml" ]; then
		printf "${RED}File 'configuration/hbase-0.98.8-hadoop2/conf/hbase-site.xml' should exist!${NORMAL}\n";
		exit 1;
	fi

	printf "${GREEN}ok.${NORMAL}\n\n";
}


stop_tor(){
	printf "${YELLOW}stopping tor network...${NORMAL}\n";
	sudo pkill -f tor
}


start_tor() {
	stop_tor
	printf "${YELLOW}we're going to start the tor network to download the files.\nsince we're going to use tor, the download is likely to be slower than without it.\n${NORMAL}";
	# http://tor.stackexchange.com/q/13244/13574
	tor --runasdaemon 1
}


can_download() {
	start_tor

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


clean_downloads() {
	rm -rf apache-nutch*
	rm -rf hbase*
	rm -rf solr*	
}


download() {
	printf "${YELLOW}starting to donwload...${NORMAL}\n"
	
	clean_downloads

	torify wget http://www.pirbot.com/mirrors/apache/nutch/2.3.1/apache-nutch-2.3.1-src.tar.gz
	torify wget https://archive.apache.org/dist/hbase/hbase-0.98.8/hbase-0.98.8-hadoop2-bin.tar.gz
	torify wget https://archive.apache.org/dist/lucene/solr/4.10.3/solr-4.10.3.zip
	
	stop_tor

	printf "${GREEN}done.${NORMAL}\n\n"
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
	if [ ! -f "solr-4.10.3/bin/solr" ]; then
		printf "${RED}'File 'solr-4.10.3/bin/solr' should exist!${NORMAL}\n";
	else
		printf "${YELLOW}starting solr at port 8983...${NORMAL}\n";

		cd solr-4.10.3
		lsof -P | grep ':8983' | awk '{print $2}' | xargs kill -9 
		bin/solr start -c -dir example

		cd ..
		printf "${GREEN}done.${NORMAL}\n\n";
	fi
}


nutch() {
	if [ ! -d "apache-nutch-2.3.1" ]; then
		printf "${RED}'Directory 'apache-nutch-2.3.1' should exist!${NORMAL}\n";
	else
		printf "${YELLOW}starting nutch...${NORMAL}\n";
	
		cd apache-nutch-2.3.1

		# these 2 commands should be run only when the configurations file have been replaced
		printf "${YELLOW}building nutch...${NORMAL}\n";
		ant clean
		ant runtime
		printf "${GREEN}done.${NORMAL}\n\n";

		# fatal error: some files/configurations somewhere else where probably modified!
		if [ ! -f "runtime/local/bin/nutch" ]; then
			printf "${RED}'File 'runtime/local/bin/nutch' should exist!${NORMAL}\n";
		else
			printf "${YELLOW}injecting seeds...${NORMAL}\n";
			# trun the following 6 commands only after HBase is running
			runtime/local/bin/nutch inject seeds
			printf "${GREEN}done.${NORMAL}\n\n";

			# change number after -topN depending on your needs
			printf "${YELLOW}generating top 1000...${NORMAL}\n"; 
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

			printf "${GREEN}done.${NORMAL}\n\n";			
		fi

		cd ..

	fi
}


hbase() {
	if [ ! -d "hbase-0.98.8-hadoop2" ]; then
		printf "${RED}'Directory 'hbase-0.98.8-hadoop2' should exist!${NORMAL}\n";
	else
		cd hbase-0.98.8-hadoop2

		printf "${YELLOW}starting hbase...${NORMAL}\n";

		if [ ! -f "bin/start-hbase.sh" ]; then
			printf "${RED}'File 'bin/start-hbase.sh' should exist!${NORMAL}\n";			
		else
			bin/stop-hbase.sh
			bin/start-hbase.sh
			printf "${GREEN}done.${NORMAL}\n\n";
		fi

		cd ..
	fi
}

run_webapp() {
	cd app
	mvn clean install
	mvn spring-boot:run
	cd ..
}


check_platform() {
	if [ "$(uname)" != "Darwin" ]; then
		printf "${RED}Your not on a Mac OS X, but this script was designed to run on Darwin!\nIt may also work if you're on a unix-like system, but I encourage you to contact the provider of this script (nelson.brochado@outlook.com) if you don't know what you're doing.\nIn any case, if you know what you're doing, you could also directly adapt this script to your platform.${NORMAL}";
		kill -INT $$
	fi	
}


start() {

	check_platform

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

