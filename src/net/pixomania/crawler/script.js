var logApp = angular.module("logApp", []);

logApp.controller("logCtrl", function($scope, $http) {
	$http.get("log.json").success(function(data) {
		$scope.log = data;
	});
});