/**
 * Created by salaboy on 10/05/15.
 */
(function(){
    var drawing = function(){
        return {
            restrict: "A",
            link: function($scope, element){
                var ctx = element[0].getContext('2d');
                ctx.strokeStyle = $scope.getRandomColor();
                // variable that decides if something should be drawn on mousemove
                var drawing = false;

                // the last coordinates before the current move
                var lastX;
                var lastY;

                element.bind('mousedown', function(event){

                    lastX = event.offsetX;
                    lastY = event.offsetY;

                    // begins new line
                    ctx.beginPath();

                    drawing = true;
                    $scope.doSend(
                        JSON.stringify(
                            {user: $scope.email,
                                x: lastX,
                                y: lastY,
                                type: 'dragstart',
                                color: ctx.strokeStyle.toString()}));
                });
                element.bind('mousemove', function(event){
                    if(drawing){
                        // get current mouse position
                        currentX = event.offsetX;
                        currentY = event.offsetY;

                        $scope.doSend(
                            JSON.stringify(
                                {user: $scope.email,
                                    x: currentX,
                                    y: currentY,
                                    type: 'drag',
                                    color: ctx.strokeStyle.toString()}));
                        $scope.draw(lastX, lastY, currentX, currentY);

                        // set current coordinates to last one
                        lastX = currentX;
                        lastY = currentY;
                    }

                });
                element.bind('mouseup', function(event){
                    // stop drawing
                    drawing = false;
                    $scope.doSend(
                        JSON.stringify(
                            {user: $scope.email,
                                x: currentX,
                                y: currentY,
                                type: 'dragend',
                                color: ctx.strokeStyle.toString()}));
                });

                // canvas reset
                function reset(){
                    element[0].width = element[0].width;
                }

                $scope.draw = function (lX, lY, cX, cY){
                    // line from
                    ctx.moveTo(lX,lY);
                    // to
                    ctx.lineTo(cX,cY);
                    // color
                    ctx.strokeStyle = "#4bf";
                    // draw it
                    ctx.stroke();

                }

                $scope.drawExternal = function (x, y, type, color){
                    if(type == "dragstart"){
                    // line from
                        ctx.moveTo(x,y);
                    }else if(type == "drag"){
                    // to
                        ctx.strokeStyle = color;
                        ctx.lineTo(x,y);
                        ctx.stroke();
                    }

                }

                $scope.loadCanvas = function(dataURL){    // load image from data url
                    var imageObj = new Image();
                    imageObj.onload = function() {
                        console.log("drawing image");
                        ctx.drawImage(this, 0, 0);
                    };
                    console.log("setting image src");
                    imageObj.src = "data:image/gif;base64,"+dataURL;
                }


            }
        };
    }
    angular.module( "gcanvas" ).directive('drawing', drawing);
}());
