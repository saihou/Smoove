from flask import Flask, render_template, request, jsonify
import mysql.connector as mysql
import json


app = Flask('Smoove')

smoovedb = mysql.connect(user='browncowmaster', password='hownowbrowncow', host='browncow.ctjebouppjjs.us-west-2.rds.amazonaws.com', database='smoove')

###### Web APP #####

@app.route('/', methods=['GET', 'POST'])
def index():
	cursor = smoovedb.cursor()
	query = ("SELECT *  FROM reservations rs inner join users_new u on u.idusers = rs.rsvn_user_id")
	cursor.execute(query)
	data = []
	for item in cursor:
		data.append(item)
	if request.method == 'POST':
		return None
	return render_template('index.html', data=data)

@app.route('/get_reservation_ajax')
def get_reservation_ajax():
	cursor = smoovedb.cursor()
	query = ("SELECT rs.rsvn_date, rs.rsvn_user_id, rs.rsvn_merchant_id, rs.rsvn_pax, day(rs.rsvn_time_in) rsvn_day, hour(rs.rsvn_time_in) rsvn_hour, u.users_first_name FROM reservations rs inner join users_new u on u.idusers = rs.rsvn_user_id")
	cursor.execute(query)
	data = []
	for item in cursor:
		data.append(item)
	return jsonify(result=data)

###### Mobile App REST API #####

# Get Transaction Records
@app.route('/get_transaction/<user_id>')
def get_txn(user_id):
	cursor = smoovedb.cursor()
	query = ("SELECT txn_date, txn_amount, txn_tip, uc.users_first_name client_first_name, uc.users_last_name client_last_name, um.users_first_name merchant_name \
				from transactions txn\
				inner join users_new uc on uc.idusers = txn.txn_user_id\
				inner join users_new um on um.idusers = txn.txn_merchant_id where txn.txn_user_id=" + user_id + " order by txn_date desc")
	cursor.execute(query)
	header = ["txn_date", "txn_amount", "txn_tip", "client_first_name", "client_last_name", "merchant_name"]
	data = []
	for item in cursor:
		single_txn = {}
		for i in range(len(item)):
			single_txn[str(header[i])] = str(item[i])
		data.append(single_txn)
	data = json.dumps(data)
	return data


# Get Reservations Records
@app.route('/get_reservation/<user_id>')
def get_rsvn(user_id):
	cursor = smoovedb.cursor()
	query = ("SELECT rs.rsvn_date date_recorded, rs.rsvn_pax pax, rs.rsvn_time_in rsvn_time, uc.users_first_name client_first_name, uc.users_last_name client_last_name, um.users_first_name merchant_name \
				FROM reservations rs \
				inner join users_new uc on uc.idusers = rs.rsvn_user_id \
				inner join users_new um on um.idusers = rs.rsvn_merchant_id where rs.rsvn_user_id=" + user_id + " order by rs.rsvn_date")
	cursor.execute(query)
	header = ["date_recorded", "pax", "rsvn_time", "client_first_name", "client_last_name", "merchant_name"]
	data = []
	for item in cursor:
		single_rsvn = {}
		for i in range(len(item)):
			single_rsvn[str(header[i])] = str(item[i])
		data.append(single_rsvn)
	data = json.dumps(data)
	return data

# Get List of Available Restaurants
@app.route('/get_restaurants')
def get_restaurant():
	cursor = smoovedb.cursor()
	query = ("SELECT idusers, users_first_name, merchant_desc FROM users_new where type = 'merchant'")
	cursor.execute(query)
	header = ['merchant_id', 'company_name', 'desc']
	data = []
	for item in cursor:
		single_res = {}
		for i in range(len(item)):
			single_res[str(header[i])] = str(item[i])
		data.append(single_res)
	data = json.dumps(data)
	return data

# POST a reservation
@app.route('/reserve/<rsvn_data>', methods=['POST'])
def reserve(rsvn_data):
	data = json.loads(rsvn_data)
	cursor = smoovedb.cursor()
	query = ("insert into reservations (rsvn_date, rsvn_user_id, rsvn_merchant_id, \
			rsvn_pax, rsvn_time_in values (%(date)s, %(user)s, %(merchant)s, %(pax)s), %(time)s", data)
	cursor.execute(query)
	return "reservation made"



if __name__ == '__main__':
	app.run(host="0.0.0.0", debug=True)