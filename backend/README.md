know-your-costs-backend
===
For now there are 2 maven profiles:
* dev - a development profile, creates implicit h2 database
* prod - uses a postgres database on localhost:5432. NOT TESTED YET.

Maven properties are used to configure a database based on a maven profile.

API:
(/) maven commits a new user. For now.