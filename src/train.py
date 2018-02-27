#!/usr/bin/env python
#
# University of South Alabama CIS-497
# IBM Watson AI Sepsis Detection
# Training Model
#
# Authors
#	Jack Riales
#	Alec McRae
#	Josh Richardson
#	Shawyn Kane
#
# Client
#	Steve Travers, Travers Consulting
#
# Github
#	https://github.com/JackRiales/USA-CIS497-Spring2018-SQuirreL.git
#
# Description
#	This file works with the IBM Watson API using the Machine Learning Service
#	to load in a CSV file containing patient data. The patient data is serialized
#	into a model with named fields. It trains the AI to correlate the data in the columns
#	with the "answer" column, a boolean telling us if the patient had sepsis or not.
#	The AI is then able to be scored (see score.*) and we're able to get a prediction
#	of the patient's diagnosis.
