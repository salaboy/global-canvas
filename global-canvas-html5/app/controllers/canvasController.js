(function(){
	 var canvasController = function($scope, $rootScope, $routeParams, $canvasFactory, appConstants, $transformRequestToForm){

		 $scope.email = ($routeParams.email || "");
		 console.log("Email From Route: "+$scope.email);
		 $scope.getRandomColor = function() {
			 var letters = '0123456789ABCDEF'.split('');
			 var color = '#';
			 for (var i = 0; i < 6; i++ ) {
				 color += letters[Math.floor(Math.random() * 16)];
			 }
			 return color;
		 }

		 $scope.merge = function () {
			console.log("Calling Merge! ");
			 $canvasFactory.merge()
				 .success(function (data) {
					 console.log("Merged! : " + data);
				 }).error(function (data) {
					 console.log("Error! : " + data);

				 });

		 };

		 $scope.load = function () {
			$canvasFactory.load().
				success(function (data) {
					console.log(data);
					$scope.loadCanvas(data);
				}).
				error(function (data) {
					console.log("Error! : " + data);

				});
		 }

		 $scope.load();



		 var wsUri = "ws://"+appConstants.server + appConstants.context +"draw?user=" + $scope.email;

		 websocket = new WebSocket(wsUri);

		 websocket.onopen = function (evt) {
			 console.log("WebScoket opening...")
		 };
		 websocket.onclose = function (evt) {
			 console.log("WebSocket closing...");
			 websocket.close();
		 };
		 websocket.onmessage = function (evt) {
			 var event = JSON.parse(evt.data);
			 $scope.drawExternal(event.x, event.y, event.type, event.color);
		 };
		 websocket.onerror = function (evt) {
			 console.log("Error: " + evt)
		 };

		 $scope.doSend = function (message) {
			 websocket.send(message);
		 }

	 }


	 canvasController.$inject = ['$scope', '$rootScope', '$routeParams', '$canvasFactory', 'appConstants', '$transformRequestToForm'];
	 
	 angular.module("gcanvas").controller("canvasController", canvasController);
}());