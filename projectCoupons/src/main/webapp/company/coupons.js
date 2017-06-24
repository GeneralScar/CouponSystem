var app = angular.module('companyApp')

	.controller("CouponController",['$compile','$window','$http','companyWebService','$scope', '$filter',function ($compile,$window,$http,companyWebService,$scope, $filter)
	{
		var vm = this;
		
		vm.selected = 0;
		vm.editName='';
		
		vm.toUpdate=''; 
		vm.toUpdateIMG=''; 
		vm.toUpdateTYPE=''; 
		
		this.create = false;   // form marker
		this.edit = false;	  // form marker

		vm.coupons =[];
	
		vm.propertyName='couponId';
		vm.reverse=true;
		
		vm.couponTypefilter='';
		vm.couponTypefilter.price='';
		vm.couponTypefilter.date='';
		vm.couponSelect='';

		vm.couponStartDate = new Date();
		vm.couponEndDate = new Date();

		/// coupon vars for form check
		this.couponTitle = '';
		this.couponType = '';
		this.couponStartDate = '';
		this.couponEndDate = '';
		this.couponMessage = '';
		this.couponPrice = 0;
		this.couponAmount = 0;
		
		vm.userMessage='';
			
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

		
		/// function to set sorting table
		this.sortBy =function (column)
		{
			vm.couponSelect='';
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

		
		/// get all coupons on loading page
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
	
		/// function to get logged user details
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

		
		// scrolling function upp and down
		this.scrollTo = function(where) {
			if ('top' == where)
				$window.scrollTo(0,0);
			else if ('bottom' == where)
				$window.scrollTo(0,document.body.scrollHeight);
		}		
		
//		// refresh table filter selections
//		this.refresh = function() {
//		vm.couponSelect='';
//		this.couponPrice = undefined;
//		this.couponEndDate = undefined;
//		this.couponfilter.price=undefined;
//	}
//	
	
	
	/// function that controls the from requests and gather data from index row to update
	this.editORCreate = function (index)    /// form usage create or update
	{ 									
				if (isNaN(index)) // if index is not a number,
								  // means its var "new" i.e. we sent a create request
					{
													
						this.create = true;   // form marker
						this.edit = false;	   // form marker
					}
				
				else
					{
						vm.selected = index;
						vm.toUpdate = vm.coupons[vm.selected].title;
						vm.toUpdateID = vm.coupons[vm.selected].couponId;
						vm.toUpdateTYPE = vm.coupons[vm.selected].couponType;
						vm.toUpdateIMG = vm.coupons[vm.selected].couponImage;
						vm.toUpdateStartDate = vm.coupons[vm.selected].couponImage;
						this.create = false;   // form marker
						this.edit = true;	   // form marker
					}
	}		
	
	
	this.applyCreate= function()
		{
			
			vm.filesSelected = document.getElementById("theFile").files;
		
			vm.baseImage='';
			
			if (vm.filesSelected.length > 0) {
		    	vm.fileToLoad = vm.filesSelected[0];

		    	vm.fileReader = new FileReader();

		    	vm.fileReader.onload = function(fileLoadedEvent) 
		    	{
		    	 // <--- data: base64

		    	
			
					var thisCoupon = {
							amount     : vm.couponAmount,
			   				couponId   : vm.couponId,
			   				couponType : vm.couponType,
			        		dateEnd    : vm.couponEndDate,
			        		dateStart  : vm.couponStartDate,
			        		image	   : fileLoadedEvent.target.result,
			        		message	   : vm.couponMessage,
			        		title	   : vm.couponTitle,
			        		price	   : vm.couponPrice,

			   		}
					console.log(thisCoupon)
					
					companyWebService.createCoupon(thisCoupon)
					.then(function(response)
					{
						console.log(response.data);
						console.log("Coupon added");
					      vm.userMessage="Coupon added " + "by the title "+ thisCoupon.title;
					      vm.postMSG();

	        		}, 
	        		
	        		function(response)
	        		{
	        			console.log(response.data);  
	        		      vm.userMessage=response.data;
					      vm.postMSG();

	        		});
		      }

			      vm.fileReader.readAsDataURL(vm.fileToLoad);	
		    }
			
		}	
		
		
		
		this.applyUpdate = function()
		{
			var thisIsCoupon = vm.coupons[vm.selected];
			vm.toUpdate = thisIsCoupon.title;
					var thisCoupon = {
							title	   : thisIsCoupon.title,
							couponId   : thisIsCoupon.couponId,
			   				amount     : vm.couponAmount,
			   				couponType : thisIsCoupon.couponType,
			        		dateEnd    : vm.couponEndDate,
			        		dateStart  : vm.couponStartDate,
			        		image	   : thisIsCoupon.couponImage,
			        		message	   : vm.couponMessage,
			        		price	   : vm.couponPrice
		   				}
					
						console.log(thisCoupon);
						
				companyWebService.updateCoupon(thisCoupon)
				.then(function(response)
						{
							console.log(response.data);
							console.log("Coupon updated");
						      vm.userMessage= "updated coupon "+thisCoupon.title;
						      vm.postMSG();

		        		}, 
		        		
		        		function(response)
		        		{
		        			console.log(response.data); 
						      vm.userMessage= " failed to updated coupon "+thisCoupon.title+" " + response.data;
						      vm.postMSG();
		        		});

				
					companyWebService.getAllCoupons()	
					.then 
					(function (response)
					{
					console.log(response.data);
				      vm.userMessage=response.data;
				      vm.coupons = response.data;
				      vm.postMSG();

					},
				
					function (response)
					{
						console.log(response.data);
					      vm.userMessage=response.data;
					      vm.postMSG();
					      
					});

				
		}
		
		
		
		// remove method
		
		this.removeCoupon = function (index)
		{
			vm.selected = index;
			var removeCoupon = vm.coupons[index];
			console.log(removeCoupon);
			
			var removeIt = 
				{
					couponId : removeCoupon.couponId,  // may not be changed
	   				title : removeCoupon.title, // may not be changed
				}
					
			console.log(removeIt);

			companyWebService.removeCoupon(removeIt)
			.then(function(response)
			{
			      vm.userMessage="successfully removed the coupon : "+removeIt.title;
			      vm.postMSG();

			}, 
			function(response)
			{
				
			      vm.userMessage="failed to removed the coupon : "+removeIt.title + " "+ response.data;
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
		
	}]);
