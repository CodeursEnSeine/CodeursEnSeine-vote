var app = angular.module('vote',[]);

app.controller("VoteCtrl",["$scope","$http",function MainCtrl($scope, $http) {

    $scope.currentTab="talks";
    $scope.showerrorsending=false;
    var refreshTalks=function() {
        $http.get("/talks").success(function (data) {
            $scope.talks = data;

        });
    }
    refreshTalks();
    $scope.refreshTalks=refreshTalks;

    $scope.gotoTalks= function () {
        $scope.currenttalk=undefined;
        $scope.currentTab="talks";
    }
    $scope.gotoVote= function (talk) {
        $scope.currenttalk=talk;
        $scope.currentTab="vote";
    }

    $scope.addVote =function (talkid,vote) {
        $scope.showerrorsending=false;
        $scope.currentvote=vote;
        $scope.currentTab="sendingvote";
        $http.post("/vote",{ talkid: talkid, votevalue: vote}).
            success(function(data, status, headers, config) {
                $scope.currentTab="thanks";
            }).
            error(function(data, status, headers, config) {
                $scope.showerrorsending=true;
            });
    }

}]);


