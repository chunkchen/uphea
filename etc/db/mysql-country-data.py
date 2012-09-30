import csv

# http://en.wikipedia.org/wiki/ISO_3166-1
out = open('mysql-country-data.sql', 'w')
reader = csv.reader(open("CountryCodes.csv", "rt"))
for row in reader:
	print(row)
	name = row[0].replace('\'', '\\\'')
	out.write('insert into up_country(ID, NAME, ALPHA2, ALPHA3,  ACTIVE) values (' + row[3] + ', \'' + name + '\', \'' + row[1] + '\', \'' + row[2] + '\', 1);\n');
out.close()