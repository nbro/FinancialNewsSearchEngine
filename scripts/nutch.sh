#!/usr/bin/env bash

# these 2 commands should be run only when the configurations file have been replaced
ant clean
ant runtime

# trun the following 6 commands only after HBase is running
runtime/local/bin/nutch inject seeds
runtime/local/bin/nutch readdb
runtime/local/bin/nutch generate -topN 10000
runtime/local/bin/nutch fetch -all
runtime/local/bin/nutch parse -all
runtime/local/bin/nutch updatedb -all

# run this command only after Solr is running
runtime/local/bin/nutch solrindex http://localhost:8983/solr -all
