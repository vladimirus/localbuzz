angular.module("ChatApp", []).controller("ChatController", function($scope){
  // connect to websockets endpoint of our server
  var ws = new WebSocket("ws://" + document.location.host + ":9000/socket");

  var chat = this;
  chat.messages = [];

  ws.onmessage = function(msg) {
    chat.messages.unshift(msg.data);
    $scope.$digest();
  };
});