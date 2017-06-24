var app = angular.module('adminApp', ['ngRoute']);


//app.controller('navController', function($location) 
//	{
//	// highlight the selected item from the navbar
//    	$scope.isActive = function (viewLocation) 
//    	{ 
//        return viewLocation === $location.path();
//    	};
//	});

// disable http get caching (for internet explorer)
app.config(function($httpProvider){
	  $httpProvider.defaults.headers.common['Cache-Control'] = 'no-cache';
	  $httpProvider.defaults.cache = false;

	  if (!$httpProvider.defaults.headers.get) {
	      $httpProvider.defaults.headers.get = {};
	  }
	  $httpProvider.defaults.headers.get['If-Modified-Since'] = '0';
});

// configure our routes
app.config(['$routeProvider' ,function($routeProvider) {
	$routeProvider
	
	// route for the coupons list
	.when('/', {
		templateUrl : 'companies.html',
		controller  : 'CompanyController as CompanyController'
	})	

	// route for the company coupons list 
	.when('/customers', {
		templateUrl : 'customers.html',
		controller  : 'CustomerController as CustomerController'
	})

	// route for the company settings 
	.when('/companies', {
		templateUrl : 'companies.html',
		controller  : 'CompanyController as CompanyController'
	});
	
}]);