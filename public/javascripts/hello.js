angular.module("ChatApp", []).controller("ChatController", function($scope){
  // connect to websockets endpoint of our server
  var ws = new WebSocket("ws://localhost:9000/socket");

  // binding model for the UI
  var chat = this;
  chat.messages = [];

  // what to do when we receive message from the webserver
  ws.onmessage = function(msg) {
    chat.messages.push(msg.data);
    $scope.$digest();
  };
});