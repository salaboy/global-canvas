(function(){
	var gcanvas = angular.module('gcanvas', ['ngRoute']);
    gcanvas.constant("appConstants", {
        server: "localhost:8080/",
        context: "global-canvas/"
    })
	gcanvas.config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/home.html',
                controller: 'homeController'
            })
			.when('/canvas/:email', {
                templateUrl: 'views/canvas.html',
                controller: 'canvasController'
            })
           
        .otherwise({
            redirectto: '/'
        });

    });


    //HISTORY
    gcanvas.run(function ($rootScope, $location) {

        var history = [];

        $rootScope.$on('$routeChangeSuccess', function () {
            history.push($location.$$path);
            console.log("HISTORY " + history)
        });

        $rootScope.back = function () {
            var prevUrl = history.length > 1 ? history.splice(-2)[0] : "/";

            $location.path(prevUrl);
        };
    });

    //END HISTORY

	
}());