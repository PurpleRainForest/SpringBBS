/*function SimpleController05($scope) {
	$scope.funding = {
		budget : 0,
		invest : 0,
		target : 0
	};
	$scope.$watch('funding.budget', function() {
		$scope.funding.invest = $scope.funding.budget * 1.5;
	});
	$scope.$watch('funding.invest', function() {

		$scope.funding.target = $scope.funding.invest * 1.2;
	});
}*/

function MemberController($scope, $http) {

	$http({
		method : 'GET',
		url : 'http://localhost:8080/s3m/getMemListJSON.do'
	}).success(function(data, status, headers, config) {
		$scope.members = data;
	}).error(function(data, status, headers, config) {
		alert("failure");
	});

/*	$scope.addCustomer = function() {
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

	};*/
}
