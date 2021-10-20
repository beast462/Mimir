((_bridge) => {
    const event = new PromiseEvent('%s');
    event.error = new PromiseError('%s');
    event.error.javaStackTrace = '%s';

    _bridge.dispatcher.dispatchEvent(event);
})(window._bridge);