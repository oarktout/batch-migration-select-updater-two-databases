# BATCH MIGRATION SELECT UPDATER TWO DATABASE
## Principle
Migration of data based on select query executed in database Source and injected in database Destination with Table name indicated, columns are generated based on the select and data from the select too.
So the data structure in Destination database is based on the select to read from Source database.

Source corresponding where I go to search data.

Destination corresponding where I go to write data. 

## Environment variables
In this project , I used some environment variables : 

- DATABASE_SOURCE_URL : URL for the source database.
- DATABASE_SOURCE_USER : User for the source database.
- DATABASE_SOURCE_PASSWORD : Password for the source database.
- DATABASE_SOURCE_DRIVER : Driver corresponding to the type of source database.

- DATABASE_DESTINATION_URL : URL for the destination database.
- DATABASE_DESTINATION_USER : User for the destination database.
- DATABASE_DESTINATION_PASSWORD : Password for the destination database.
- DATABASE_DESTINATION_DRIVER : Driver for the destination database.

- TABLE_NAME : Table name in destination database  to fill with select data.
- SELECT_QUERY : Select query to read data from source database.

## License
Software licensed with GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 (see LICENSE.txt)

## Contact
Don't hesitate if any questions or suggestions to ask me in twitter : oth_fjk :D

Enjoy !