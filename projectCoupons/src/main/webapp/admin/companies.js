var adminApp = angular.module("adminApp")
		.controller("CompanyController",['$window','$http','adminWebService','$scope', '$filter',function ($window,$http,adminWebService,$scope, $filter)
{
		
		var vm = this;
		
	
		vm.selected = 0;
		vm.editName='';
		vm.passw1 ='';
		vm.passw2 ='';
		vm.compName='';
		vm.equalPass=false;
		vm.clickCoupons=[];
		
		vm.companies = [];

		this.create = false;   // form marker
		this.edit = false;	  // form marker
		
		vm.showCoupons=false;
		vm.companyFilter='';
		vm.companyFilter.name='';
		vm.companyFilter.compId='';
		vm.propertyName= '';
		vm.theirCoupons='';
		
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
			vm.companyFilter.name='';
			vm.companyFilter.compId='';
			vm.companyTypefilter='';
			
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
			vm.companyFilter.name='';
			vm.companyFilter.compId='';
			vm.couponTypefilter='';
			
		}
		
		
		
		adminWebService.getAllCompanies()	
		.then 
			(function (data)
			{
			console.log(data);
			
				vm.companies = data;
			},
		
			function (response)
			{
				console.log(response.data);
				vm.userMessage=response.response;
			});
		
		
					
		this.getCoupons= function(index)
		{
			vm.theirCoupons=vm.companies[index].compName;
			vm.selected = index;
			console.log(vm.selected);
			console.log(vm.showCoupons);
			if (vm.showCoupons==false)
				{
					vm.CoupLastIndex=vm.selected;
					vm.showCoupons=true;
					console.log(vm.showCoupons)
					vm.clickCoupons=vm.companies[index].coupons;
					console.log(vm.clickCoupons)
								}
				
			else
				{
					if(vm.CoupLastIndex==index)
					{
						vm.showCoupons=false;
					}
					else
					{
						vm.CoupLastIndex=vm.selected;
						vm.clickCoupons=vm.companies[index].coupons;
						console.log(vm.clickCoupons)
					}
				}
		}
			


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
								vm.editName = vm.companies[index].compName;
								this.create = false;   // form marker
								this.edit = true;	   // form marker
							}
			}
	
						
			// form submitting method			
						
			this.applyCreate= function()
			{
				
						var thisCompany = {
								compName : vm.compName,
				   				password : vm.passw1,
				        		email 	 : vm.newEmail
				        
				   		}
						console.log(thisCompany)
						
						adminWebService.createCompany(thisCompany)
						.then(function(response)
						{
							console.log(response.data);
							console.log("company added");
							vm.userMessage="successfuly created  the company : "+ thisCompany.compName +" "+response.data;
							console.log(response);
							vm.postMSG();
						}, 
		        		
		        		function(response)
		        		{
		        			console.log(response.data);  
							vm.userMessage="failed to create  the company : "+ thisCompany.compName +" "+response.data;
							vm.postMSG();
		        		});
			}	
			
			this.applyUpdate = function()
			{
				var thisIsCompany = vm.companies[vm.selected];
	
						var thisCompany = {
							compId: thisIsCompany.compId,  // may not be changed
			   				compName : thisIsCompany.compName, // may not be changed
			        		password : vm.passw1,
			        		email : vm.newEmail,
			   				}
						
							console.log(thisCompany);
							
					adminWebService.updateCompany(thisCompany)
					.then(function(response)
							{
								console.log(response.data);
								console.log("company updated");
								vm.userMessage="successfuly updated  the company : "+ thisIsCompany.compName +" "+response.data;
								console.log(response);
								vm.postMSG();

							}, 
			        		
			        		function(response)
			        		{
			        			console.log(response.data); 
								vm.userMessage="failed updated the company : "+ thisIsCompany.compName +" "+response.data;
								vm.postMSG();
			        		});
			}
			
			
			
			// remove method
			
			this.removeCompany = function (index)
			{
				vm.selected = index;
				var removeComp = vm.companies[index];
				console.log(removeComp);
				
				var removeIt = 
					{
						compId: removeComp.compId,  // may not be changed
		   				compName : removeComp.compName, // may not be changed
					}
						
				console.log(removeIt);

				adminWebService.removeCompany(removeIt)
				.then(function(response)
				{
					//vm.message =  response;
					vm.userMessage=response;
					vm.userMessage="successfuly remove company : "+ removeIt.compName +" "+response.data;
					vm.postMSG();
				}, 
				function(response)
				{
					vm.userMessage="failed to remove company : "+ removeIt.compName +" "+response.data;
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

