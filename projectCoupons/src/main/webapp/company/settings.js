var app = angular.module('companyApp')

	.controller("InfoController",['$window','$http','companyWebService','$scope', '$filter',function ($window,$http,companyWebService,$scope, $filter)
	{

		var vm = this;

		vm.logName = '';
		vm.logPassword = '';
		vm.logID = '';
		vm.logEmail = '';

		
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

		
		companyWebService.getUser()
		.then 
			(function (response)
			{
			console.log(response.data);
			
			var logCompany = response.data;
			vm.logName = logCompany.compName;
			vm.logPassword = logCompany.password;
			vm.logID = logCompany.compId;
			vm.logEmail = logCompany.email;
			},
		
			function (response)
			{
				console.log(response.data);
			});

	}]);