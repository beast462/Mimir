class PromiseEvent extends Event {
    constructor(e) {
        super(e);
        this.result = null;
        this.error = null;
    }
}

((bridge) => {
    console.log = function (params) {
        bridge.log(params.toString());
    };

    console.error = function (params) {
        bridge.error(params.toString());
    };

    console.info = function (params) {
        bridge.info(params.toString());
    };

    window._bridge = {
        dispatcher: new EventTarget()
    };
})(window.bridge);