(function(){
	 var homeController = function($scope, $rootScope){
	 	$scope.homeVar = "Hola desde el home visteh"
		 $scope.newUserSubmit = function () {
			 $rootScope.$broadcast('goTo', "/canvas/"+$scope.newUser.email);
		 }
	 }
	 
	 homeController.$inject = ['$scope', '$rootScope'];
	 
	 angular.module("gcanvas").controller("homeController", homeController);
}());