		var demoApp = angular.module('demoApp', []);

		// controller use-case #1 
		function SimpleController($scope) {
			$scope.customers = [ {
				name : 'Dave Jones',
				city : 'Phoneix'
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
		}
		demoApp.controller('SimpleController', SimpleController);

		/* // controller use-case #2
		demoApp.controller('SimpleController',function SimpleController($scope) {
			$scope.customers = [ {
				name : 'Dave Jones',
				city : 'Phoneix'
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
		}); */

		/* // controller use-case #3
		var controllers = {};
		controllers.SimpleController = function SimpleController($scope) {
			$scope.customers = [ {
				name : 'Dave Jones',
				city : 'Phoneix'
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
		};
		demoApp.controller(controllers); */