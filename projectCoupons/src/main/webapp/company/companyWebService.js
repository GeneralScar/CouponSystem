(function()	{
	var companyApp = angular.module('companyApp');
	companyApp.service('companyWebService', companyWebService);

	function companyWebService($http)
	{	

		// companies
	
		var self = this;

		
		//user
		self.getUser = function()
		{
			return $http.get("http://localhost:8080/projectCoupons/webapi/company/LoggedUser");
		}
		
		
		//user
		self.getUserName = function()
		{
			return $http.get("http://localhost:8080/projectCoupons/webapi/company/user");
		}
		
		//create
		self.createCoupon = function(coupon)
		{
			console.log(coupon);
			var promise = $http.post("http://localhost:8080/projectCoupons/webapi/company/createCoupon", coupon);
			var promise2 = promise.then(function(response){
				return response.data;
			});
			return promise2;

			
		}	 
	
		//remove
		
		self.removeCoupon = function(coupon)
		{
			//console.log("remove company");TRUE
			console.log(coupon);
			
			return ($http.post("http://localhost:8080/projectCoupons/webapi/company/removeCoupon", coupon));
			
		}
		
		// update
		
		self.updateCoupon = function(coupon)
		{
			return  $http.put("http://localhost:8080/projectCoupons/webapi/company/updateCoupon", coupon);

		}
		
		//getAll
		
		self.getAllCoupons = function()
		{
			var promise =  $http.get("http://localhost:8080/projectCoupons/webapi/company/coupons");
			var promise2 = promise.then(function(response){
				return response.data;
			});
			return promise2;
		}

		// customers
			
		//Create
		
	}

})();
  

