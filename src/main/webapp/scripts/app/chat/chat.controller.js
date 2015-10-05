'use strict';

angular.module('gpwradarApp')
    .config(function(ngstompProvider){
            ngstompProvider.url('/socket').class(SockJS);
	})
    .controller('ChatController', function ($scope, $window, $http, ngstomp, ChatMessage) {
    	
        $scope.messages = ChatMessage.getMessages({page: 0}); 
        
        var counter = 1;
        $scope.loadOlder = function() {
        	var promise = $http({
        	    url: 'api/chat/older/messages', 
        	    method: "GET",
        	    params: {page: counter}
        	 });
        	promise.then(
			  function(response) {
				  $scope.olderMessages = response.data;
				  for (var i = 0; i < $scope.olderMessages.length ; i++) {
		    			$scope.messages.unshift($scope.olderMessages[i]);
		    		}
			  });
            counter++;
        };
        
        ngstomp.send('/app/webchat/user/login');
        ngstomp.subscribe('/webchat/user',  newUserConnected);
        function newUserConnected(user) {
        	$scope.users = JSON.parse(user.body);
        };
        
	    $scope.sendMessage = function() {
	        ngstomp.send('/app/webchat/send/message', $scope.message);
	        if($scope.messages.length > 15){
	        	$scope.messages.splice(0, $scope.messages.length-15);
	        }
	        $scope.message = "";
	    };
	    
	    ngstomp.subscribe('/webchat/recive',  messageFromServer);
        function messageFromServer(message) {
        	$scope.messages.push(JSON.parse(message.body));
        };
        
        $window.onbeforeunload = function (evt) {
        	$scope.logout();
        }
        
	    $scope.$on('$destroy', function() {
	    	$scope.logout();
	    });
	    
	    $scope.logout = function() {
	    	ngstomp.send('/app/webchat/user/logout');
	    	ngstomp.unsubscribe('/webchat/recive', unsubscribe);
	    	ngstomp.unsubscribe('/webchat/user', unsubscribe);
	    }
    	
    	function unsubscribe() {
 	    }
    });
