var adminApp = angular.module("adminApp")
	.controller("CustomerController",['$window','$http','adminWebService','$scope', '$filter',function ($window,$http,adminWebService,$scope, $filter)
		{
		
		var vm = this;
		
		//initializing company fields

		this.create = false;   // form marker
		this.edit = true;	   // form marker
		
		
		vm.CoupLastIndex=-1; // intialization index starts with 0 so cant happen
		vm.selected = -1;
		vm.editName='';
		vm.customers=[];
		
		//get all companies for table
		//should be called each operation
		
		vm.create=false;
		vm.edit=false;
		
		vm.passw1='';	
		vm.passw2='';
		vm.custName='';
		
		vm.customerTypeFilter='';
		vm.customerTypeFilter.custName='';
		vm.customerTypeFilter.custId='';
		vm.propertyName='';

		vm.hisCoupons='';
		vm.showCoupons=false;
		vm.clickCoupons=[];
		
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
		
		
		
		this.sortBy =function (column)
		{
			vm.customerTypeFilter.custNname='';
			vm.customerTypeFilter.custId='';
			vm.customerTypefilter='';
			
			vm.clickCoupons=false;
			
			vm.propertyName= column;
			vm.reverse=!vm.reverse;
			
		if (vm.reverse)
			{
				vm.propertyName= '-'+column;
			}
			
		console.log(vm.propertyName);
			console.log(vm.reverse);
		}

		this.refresh = function() {
			//alert('refresh');
			vm.customerTypeFilter.name='';
			vm.customerTypeFilter.custId='';
			vm.customerTypefilter='';
			
		}

		

		adminWebService.getAllCustomers()	
		.then 
			(function (data)
			{
			console.log(data);
			
			vm.customers = data;
			},
		
			function (response)
			{
				console.log(response.data);
			});
	
		
		this.getCoupons= function(index)
		{
			vm.hisCoupons=vm.customers[index].custName;
			vm.selected = index;
			console.log(vm.selected);
			console.log(vm.showCoupons);
			if (vm.showCoupons==false)
				{
					vm.CoupLastIndex=vm.selected;
					vm.showCoupons=true;
					console.log(vm.showCoupons)
					vm.clickCoupons=vm.customers[index].coupons;
					console.log(vm.clickCoupons)

				}
				
			else
				{
					if(vm.CoupLastIndex==vm.selected)
					{
						vm.showCoupons=false;
					}
					else
					{
						vm.CoupLastIndex=vm.selected;
						vm.clickCoupons=vm.customers[index].coupons;
						console.log(vm.clickCoupons)
					}
				}
		}

		
		
						
			this.editORCreateCust = function (index)    /// form usage create and update
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
								vm.editName = vm.customers[index].custName;
							
								this.create = false;   // form marker
								this.edit = true;	   // form marker
					
							}
			}
			
			
			//apply form
			
			this.applyCreate= function()
			{
						var thisCustomer = {
								custName : vm.custName,
				   				password : vm.passw1,
						}
						console.log(thisCustomer)
						
						adminWebService.createCustomer(thisCustomer)
						.then(function(response)
						{
							console.log(response.data);
							vm.userMessage="successfuly created the customer : "+ thisCustomer.custName;
							console.log(response.data);
							vm.postMSG();		        		
							}, 
		        		
		        		function(response)
		        		{
								vm.userMessage="failed to creare a new customer named : "+ thisCustomer.custName +": "+response.data;
								console.log(response.data);
								vm.postMSG();
		        		});
			}	
			
			this.applyUpdate = function()
			{
				var thisIsCustomer = vm.customers[vm.selected];
	
						var thisCustomer = {
							custId: thisIsCustomer.custId,  // may not be changed
			   				custName : thisIsCustomer.custName, // may not be changed
			        		password : vm.passw1,
			   				}
						
							console.log(thisCustomer);
							
					adminWebService.updateCustomer(thisCustomer)
					.then(function(response)
							{
								vm.userMessage="successfuly updated  the customer : "+ thisCustomer.custName;
								console.log(response.data);
								vm.postMSG();
			        		}, 
			        		
			        		function(response)
			        		{
								vm.userMessage="failed to update the customer : "+ thisCustomer.custName +": "+response.data;
								console.log(response.data);
								vm.postMSG();     			
			        		});

			}

			
			
			
			// remove method
			
			this.removeCustomer = function (index)
			{
				vm.selected = index;
				var removeCust = vm.customers[index];
				var removeIt =
					{
						custId: removeCust.custId,  // may not be changed
		   				custName : removeCust.custName, // may not be changed
					}

				adminWebService.removeCustomer(removeIt)
				.then(function(response)
				{
					vm.userMessage="successfuly removed  the customer : "+ removeIt.custName;
					console.log(response.data);
					vm.postMSG();
				}, 
				function(response)
				{
					vm.userMessage="failed to created  the company : "+ removeIt.custName +": "+response.data;
					console.log(response.data);
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
