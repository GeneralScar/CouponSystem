var app = angular.module('companyApp', ['ngRoute']);

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
app.config(['$routeProvider' ,function($routeProvider) { //, $locationProvider
//    $locationProvider.html5Mode(true);
	$routeProvider
	
	// route for the coupons list
	.when('/', 
	{
		templateUrl : 'settings.html',
		controller  : 'InfoController as InfoController'
	})	

	// route for the company coupons list 
	.when('/coupons', {
		templateUrl : 'coupons.html',
		controller  : 'CouponController as CouponController'
	})

	// route for the company settings 
	.when('/settings', {
		templateUrl : 'settings.html',
		controller  : 'InfoController as InfoController'
	})
	
	// route for the company settings 
	.when('/couponTest', {
		templateUrl : 'couponTest.html',
		controller  : 'TestController as TestController'
	});
	
}]);
