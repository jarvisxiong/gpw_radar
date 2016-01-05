'use strict';

angular.module('gpwRadarApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stockslist', {
                parent: 'entity',
                url: '/stocks/list',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'global.leftsidemenu.stockList'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stock/list/stocks-list.html',
                        controller: 'StockController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stock');
                        return $translate.refresh();
                    }]
                }
            });
    });