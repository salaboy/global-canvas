(function(){
	 var mainController = function($scope, $rootScope, $location){
	 	$scope.hiVar = "hola esto esta andando en teoria"
		 $scope.isActive = function (route) {
			 return route === $location.path();
		 };

		 $rootScope.$on('goTo', function (event, data) {
			 $location.path(data);
		 });

	 }
	 
	 mainController.$inject = ['$scope', '$rootScope', '$location'];
	 
	 angular.module("gcanvas").controller("mainController", mainController);
}());