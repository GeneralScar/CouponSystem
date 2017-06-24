var app = angular.module('companyApp');

	app.controller("TestController",['$window','$http','companyWebService','$scope', '$filter',function ($window,$http,companyWebService,$scope, $filter)
	{
		var vm = this;
		
		vm.selected = 0;
		vm.editName='';
		
		vm.toUpdate=''; 
		vm.toUpdateIMG=''; 
		vm.toUpdateTYPE=''; 
		
		this.create = true;   // form marker
		this.edit = false;	  // form marker

		vm.coupons =[];
	
		vm.propertyName='couponId';
		vm.reverse=true;
		
		vm.couponTypefilter='';
		vm.couponTypefilter.price='';
		vm.couponTypefilter.date='';
		vm.couponSelect='';

		/// coupon vars for form check
		this.couponTitle = '';
		this.couponType = '';
		this.couponStartDate = '';
		this.couponEndDate = '';
		this.couponMessage = '';
		this.couponPrice = 0;
		this.couponAmount = 0;
		
		
		
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

		
		companyWebService.getAllCoupons()	
		.then 
			(function (data)
			{
			console.log(data);
			
			vm.coupons = data;
			},
		
			function (response)
			{
				console.log(response.data);
			});
	
		
		companyWebService.getUserName()
		.then 
			(function (response)
			{
			console.log(response.data);
			
			vm.editName = response.data;
			},
		
			function (response)
			{
				console.log(response.data);
			});

	}]);

	
	app.directive('custview', function(){
	      return {
	    	  scope: true,  //inherits parent scope
	    	  templateUrl : 'couponTAG.html', // url of injectel html piece
	          restrict:'AE', // default definition to interact as attribute and an element
	          replace: false
	      }        
	});