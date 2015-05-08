(function () {
    var App;
    App = {};
    /*
     Init 
     */


    App.init = function () {
        App.canvas = document.createElement('canvas');

        App.canvas.height = 800;
        App.canvas.width = 1000;
        document.getElementsByTagName('article')[0].appendChild(App.canvas);
        App.ctx = App.canvas.getContext("2d");
        App.ctx.fillStyle = "solid";
        App.ctx.strokeStyle = "#ECD018";
        App.ctx.lineWidth = 5;
        App.ctx.lineCap = "round";



        App.loadCanvas = function(dataURL){    // load image from data url
            var imageObj = new Image();
            imageObj.onload = function() {
                console.log("drawing image");
                App.ctx.drawImage(this, 0, 0);
            };
            console.log("setting image src");
            imageObj.src = "data:image/gif;base64,"+dataURL;
        }

        // make ajax call to get image data url
        var request = new XMLHttpRequest();
        request.open('GET', 'http://localhost:8080/global-canvas/rest/public/canvas/test/image', true);
        request.onreadystatechange = function() {
            console.log("request State = "+request.readyState);
            console.log("request status = "+request.status);
            // Makes sure the document is ready to parse.
            if(request.readyState == 4) {
                // Makes sure it's found the file.

                if(request.status == 200) {
                    App.loadCanvas(request.responseText);
                }
            }
        };
        request.send(null);

        function dlCanvas() {
            var dt = App.canvas.toDataURL('image/png');
            /* Change MIME type to trick the browser to downlaod the file instead of displaying it */
            dt = dt.replace(/^data:image\/[^;]*/, 'data:application/octet-stream');

            /* In addition to <a>'s "download" attribute, you can define HTTP-style headers */
            dt = dt.replace(/^data:application\/octet-stream/, 'data:application/octet-stream;headers=Content-Disposition%3A%20attachment%3B%20filename=Canvas.png');

            this.href = dt;
        };


        document.getElementById("dl").addEventListener('click', dlCanvas, false);

        function merge() {

            // make ajax call to get image data url
            var request = new XMLHttpRequest();
            request.open('GET', 'http://localhost:8080/global-canvas/rest/public/canvas/merge', true);
            request.onreadystatechange = function() {
                console.log("merge request State = "+request.readyState);
                console.log("merge request status = "+request.status);
                // Makes sure the document is ready to parse.
                if(request.readyState == 4) {
                    // Makes sure it's found the file.

                    if(request.status == 200) {
                        console.log("merge request done! = ");
                    }
                }
            };
            request.send(null);

        };

        document.getElementById("merge").addEventListener('click', merge, false);

        App.getUrlParameter = function (sParam)
        {
            var sPageURL = window.location.search.substring(1);
            var sURLVariables = sPageURL.split('&');
            for (var i = 0; i < sURLVariables.length; i++)
            {
                var sParameterName = sURLVariables[i].split('=');
                if (sParameterName[0] == sParam)
                {
                    return sParameterName[1];
                }
            }
        }



        var wsUri = "ws://localhost:8080/global-canvas/draw?user=" + App.getUrlParameter("user");

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
            App.draw(event.x, event.y, event.type);
        };
        websocket.onerror = function (evt) {
            console.log("Error: " + evt)
        };

        App.doSend = function (message) {
            websocket.send(message);
        }



        App.draw = function (x, y, type) {
            if (type === "dragstart") {
                App.ctx.beginPath();
                return App.ctx.moveTo(x, y);
            } else if (type === "drag") {
                App.ctx.lineTo(x, y);
                return App.ctx.stroke();
            } else {
                return App.ctx.closePath();
            }
        };
    };
    /*
     Draw Events
     */
    $('canvas').live('drag dragstart dragend', function (e) {
        var offset, type, x, y;
        type = e.handleObj.type;
        offset = $(this).offset();
        e.offsetX = e.layerX - offset.left;
        e.offsetY = e.layerY - offset.top;
        x = e.offsetX;
        y = e.offsetY;
        App.draw(x, y, type);
        App.doSend(JSON.stringify({user: App.getUrlParameter("user"), x: x, y: y, type: type}));

    });
    $(function () {
        return App.init();
    });
}).call(this);