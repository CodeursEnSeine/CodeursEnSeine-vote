var app = angular.module('vote',[]);

app.controller("VoteCtrl",["$scope","$http",function MainCtrl($scope, $http) {

    $scope.showTalks=true;
    $http.get("/talks").success(function(data) {
        $scope.talks=data;

    });

}]);


