Feature: Autenticate teste for Audora Selection

	Background: User generate for authorization
		Given: User authorized
	
	Scenario: To Authorize user is able of the search a product and add into shopping cart
		Given A token
		When I authenticate with my credentials
		Then To enter environment 
		When I choose a product
		Then add into shopping cart