var app = angular.module('index', []);

app.controller('search', ["$scope", "$http", function ($scope, $http) {

    var self = this;

    $scope.callRestSearchService = function (keyEvent) {
        if(keyEvent.which != 13) {
            return;
        }

        var dataObj = {
            searchInput: $scope.searchInput
        };

        var res = $http.post("/search", dataObj);

        res.success(function (data, status, headers, config) {
            $scope.results = data;
        });

        res.error(function (data, status, headers, config) {
            console.log("ERROR");
            console.log(JSON.stringify({data: data}));
        });
    }

}]);