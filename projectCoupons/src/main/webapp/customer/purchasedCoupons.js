var app = angular.module('customerApp')

	.controller("PurchaseController",['$window','$http','customerWebService','$scope', '$filter',function ($window,$http,customerWebService,$scope, $filter)
	{
		var vm = this;	
		
		vm.selected = 0;
		vm.logName='';
		vm.newIndex=-1;
		vm.coupons =[];
		vm.display=false;
		
		vm.propertyName='';
		vm.reverse=true;
		
		vm.couponTypefilter='';
		vm.couponTypefilter.price='';
		vm.couponTypefilter.date='';
		vm.couponSelect='';

		this.refresh = function() {
			//alert('refresh');
			this.couponType = '';
			this.couponPrice = '';
			this.couponEndDate = '';
		}
		
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
		
		this.sortBy =function (column)
		{
			vm.couponTypefilter.price='';
			vm.couponTypefilter.date='';
			vm.couponTypefilter.title='';
			vm.couponTypefilter='';
			vm.propertyName= column;
			
			vm.reverse=!vm.reverse;
			if (vm.reverse)
				{
				vm.propertyName= '-'+column;
				}
			console.log(vm.propertyName);
			console.log(vm.reverse);
		}

		
		//imidiate function call
		
		customerWebService.getAllPurchasedCoupons()	
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
	
		//imidiate function call
		
		customerWebService.getUser()
		.then 
			(function (response)
			{
			console.log(response.data);
			
			vm.logCustomer = response.data;
			
			vm.logName = vm.logCustomer.custName;
			},
		
			function (response)
			{
				console.log(response.data);
			});

		
		this.scrollTo = function(where) {
			if ('top' == where)
				$window.scrollTo(0,0);
			else if ('bottom' == where)
				$window.scrollTo(0,document.body.scrollHeight);
		}			

	/* refresh : refreshes the coupons list (by calling getCoupons... ).
	 */
		
/*		
	this.refresh = function() {
		//alert('refresh');
		this.couponType = '';
		this.couponPrice = '';
		this.couponEndDate = '';
		vm.couponTypefilter='';
		vm.couponTypefilter.price='';
		vm.couponTypefilter.title='';
		vm.couponTypefilter.couponType='';
//		vm.couponTypefilter.date='';
		vm.couponSelect='';
		this.coupons = [];
		
	}*/
		
	}]);

	
