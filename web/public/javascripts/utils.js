//Declare the namespace
var e3soos = e3soos || {};

/**
 * Utility module.
 */
e3soos.utils = (function () {
    return {
        toParam : function (object, keyPrefix) {
            var tmp = {};
            $.each(object, function (key, value) {
                tmp[keyPrefix + key] = value;
            });
            return $.param(tmp);
        }
    };
})();