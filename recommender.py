from sklearn import linear_model
import csv

factors = []
prices = []
with open('challenge_data.csv') as csv_file:
	reader = csv.DictReader(csv_file, dialect='excel')
	for row in reader:
		bedrooms = int(float(row['bedrooms']))
		bathrooms = int(float(row['bathrooms']))
		squarefeet = int(float(row['square-foot']))
		price = int(float(row['price']))

		factors.append([bedrooms, bathrooms, squarefeet])
		prices.append(price)

csv_file.close()

classifier = linear_model.LinearRegression()
classifier.fit(factors, prices)

coef_file = open('coefficients.txt', 'w')
for coef in classifier.coef_:
	coef_file.write(str(classifier.coef_) + ',')
coef_file.write(str(classifier.intercept_))
coef_file.close()