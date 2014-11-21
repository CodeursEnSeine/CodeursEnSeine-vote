var app = angular.module('vote',[]);

app.controller("VoteCtrl",["$scope","$http",function MainCtrl($scope, $http) {

    $http.get("/talks").success(function(data) {
        $scope.talks=data;

    });

}]);
