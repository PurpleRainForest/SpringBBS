var demoApp = angular.module('demoApp', [ 'ngRoute' ]);

demoApp.config(function($routeProvider) {
	$routeProvider.when('/view1', {
		controller : 'SimpleController',
		templateUrl : 'partials/view1.html'
	}).when('/view2', {
		controller : 'SimpleController2',
		templateUrl : 'partials/view2.html'
	}).otherwise({
		redirectTo : '/'
	});
});

demoApp.controller('SimpleController', function($scope) {
	$scope.customers = [ {
		name : 'Dave Jones',
		city : 'Phoenix'
	}, {
		name : 'Jamie Riley',
		city : 'Atlanta'
	}, {
		name : 'Heedy Wahlin',
		city : 'Chandler'
	}, {
		name : 'Thomas Winter',
		city : 'Seattle'
	} ];

	$scope.addCustomer = function() {
		$scope.customers.push({
			name : $scope.newCustomer.name,
			city : $scope.newCustomer.city
		});
	};

	$scope.removeCustomer = function(cust) {
		var idx = $scope.customers.indexOf(cust);
		if (idx != -1) {
			$scope.customers.splice(idx, 1);
		}

	};

});

demoApp.controller('SimpleController2', function($scope) {
	$scope.customers = [ {
		name : 'John Smith',
		city : 'Phoenix'
	}, {
		name : 'John Doe',
		city : 'New York'
	}, {
		name : 'Jane Doe',
		city : 'San Francisco'
	} ];
});