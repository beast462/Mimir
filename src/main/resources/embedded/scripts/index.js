console.log = function (params) {
    bridge.log(params.toString());
};

console.error = function (params) {
    bridge.error(params.toString());
};

console.info = function (params) {
    bridge.info(params.toString());
};