from flask import Flask, render_template, request
import mysql.connector as mysql


app = Flask('Smoove')

smoovedb = mysql.connect(user='browncowmaster', password='hownowbrowncow', host='browncow.ctjebouppjjs.us-west-2.rds.amazonaws.com', database='smoove')

@app.route('/', methods=['GET', 'POST'])
def index():
	cursor = smoovedb.cursor()
	query = ("select * from test_table")
	cursor.execute(query)
	data = []
	for item in cursor:
		data.append(item)
	if request.method == 'POST':
		return None
	return render_template('index.html', data=data)

@app.route('/left')
def left():
	return render_template('left-sidebar.html')

@app.route('/get_transaction')
def get_txn():
	cursor = smoovedb.cursor()
	query = ("select * from transactions")
	cursor.execute(query)
	data = []
	for item in cursor:
		data.append(item)
	return render_template('index.html', data=data)

if __name__ == '__main__':
	app.run(debug=True)