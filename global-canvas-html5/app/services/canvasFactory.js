
(function(){

    var $canvasFactory = function ($http, $transformRequestToForm, appConstants){
        var factory={};
        factory.merge = function(){
            return $http({
                method: 'GET',
                url: "http://"+appConstants.server + appConstants.context + 'rest/public/canvas/merge'

            });
        };

        factory.load = function(){
            return $http({
                method: 'GET',
                url: "http://"+appConstants.server + appConstants.context + 'rest/public/canvas/test/image'

            });
        };


        return factory;
    }



    $canvasFactory.$inject = ['$http','$transformRequestToForm','appConstants'];
    angular.module("gcanvas").factory("$canvasFactory", $canvasFactory);

}());