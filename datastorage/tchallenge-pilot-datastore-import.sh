#!/bin/bash
mongoimport --db tchallenge-pilot --collection accounts --file ./data/demo/tchallenge-pilot-accounts.json
mongoimport --db tchallenge-pilot --collection events --file ./data/demo/tchallenge-pilot-events.json
mongoimport --db tchallenge-pilot --collection problems --file ./data/demo/tchallenge-pilot-problems.json
mongoimport --db tchallenge-pilot --collection specializations --file ./data/demo/tchallenge-pilot-specializations.json
mongoimport --db tchallenge-pilot --collection workbooks --file ./data/demo/tchallenge-pilot-workbooks.json
