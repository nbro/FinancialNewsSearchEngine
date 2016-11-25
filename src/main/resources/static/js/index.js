angular.module('hello', []).controller('home', function ($http) {

    var self = this;

    $http.get('resource/').then(function (response) {
        console.log(response.data);
        self.greeting = response.data;
    });

});