var app = angular.module('customerApp', ['ngRoute']);

//disable http get caching (for internet explorer)
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
		templateUrl : 'customerInfo.html',
		controller  : 'InfoController as InfoController'
	})	

	// route for the customer coupons list 
	.when('/coupons', {
		templateUrl : 'avilableCoupons.html',
		controller  : 'CustCouponController as CustCouponController'
	})

	// route for the customer available coupons to buy 
	.when('/buy', {
		templateUrl : 'purchasedCoupons.html',
		controller  : 'PurchaseController as PurchaseController'
	})	
	// route for the customer settings 
	.when('/settings', {
		templateUrl : 'customerInfo.html',
		controller  : 'InfoController as InfoController'
	})
	
	// route for the customer available coupons - full view 
	.when('/fullview', {
		templateUrl : 'avilableFV.html',
		controller  : 'AvilableFVController as AvilableFVController'
	});
	
}]);