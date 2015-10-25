import braintree

braintree.Configuration.configure(braintree.Environment.Sandbox,
                                  merchant_id="mpq4wrxc24y3vbvh",
                                  public_key="bk2ry3gtc2v2spbm",
                                  private_key="bef5caa64ef7ba35eb5c1fa215aa8071")

# result = braintree.Customer.create({
#     "first_name": "user",
#     "last_name": "smoove",
#     "credit_card": {
#     	'cardholder_name': "user smoove",
#     	'cvv': '123',
#     	'expiration_date': "08/19",
#     	'number': '4111111111111111',
#     	'token': 'user_smoove_token'
#     }
# })

# result = braintree.MerchantAccount.create({
#     'individual': {
#         'first_name': "Jack",
#         'last_name': "Ong",
#         'email': "jack@gmail.com",
#         'phone': "5553334444",
#         'date_of_birth': "1981-11-19",
#         'address': {
#             'street_address': "111 Main St",
#             'locality': "Chicago",
#             'region': "IL",
#             'postal_code': "60622"
#         }
#     },
#     'business': {
#         'legal_name': "Smoove",
#         'dba_name': "Smoove",
#         'tax_id': "98-7654321",
#         'address': {
#             'street_address': "111 Main St",
#             'locality': "Chicago",
#             'region': "IL",
#             'postal_code': "60622"
#         }
#     },
#     'funding': {
#         'descriptor': "Blue Ladders",
#         'destination': braintree.MerchantAccount.FundingDestination.Bank,
#         'email': "funding@blueladders.com",
#         'mobile_phone': "5555555555",
#         'account_number': "1123581321",
#         'routing_number': "071101307",
#     },
#     "tos_accepted": True,
#     "master_merchant_account_id": "money2020hackathon",
#     "id": "smoove_merchant"
# })


# merchant_account = braintree.MerchantAccount.find("smoove_merchant")

# print merchant_account

result = braintree.Transaction.sale({
  "amount": "10.00",
  "merchant_account_id": "smoove_merchant",
  "payment_method_nonce": "fake-valid-visa-nonce",
  "service_fee_amount": '0.10',
  "options": {
     "submit_for_settlement": 'True'
  }
  
  # "customer": {
  #   "first_name": "Drew",
  #   "last_name": "Smith",
  #   "company": "Braintree",
  #   "phone": "312-555-1234",
  #   "fax": "312-555-1235",
  #   "website": "http://www.example.com",
  #   "email": "drew@example.com"
  # },
  # "billing": {
  #   "first_name": "Paul",
  #   "last_name": "Smith",
  #   "company": "Braintree",
  #   "street_address": "1 E Main St",
  #   "extended_address": "Suite 403",
  #   "locality": "Chicago",
  #   "region": "IL",
  #   "postal_code": "60622",
  #   "country_code_alpha2": "US"
  # },
  # "shipping": {
  #   "first_name": "Jen",
  #   "last_name": "Smith",
  #   "company": "Braintree",
  #   "street_address": "1 E 1st St",
  #   "extended_address": "Suite 403",
  #   "locality": "Bartlett",
  #   "region": "IL",
  #   "postal_code": "60103",
  #   "country_code_alpha2": "US"
  # },
  # "options": {
  #   "submit_for_settlement": 'True'
  # },
  # "channel": "MyShoppingCartProvider"
})
