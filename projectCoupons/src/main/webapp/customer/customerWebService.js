(function()	{
	var customerApp = angular.module('customerApp');
	customerApp.service('customerWebService', customerWebService);

	function customerWebService($http)
	{	

		// companies
	
		var self = this;

		
		//user
		self.getUser = function()
		{
			return $http.get("http://localhost:8080/projectCoupons/webapi/customer/current");
		}
		
			
		//buy
		
		self.purchaseCoupon = function(coupon)
		{
			//console.log("buy coupon");TRUE
			console.log(coupon);
			
			return ($http.post("http://localhost:8080/projectCoupons/webapi/customer/buy", coupon));
			
		}
		
		// update
				
		//getAll
		
		self.getAllPurchasedCoupons = function()
		{
			var promise =  $http.get("http://localhost:8080/projectCoupons/webapi/customer/purchasedCoupons");
			var promise2 = promise
			.then(function(response){
			return response.data;
			});
			return promise2;
		}


		self.getAllAvilableCoupons = function()
		{
			console.log("on get Availables");
			var promise =  $http.get("http://localhost:8080/projectCoupons/webapi/customer/avilableCoupons");
			var promise2 = promise
			.then(function(response){
			return response.data;
			});
			return promise2;
		}


		
	}

})();
