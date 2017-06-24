(function()	{
	var adminApp = angular.module('adminApp');
	adminApp.service('adminWebService', adminWebService);

	function adminWebService($http)
	{	

		// companies
	
		var self = this;

		//logout
		
		
		//create
		
		self.createCompany = function(company)
		{
			console.log(company);
			return $http.post("http://localhost:8080/projectCoupons/webapi/admin/company", company);
			
		}	 
	
		//remove
		
		self.removeCompany = function(company)
		{
			//console.log("remove company");TRUE
			console.log(company);
			return ($http.post("http://localhost:8080/projectCoupons/webapi/admin/removeCompany", company));
			
		}
		
		// update
		
		self.updateCompany = function(company)
		{
			return  $http.put("http://localhost:8080/projectCoupons/webapi/admin/updateCompany", company);

		}
		
		//getAll
		
		self.getAllCompanies = function()
		{
			var promise =  $http.get("http://localhost:8080/projectCoupons/webapi/admin/getAllCompanies");
			var promise2 = promise.then(function(response){
				return response.data;
				console.log(response.data);
			});
			return promise2;
		}

		// customers
			
		//Create
		
		self.createCustomer = function(customer)
		{	console.log(customer);
			return  $http.post("http://localhost:8080/projectCoupons/webapi/admin/createCustomer", customer);
		}
		
		//remove
		
		self.removeCustomer = function(customer)
		{
			return  $http.post("http://localhost:8080/projectCoupons/webapi/admin/removeCustomer", customer);
		}		

		//update
		
		self.updateCustomer = function(customer)
		{
			console.log("in the client web serivce");
			console.log(customer);
			return $http.put("http://localhost:8080/projectCoupons/webapi/admin/updateCustomer", customer);
		}
	
		//getAll
		
		self.getAllCustomers = function()
		{
			var promise =  $http.get("http://localhost:8080/projectCoupons/webapi/admin/getAllCustomers");
			var promise2 = promise.then(function(response){
				return response.data;
				console.log(response.data);
			});
			return promise2;
		}
		
	}

})();
  

