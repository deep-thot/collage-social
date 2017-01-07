/**
 * Created by Jonatan on 2015-12-01.
 */
angular.module('profileServices', ['ngResource']).factory('Profile', ['$resource',
    function($resource){
        var resource = $resource('/profile/:action', {}, {
            list: {method: 'GET', params:{action:'all'}, isArray:true},
            new: {method: 'POST', params:{action:'new'}, isArray:false},
            update: {method: 'POST', params: {action: 'update'}, isArray: false}
        });
        return {
            list: function (skipCache, success) {
                if (this.profiles) {
                    if(success) {
                        success(this.profiles);
                    }
                    return this.profiles;
                } else {
                    this.profiles = resource.list(success);
                    return this.profiles;
                }
            },
            new: resource.new,
            update: resource.update
        };
    }]).factory('Auth', ['$resource', function($resource){
        return $resource('/:action', {}, {
            logout: {method: 'POST', params:{action:'logout'}, isArray:false}
        });
}]).factory('User', ['$resource', function($resource){
    var resource = $resource('/user');
    return {
        get: function(success) {
            if(this.user){
                if(success){
                    success(this.user);
                }
                return this.user;
            } else {
                this.user = resource.get(success);
                return this.user;
            }
        }
    };

}]).factory('Image', ['$resource', function($resource){
    return $resource('/profile/image/:id', {id: '@id'}, {
        upload: {method: 'POST', headers: {'Content-Type': undefined}, transformRequest: angular.identity, isArray: false }
    })
}]);

