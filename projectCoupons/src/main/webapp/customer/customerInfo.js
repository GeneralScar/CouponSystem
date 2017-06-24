var app = angular.module('customerApp')

	.controller("InfoController",['$window','$http','customerWebService','$scope', '$filter',function ($window,$http,customerWebService,$scope, $filter)
	{

		var vm = this;	

		vm.logName = '';
		vm.logPassword = '';
		vm.logID = '';
		
		this.logOut = function()
		{
			$http.post("http://localhost:8080/projectCoupons/Logout").then(function(response)
					{
						$window.location.href="http://localhost:8080/projectCoupons/index.html";
					},
					function(response)
					{
						
					});
		}
		
		customerWebService.getUser()
		.then 
			(function (response)
			{
			console.log(response.data);
			
			vm.logCustomer = response.data;
			
			vm.logName = vm.logCustomer.custName;
			vm.logPassword = vm.logCustomer.password;
			vm.logID = vm.logCustomer.custId;
			},
		
			function (response)
			{
				console.log(response.data);
			});
	}]);