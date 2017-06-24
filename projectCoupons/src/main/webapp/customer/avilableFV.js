var app = angular.module('customerApp');

app.controller("AvilableFVController",['$window','$http','customerWebService','$scope', '$filter',function ($window,$http,customerWebService,$scope, $filter)
	{
		var vm = this;	
		
		vm.selected = 0;
		vm.logName='';
		
		vm.coupons =[];
	
		
		
		vm.propertyName='couponId';
		vm.reverse=true;
		
		vm.couponTypefilter='';
		vm.couponTypefilter.price='';
		vm.couponTypefilter.date='';
		vm.couponSelect='';
		vm.toPrint=0;
		

		
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
		
		
		//get all available coupons
		
		customerWebService.getAllAvilableCoupons()	
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
		
		
		//get user login information
		
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

		
		/// scrolling function

		this.scrollTo = function(where) {
			if ('top' == where)
				$window.scrollTo(0,0);
			else if ('bottom' == where)
				$window.scrollTo(0,document.body.scrollHeight);
		}		
		
		
		/// function to purchase coupons
	
	this.purchaseCoupon = function(index)
	{
			vm.selected = index;
			console.log(vm.selected);
			var buyThisCoupon = vm.coupons[vm.selected];
			console.log(buyThisCoupon);
			vm.title	 =buyThisCoupon.title;
			vm.couponId  =buyThisCoupon.couponId;
			vm.amount  =buyThisCoupon.amount;
			
			console.log(buyThisCoupon);
			
			var buyThis ={
					title	:	vm.title,		/// coupon primary key
					couponId:	vm.couponId,	/// coupon name - unique
					amount	:	vm.amount,		/// need to be sent to check if the amount is positive
				}
					
			console.log(buyThis);

			customerWebService.purchaseCoupon(buyThis)
			.then(function(response)
			{
				console.log(response.data);
			      vm.userMessage="successfuly purchased the coupon : " + buyThis.title;
			      vm.postMSG();
			},
			
			function(response)
			{
						console.log(response.data);
					      vm.userMessage="failed to purchase the coupon : " + buyThis.title;
					      vm.postMSG();
			});
	}
	
	
	vm.postMSG=function()
	{
		// Get the modal
		var modal = document.getElementById('myModal');

		// Get the button that opens the modal
		var btn = document.getElementById("myBtn");

		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];

		// When the user clicks the button, open the modal 
		    modal.style.display = "block";

		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
		    modal.style.display = "none";
		    location.reload();   
		}

		// When the user clicks anywhere outside of the modal, close it
		$window.onclick = function(event) {
		    if (event.target == modal) {
		        modal.style.display = "none";
		    }
		    location.reload();   

		}
	}
	
	}])
	
	app.directive('custview2', function(){
	      return {
	    	  scope: true,  //inherits parent scope
	    	  templateUrl : 'couponTAG2.html', // url of injectel html piece
	          restrict:'AE', // default definition to interact as attribute and an element
	          replace: false
	      }        
	});