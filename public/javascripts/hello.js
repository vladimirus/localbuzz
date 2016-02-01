angular.module("ChatApp", []).controller("ChatController", function($scope){
  // connect to websockets endpoint of our server
  var ws = new WebSocket("ws://localhost:9000/socket");

  var chat = this;
  chat.messages = [];

  ws.onmessage = function(msg) {
    chat.messages.push(msg.data);
    $scope.$digest();
  };
});