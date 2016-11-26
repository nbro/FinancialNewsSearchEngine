var app = angular.module('index', []);

app.controller('search', ["$scope", "$http", "$sce", "$timeout", function ($scope, $http, $sce, $timeout) {

    var self = this;

    /* quotes from: https://www.brainyquote.com/quotes/topics/topic_finance.html*/
    var financialQuotes = {
        "In this world nothing can be said to be certain, except death and taxes.": "Benjamin Franklin",
        "Derivatives are financial weapons of mass destruction.": "Warren Buffett",
        "It is well enough that people of the nation do not understand our banking and monetary system, for if they did, I believe there would be a revolution before tomorrow morning": "Henry Ford",
        "Our incomes are like our shoes; if too small, they gall and pinch us; but if too large, they cause us to stumble and to trip.": "John Locke",
        "Beware of little expenses. A small leak will sink a great ship.": "Benjamin Franklin",
        "All money is a matter of belief.": "Adam Smith"
    }

    var randomQuote = function () {
        var keys = Object.keys(financialQuotes);
        return keys[Math.floor(Math.random() * keys.length)];
    }

    var switchQuote = function () {
        var currentInnerHTML = document.getElementById("financialQuote").innerHTML;

        var nextQuote = randomQuote();
        while (currentInnerHTML == nextQuote) {
            nextQuote = randomQuote();
        }

        var author = financialQuotes[nextQuote];

        document.getElementById("financialQuote").innerHTML = nextQuote;
        document.getElementById("quoteAuthor").innerHTML = author;

        $timeout(switchQuote, 60000);

    }

    switchQuote();

    // source: http://stackoverflow.com/questions/15519713/highlighting-a-filtered-result-in-angularjs

    $scope.callRestSearchService = function (keyEvent) {

        /*
         if (keyEvent.which != 13) {
         return;
         } */

        var dataObj = {
            searchInput: $scope.searchInput
        };

        var res = $http.post("/search", dataObj);

        res.success(function (data, status, headers, config) {
            $scope.results = data;
        });

        res.error(function (data, status, headers, config) {
            console.log(JSON.stringify({data: data}));
        });
    }

}
])
;
