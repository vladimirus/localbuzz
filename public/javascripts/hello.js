angular.module("ChatApp", []).controller("ChatController", function($scope){
  // connect to websockets endpoint of our server
  var host = window.document.location.host.replace(/:.*/, '');
  var port = window.document.location.port;
  var ws = new WebSocket('ws://'+ host+':'+ port +'/socket');

    var chat = this;
  chat.messages = [];

  ws.onmessage = function(msg) {
    chat.messages.unshift(msg.data);
    $scope.$digest();
  };
});